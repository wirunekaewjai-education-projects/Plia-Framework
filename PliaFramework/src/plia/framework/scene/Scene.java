package plia.framework.scene;

import java.util.ArrayList;

import android.opengl.GLES20;

import plia.framework.core.GameObject;
import plia.framework.core.Screen;
import plia.framework.math.Matrix3;
import plia.framework.math.Matrix4;
import plia.framework.math.Vector3;
import plia.framework.math.Vector4;
import plia.framework.scene.obj3d.animation.Animation;
import plia.framework.scene.obj3d.geometry.Geometry;
import plia.framework.scene.obj3d.geometry.Mesh;
import plia.framework.scene.obj3d.shading.Color3;
import plia.framework.scene.obj3d.shading.Shader;
import plia.framework.scene.obj3d.shading.ShaderProgram;
import plia.framework.scene.obj3d.shading.Texture2D;

@SuppressWarnings({"rawtypes"})
public abstract class Scene extends GameObject implements IScene
{
	private Layer[] children = new Layer[32];
	private int childCount = 0;
	private boolean isInitialized = false;
	
	public Scene()
	{
		setName("Scene");
	}
	
	public void initialize()
	{
		if(isInitialized)
		{
			onInitialize();
			isInitialized = true;
		}
	}
	
	@Override
	public void update()
	{
		if(isActive())
		{
			for (int i = 0; i < childCount; i++)
			{
				children[i].update();
			}
		}
	}
	
	private final int indexOf(Layer layer)
	{
		for (int i = 0; i < children.length; i++)
		{
			if(layer == children[i])
			{
				return i;
			}
		}
		
		return -1;
	}

	public final boolean contains(Layer layer)
	{
		return indexOf(layer) != -1;
	}

	public final int getLayerCount()
	{
		return childCount;
	}

	public final Layer getLayer(int index)
	{
		if(index < childCount)
		{
			return children[index];
		}
		
		return null;
	}

	public final boolean addLayer(Layer layer)
	{
		if(indexOf(layer) == -1)
		{
			if(childCount >= children.length)
			{
				Layer[] arr = new Layer[children.length + 32];
				System.arraycopy(children, 0, arr, 0, children.length);
				children = arr;
			}
			
			children[childCount++] = layer;
			return true;
		}
		return false;
	}

	public final boolean removeLayer(Layer layer)
	{
		int i = indexOf(layer);
		if(i > -1 && i < childCount)
		{
			Layer[] arr = new Layer[children.length];
			System.arraycopy(children, 0, arr, 0, i);
			
			int i2 = i+1;
			int length = childCount - i2;
			if(length > 0)
			{
				System.arraycopy(children, i2, arr, i, length);
			}
			
			children = arr;
			childCount--;

			return true;
		}
		return false;
	}
	
	static Camera mainCamera = new Camera();
	static boolean hasChangedProjection = true;
	static boolean hasChangedModelView = true;
	
	public static Camera getMainCamera()
	{
		return mainCamera;
	}
	
	public static void setMainCamera(Camera mainCamera)
	{
		Scene.mainCamera = mainCamera;
	}
	
	private static float ratio;
	
	private static final Matrix4 modelViewProjectionMatrix = new Matrix4();
	private static final Matrix4 modelViewMatrix = new Matrix4();
	private static final Matrix4 projectionMatrix = new Matrix4();
	
	
	private static final Matrix4 tempMV = new Matrix4();
	private static final Matrix4 tempMVP = new Matrix4();
	private static final Matrix4 tempInvertmMatrix = new Matrix4();
	private static final Matrix4 tempTransformMatrix = new Matrix4();
	private static final Matrix3 tempNormalMatrix = new Matrix3();
	
	private static final Matrix4 tempS = new Matrix4();
	private static final Matrix4 tempPalette = new Matrix4();
	
	private static final Vector3 target = new Vector3();
	private static final Vector3 lightPos3 = new Vector3();
	private static final Vector4 lightPos4 = new Vector4();
	private static final Vector4 lightPosTemp = new Vector4();
	private static final Vector4 lightColorTemp = new Vector4();
	private static final Vector4 colorTemp = new Vector4();
	
	private ArrayList<Model> models = new ArrayList<Model>();
	private ArrayList<Light> lights = new ArrayList<Light>();
	
	public static Matrix4 getModelViewMatrix()
	{
		return modelViewMatrix;
	}
	
	public static Matrix4 getProjectionMatrix()
	{
		return projectionMatrix;
	}
	
	public static Matrix4 getModelViewProjectionMatrix()
	{
		return modelViewProjectionMatrix;
	}
	
	// Draw State
	public void drawScene()
	{
		if(hasChangedProjection)
		{
			ratio = (float)Screen.getWidth() / Screen.getHeight();
			
			if(mainCamera.getProjectionType() == Camera.PERSPECTIVE)
			{
				Matrix4.createFrustum(projectionMatrix, -ratio, ratio, -1, 1, 1, mainCamera.getRange());
			}
			else
			{
				Matrix4.createOrtho(projectionMatrix, -ratio, ratio, -1, 1, 1, mainCamera.getRange());
			}
			
			hasChangedProjection = false;
		}
		if(hasChangedModelView)
		{
			Vector3 eye = mainCamera.getPosition();
			Vector3 forward = mainCamera.getForward();
			
			target.x = eye.x + (forward.x * 10);
			target.y = eye.y + (forward.y * 10);
			target.z = eye.z + (forward.z * 10);
			
			Vector3 up = mainCamera.getUp();
			
			Matrix4.createLookAt(modelViewMatrix, eye, target, up);
			
			hasChangedModelView = false;
		}
		
		Matrix4.multiply(modelViewProjectionMatrix, modelViewMatrix, projectionMatrix);

		for (int i = 0; i < getLayerCount(); i++)
		{
			recusiveLayer(getLayer(i));
		}
		
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		for (int i = 0; i < models.size(); i++)
		{
			drawModel(models.get(i));
		}

		models.clear();
		lights.clear();
	}
	
