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
import plia.framework.scene.obj3d.shading.ShaderProgram.VariableType;
import plia.framework.scene.obj3d.shading.Texture2D;

@SuppressWarnings({"rawtypes"})
public abstract class Scene extends GameObject implements IScene
{
	private Layer[] children = new Layer[32];
	private int childCount = 0;
	private boolean isInitialized = false;
	
	private long start = System.nanoTime();
	
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
			start = System.nanoTime();
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
//				log("Set Frustum");
			}
			else
			{
				Matrix4.createOrtho(projectionMatrix, -ratio, ratio, -1, 1, 1, mainCamera.getRange());
//				log("Set Ortho");
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
//			log("Set LookAt");
			
			hasChangedModelView = false;
		}
		
		Matrix4.multiply(modelViewProjectionMatrix, projectionMatrix, modelViewMatrix);

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

		float end = (System.nanoTime() - start) / 1000000f;
		Log.d("Usage Time", (1000f / end)+" ms");
		start = System.nanoTime();
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

		int prg = program.getProgramID();
		GLES20.glUseProgram(prg);
		GLES20.glUniform1f(GLES20.glGetUniformLocation(prg, "lightCount"), lightCount);
//		program.use();
//		program.setUniform(ShaderProgram.LIGHT_COUNT, lightCount);

		for (int i = 0; i < lightCount; i++)
		{
			Light light = ls.get(i);
			int lt = light.getLightType();
			if(lt == Light.DIRECTIONAL_LIGHT)
			{
				Vector3 forward = light.getForward();
				lightPosTemp.set(-forward.x, -forward.y, -forward.z, 0);
			}
			else
			{
				lightPosTemp.set(light.getPosition(), 1);
			}
			
			Matrix4.multiply(lightPos4, modelViewMatrix, lightPosTemp);
			GLES20.glUniform4f(GLES20.glGetUniformLocation(prg, "lightPosition["+i+"]"), lightPos4.x, lightPos4.y, lightPos4.z, lt);
			
			Color3 color = light.getColor();
			GLES20.glUniform4f(GLES20.glGetUniformLocation(prg, "lightColor["+i+"]"), color.r, color.g, color.b, 1);
			
			GLES20.glUniform1f(GLES20.glGetUniformLocation(prg, "lightRange["+i+"]"), light.getRange());
			GLES20.glUniform1f(GLES20.glGetUniformLocation(prg, "lightIntensity["+i+"]"), light.getIntensity());
			
//			program.setUniformLight(i, lightPos4, light.getColor(), light.getRange(), light.getIntensity());
		}
		
		float[] tm = new float[16];
		projectionMatrix.copyTo(tm);
		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(prg, "projectionMatrix"), 1, false, tm, 0);
		
		tempTransformMatrix.copyTo(tm);
		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(prg, "modelViewMatrix"), 1, false, tm, 0);
		
		tempNormalMatrix.copyTo(tm);
		GLES20.glUniformMatrix3fv(GLES20.glGetUniformLocation(prg, "normalMatrix"), 1, false, tm, 0);
		
//		program.setUniform(ShaderProgram.PROJECTION_MATRIX, projectionMatrix);
//		program.setUniform(ShaderProgram.MODELVIEW_MATRIX, tempTransformMatrix);
//		program.setUniform(ShaderProgram.NORMAL_MATRIX, tempNormalMatrix);

		int vh = GLES20.glGetAttribLocation(prg, "vertex");
		int nh = GLES20.glGetAttribLocation(prg, "normal");
		int uvh = GLES20.glGetAttribLocation(prg, "uv");
		
		int bwh = GLES20.glGetAttribLocation(prg, "boneWeights");
		int bih = GLES20.glGetAttribLocation(prg, "boneIndices");
		int bch = GLES20.glGetAttribLocation(prg, "boneCount");
		
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getBuffer(0));
		GLES20.glEnableVertexAttribArray(vh);
		GLES20.glVertexAttribPointer(vh, 3, GLES20.GL_FLOAT, false, 0, 0);
		
		GLES20.glEnableVertexAttribArray(nh);
		GLES20.glVertexAttribPointer(nh, 3, GLES20.GL_FLOAT, false, 0, mesh.NORMALS_OFFSET);
		
//		program.setAttribPointer(ShaderProgram.VERTEX_ATTRIBUTE, 3, 0, 0, mesh.getBuffer(0), VariableType.FLOAT);
//		program.setAttribPointer(ShaderProgram.NORMAL_ATTRIBUTE, 3, 0, mesh.NORMALS_OFFSET, mesh.getBuffer(0), VariableType.FLOAT);

		if(hasTexture == 2)
		{
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.getTextureBuffer());
			GLES20.glUniform1i(GLES20.glGetUniformLocation(prg, "diffuseMap"), 0);
			
			GLES20.glEnableVertexAttribArray(uvh);
			GLES20.glVertexAttribPointer(uvh, 2, GLES20.GL_FLOAT, false, 0, mesh.UV_OFFSET);
			
//			program.setAttribPointer(ShaderProgram.UV_ATTRIBUTE, 2, 0, mesh.UV_OFFSET, mesh.getBuffer(0), VariableType.FLOAT);
//			program.setUniformDiffuseMap(ShaderProgram.DIFFUSE_MAP, texture.getTextureBuffer());
		}
		else
		{
			Color3 baseColor3 = model.getMaterial().getBaseColor();
			GLES20.glUniform4f(GLES20.glGetUniformLocation(prg, "color"), baseColor3.r, baseColor3.g, baseColor3.b, 1);
			
//			program.setUniformColor(model.getMaterial().getBaseColor());
		}
		
		if(geometryType == Geometry.SKINNED_MESH && hasAnimation)
		{
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getBuffer(2));
			GLES20.glEnableVertexAttribArray(bwh);
			GLES20.glVertexAttribPointer(bwh, 4, GLES20.GL_FLOAT, false, 0, 0);
			
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mesh.getBuffer(3));
			GLES20.glEnableVertexAttribArray(bih);
			GLES20.glVertexAttribPointer(bih, 4, GLES20.GL_SHORT, false, 0, 0);

			GLES20.glVertexAttrib1f(bch, 4);
			
//			program.setAttribPointer(ShaderProgram.BONE_WEIGHTS_ATTRIBUTE, 4, 0, 0, mesh.getBuffer(2), VariableType.FLOAT);
//			program.setAttribPointer(ShaderProgram.BONE_INDEXES_ATTRIBUTE, 4, 0, 0, mesh.getBuffer(3), VariableType.SHORT);
//			program.setAttrib(ShaderProgram.BONE_COUNT, 4);
			
			if(matrixPalette != null)
			{
				int mp = GLES20.glGetUniformLocation(prg, "matrixPalette");
				GLES20.glUniformMatrix4fv(mp, matrixPalette.length / 16, false, matrixPalette, 0);
				
//				program.setUniformMatrix4(ShaderProgram.MATRIX_PALETTE, matrixPalette);
			}
		}
		
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mesh.getBuffer(1));
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, mesh.INDICES_COUNT, GLES20.GL_UNSIGNED_INT, 0);
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		GLES20.glDisableVertexAttribArray(vh);
		GLES20.glDisableVertexAttribArray(nh);
		GLES20.glDisableVertexAttribArray(uvh);
		GLES20.glDisableVertexAttribArray(bwh);
		GLES20.glDisableVertexAttribArray(bih);
		
//		program.drawTriangleElements(mesh.getBuffer(1), mesh.INDICES_COUNT);
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