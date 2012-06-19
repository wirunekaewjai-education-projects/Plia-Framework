package plia.framework.scene;

import java.util.ArrayList;

import android.opengl.GLES20;
import android.util.Log;

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
		if(!isInitialized)
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
			onUpdate();
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
				log("Set Frustum");
			}
			else
			{
				Matrix4.createOrtho(projectionMatrix, -ratio, ratio, -1, 1, 1, mainCamera.getRange());
				log("Set Ortho");
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
			
			log("Set LookAt");
			
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
		
		int programIndx = 0;
		
		switch (geometryType)
		{
			case Geometry.MESH: programIndx = hasTexture; break;
			case Geometry.SKINNED_MESH: programIndx = 1 + hasTexture; break;
			default: break;
		}
		
		ShaderProgram program = shader.getProgram(programIndx);

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
		
		float lightCount = ls.size();
		
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

			program.setUniformLight(i, lightPos4, light.getColor(), light.getRange(), light.getIntensity());
		}
		
		program.setUniform(ShaderProgram.PROJECTION_MATRIX, projectionMatrix);
		program.setUniform(ShaderProgram.MODELVIEW_MATRIX, tempTransformMatrix);
		program.setUniform(ShaderProgram.NORMAL_MATRIX, tempNormalMatrix);
		
		int meshBuffer0 = mesh.getBuffer(0);
		ShaderProgram.VariableType attribF = ShaderProgram.VariableType.FLOAT;
		
		program.setAttribPointer(ShaderProgram.VERTEX_ATTRIBUTE, 3, 0, 0, meshBuffer0, attribF);
		program.setAttribPointer(ShaderProgram.NORMAL_ATTRIBUTE, 3, 0, mesh.NORMALS_OFFSET, meshBuffer0, attribF);
		
		if(hasTexture == 2)
		{
			program.setAttribPointer(ShaderProgram.UV_ATTRIBUTE, 2, 0, mesh.UV_OFFSET, meshBuffer0, attribF);
			program.setUniformDiffuseMap(0, texture.getTextureBuffer());
		}
		else
		{
			program.setUniformColor(model.getMaterial().getBaseColor());
		}
		
		if(geometryType == Geometry.SKINNED_MESH && hasAnimation)
		{
			program.setAttrib(ShaderProgram.BONE_COUNT, 4);
			program.setAttribPointer(ShaderProgram.BONE_WEIGHTS_ATTRIBUTE, 4, 0, 0, mesh.getBuffer(2), attribF);
			program.setAttribPointer(ShaderProgram.BONE_INDEXES_ATTRIBUTE, 4, 0, 0, mesh.getBuffer(3), ShaderProgram.VariableType.SHORT);
			
			if(matrixPalette != null)
			{
				program.setUniformMatrix4(ShaderProgram.MATRIX_PALETTE, matrixPalette);
			}
		}
		
		program.drawTriangleElements(mesh.getBuffer(1), mesh.INDICES_COUNT);
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