	private void drawModel(Model model)
	{
		boolean hasAnimation = model.hasAnimation();
		int geometryType = model.getGeometry().getType();
		
		Shader shader = model.getMaterial().getShader();
		
		int hasTexture = 0;
		
		Texture2D texture = model.getMaterial().getBaseTexture();
		
		if(texture != null)
		{
			hasTexture = 2;
		}
		
		ShaderProgram program = shader.getProgram(0);
		
		switch (geometryType)
		{
			case Geometry.MESH: program = shader.getProgram(0 + hasTexture); break;
			case Geometry.SKINNED_MESH: program = shader.getProgram(1 + hasTexture); break;
			default: break;
		}

		float[] matrixPalette = null;
		
		Mesh mesh =  ((Mesh)model.getGeometry());
		if(hasAnimation)
		{
			Animation animation = model.getAnimation();
			int frame = animation.getCurrentFrame() - mesh.getMatrixPaletteIndexOffset();
			
			float[][] matrixPaletteAll = mesh.getMatrixPalette();
			
			if(frame >= matrixPaletteAll.length)
			{
				frame = matrixPaletteAll.length - 1;
			}
			else if(frame < 0)
			{
				frame = 0;
			}
			
			matrixPalette = matrixPaletteAll[frame];
		}
		
		if(geometryType == Geometry.MESH && hasAnimation)
		{
			tempPalette.set(matrixPalette);
			Matrix4.multiply(tempTransformMatrix, model.getWorldMatrix(), tempPalette);
			Matrix4.multiply(tempMV, modelViewMatrix, tempTransformMatrix);
		}
		else
		{
			Matrix4.multiply(tempMV, modelViewMatrix, model.getWorldMatrix());
		}
		
		Matrix4.multiply(tempTransformMatrix, tempMV, model.getAxisRotation());
		Matrix3.createNormalMatrix(tempNormalMatrix, tempTransformMatrix);
		
		// Lights
		ArrayList<Light> ls = new ArrayList<Light>();
		ls.addAll(lights);
		
		int lightCount = ls.size();
		
		program.use();
		program.setUniform(ShaderProgram.LIGHT_COUNT, lightCount);

		for (int i = 0; i < lightCount; i++)
		{
			Light light = ls.get(i);
			if(light.getLightType() == Light.DIRECTIONAL_LIGHT)
			{
				lightPosTemp.set(light.getForward(), 0);
			}
			else
			{
				lightPosTemp.set(light.getPosition(), 1);
			}
			Matrix4.multiply(lightPos4, modelViewMatrix, lightPosTemp);
			lightPos4.set(lightPos4.x, lightPos4.y, lightPos4.z, light.getLightType());
			
			Color3 lightC = light.getColor();
			lightColorTemp.set(lightC.r, lightC.g, lightC.b, 1);
			
			program.setUniform(ShaderProgram.LIGHT_POSITION_0+i, lightPos4);
			program.setUniform(ShaderProgram.LIGHT_COLOR_0+i, lightColorTemp);
			program.setUniform(ShaderProgram.LIGHT_RANGE_0+i, light.getRange());
			program.setUniform(ShaderProgram.LIGHT_INTENSITY_0+i, light.getIntensity());
		}
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getBuffer(0));
		GLES20.glEnableVertexAttribArray(ShaderProgram.VERTEX_ATTRIBUTE);
		program.setAttribPointer(ShaderProgram.VERTEX_ATTRIBUTE, 3, 0, 0, ShaderProgram.VariableType.FLOAT);
		
		GLES20.glEnableVertexAttribArray(ShaderProgram.NORMAL_ATTRIBUTE);
		program.setAttribPointer(ShaderProgram.NORMAL_ATTRIBUTE, 3, 0, mesh.NORMALS_OFFSET, ShaderProgram.VariableType.FLOAT);
		
		if(hasTexture == 2)
		{
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.getTextureBuffer());
			program.setUniform(ShaderProgram.DIFFUSE_MAP, 0);
		}
		else
		{
			Color3 baseColor3 = model.getMaterial().getBaseColor();
			colorTemp.set(baseColor3.r, baseColor3.g, baseColor3.b, 1);
			program.setUniform(ShaderProgram.COLOR, colorTemp);
		}
		
		
	}
	
	private void recusiveLayer(Layer layer)
	{
		for (int i = 0; i < layer.getChildCount(); i++)
		{
			Node child = layer.getChild(i);
			if(child instanceof Object3D)
			{
				recusiveObject3D((Object3D) child);
			}
		}
	}
	
	private void recusiveObject3D(Object3D obj)
	{
		if(obj instanceof Model)
		{
			models.add((Model) obj);
		}
		else if(obj instanceof Light)
		{
			lights.add((Light) obj);
		}
		
		for (int i = 0; i < obj.getChildCount(); i++)
		{
			Object3D child = obj.getChild(i);
			recusiveObject3D(child);
		}
	}
}

interface IScene
{
	void onInitialize();
	void onUpdate();
}