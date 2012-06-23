package plia.framework.core;

import static android.opengl.GLES20.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;
import plia.framework.math.Matrix4;
import plia.framework.scene.Model;
import plia.framework.scene.Object3D;
import plia.framework.scene.Terrain;
import plia.framework.scene.obj3d.animation.Animation;
import plia.framework.scene.obj3d.geometry.Mesh;
import plia.framework.scene.obj3d.geometry.Plane;
//import plia.framework.scene.obj3d.shading.Color4;
import plia.framework.scene.obj3d.shading.Material;
import plia.framework.scene.obj3d.shading.Shader;
import plia.framework.scene.obj3d.shading.ShaderProgram;
import plia.framework.scene.obj3d.shading.Texture2D;

public class GameObjectManager
{
	private Context context;
	private ArrayList<String> assetFileNames = new ArrayList<String>();
	private HashMap<String, Texture2D> texturesList = new HashMap<String, Texture2D>();
	private HashMap<String, ScenePrefab> scenePrefabs = new HashMap<String, ScenePrefab>();
	
	
	
	private int[] terrainBuffers = new int[2];
	
	public void setContext(Context context)
	{
		this.context = context;
	}
	
	public void initialize()
	{
		this.loadAllFilesInAssets("");
		
		createTerrainBuffer();
	}
	
	public void destroy()
	{
		assetFileNames.clear();
		texturesList.clear();
		scenePrefabs.clear();
	}
	
	private void loadAllFilesInAssets(String foldername)
	{
		String[] filelist = null;
		try
		{
			filelist = context.getAssets().list(foldername);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(filelist != null)
    	{
			ArrayList<String> childfolder = new ArrayList<String>();
    		
    		for (String string : filelist)
			{
    			if(string.contains("."))
				{
    				String filename = "";
    				
    				if(foldername == "")
    				{
    					filename = string;
    				}
    				else
    				{
    					filename = foldername + "/" + string;
    				}
    				
    				assetFileNames.add(filename);
				}
    			else
    			{
    				if(foldername == "")
    				{
    					childfolder.add(string);
    				}
    				else
    				{
    					childfolder.add(foldername + "/" + string);
    				}
    			}
			}
    		
    		for (String string : childfolder)
			{
				loadAllFilesInAssets(string);
			}
    	}
	}
	
	
	
	private static GameObjectManager instance = new GameObjectManager();
	static GameObjectManager getInstance()
	{
		return instance;
	}
	
	public static Texture2D loadTexture2DWithFileName(String filename)
	{
		if(filename != null && !filename.isEmpty())
		{
			for (int j = 0; j < instance.assetFileNames.size(); j++)
			{
				String path = instance.assetFileNames.get(j);
				if(path.contains(filename))
				{
					return loadTexture2D(path);
				}
			}
		}
		
		return null;
	}
	
	public static Texture2D loadTexture2D(String file)
	{
		if(instance.texturesList.containsKey(file))
		{
			return instance.texturesList.get(file);
		}
		
		try
		{
			Bitmap bitmap = BitmapFactory.decodeStream(instance.context.getAssets().open(file));
			
			int[] tex = new int[1];
			
			GLES20.glGenTextures(1, tex, 0);
			
			// generate color texture
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex[0]);

			// parameters
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);

			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
			GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
			
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
			
			String fileName = file;

			int indexOfSlash = file.lastIndexOf("\\");
			
			if(indexOfSlash > -1)
			{
				fileName = file.substring(indexOfSlash+1);
			}
			
			int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
//			Color4[] colors = new Color4[bitmap.getWidth() * bitmap.getHeight()];
			
			bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

//			for (int i = 0; i < pixels.length; i++)
//			{
//				float r = Color.red(pixels[i]) / 255f;
//				float g = Color.green(pixels[i]) / 255f;
//				float b = Color.blue(pixels[i]) / 255f;
//				float a = Color.alpha(pixels[i]) / 255f;
//				colors[i] = new Color4(r, g, b, a);
//			}
			
			Texture2D texture = new Texture2D(fileName, tex[0], pixels, bitmap.getWidth(), bitmap.getHeight());
			instance.texturesList.put(file, texture);
			
			return texture;
		} 
		catch (IOException e)
		{
			Log.e("Error", e.getMessage());
		}
		
		return null;
	}

	public static Object3D loadModel(String fbx_path)
	{
		if(!instance.scenePrefabs.containsKey(fbx_path))
		{
			ScenePrefab scenePrefab = FbxDroid.importScene(fbx_path, instance.context);
			
			if(scenePrefab != null)
			{
				instance.scenePrefabs.put(fbx_path, scenePrefab);
			}	
		}
		//
		
		ScenePrefab scenePrefab = instance.scenePrefabs.get(fbx_path);
		
		ArrayList<Model> models = new ArrayList<Model>();
		Animation animation = scenePrefab.getAnimation();
		
		NodePrefab[] nodePrefabs = scenePrefab.getNodePrefabs();
		
		for (int i = 0; i < nodePrefabs.length; i++)
		{
			NodePrefab nodePrefab = nodePrefabs[i];
			Model mdl = new Model(nodePrefab.getName());
			
			mdl.setGeometry(nodePrefab.getMesh());
			mdl.setMaterial(nodePrefab.getMaterial());
			mdl.setAxisRotation(scenePrefab.getAxisRotation());
			
			if(nodePrefab.hasAnimation())
			{
				mdl.setAnimation(animation);
			}
			
			models.add(mdl);
		}
		
		Object3D object3d = null;
		
		if(models.size() == 1)
		{
			object3d = models.get(0);
			object3d.setName(scenePrefab.getRootName());

		}
		else if(models.size() > 1)
		{
			object3d = new Object3D(scenePrefab.getRootName());
			object3d.setAnimation(animation);

			for (int i = 0; i < models.size(); i++)
			{
				object3d.addChild(models.get(i));
			}
		}
		
		return object3d;
	}
	
	private static void createTerrainBuffer()
	{
		float[] vertices = Plane.getInstance().getVertices();
		int[] indices = Plane.getInstance().getIndices();
		
		FloatBuffer fb = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		fb.put(vertices).position(0);
		
		IntBuffer ib = ByteBuffer.allocateDirect(indices.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
		ib.put(indices).position(0);

		glGenBuffers(instance.terrainBuffers.length, instance.terrainBuffers, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, instance.terrainBuffers[0]);
		glBufferData(GL_ARRAY_BUFFER, fb.capacity() * 4, fb, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, instance.terrainBuffers[1]);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, ib.capacity() * 4, ib, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	private static int[] createMeshBuffer(float[] vertices, float[] normals, float[] uv, int[] indices)
	{
		int[] buffers = new int[2];

		int capacity = (vertices.length + vertices.length + uv.length) * 4;
		
		FloatBuffer fb = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder()).asFloatBuffer();
		fb.put(vertices).put(normals).put(uv).position(0);
		
		IntBuffer ib = ByteBuffer.allocateDirect(indices.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
		ib.put(indices).position(0);

		glGenBuffers(buffers.length, buffers, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
		glBufferData(GL_ARRAY_BUFFER, fb.capacity() * 4, fb, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffers[1]);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, ib.capacity() * 4, ib, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

		return buffers;
	}
	
	private static int[] genBonesBuffer(float[] boneWeights, short[] boneIndices)
	{
		int[] buffers = new int[2];
		FloatBuffer fb = ByteBuffer.allocateDirect(boneWeights.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		fb.put(boneWeights).position(0);
		
		ShortBuffer ib = ByteBuffer.allocateDirect(boneIndices.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
		ib.put(boneIndices).position(0);
		
		glGenBuffers(buffers.length, buffers, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
		glBufferData(GL_ARRAY_BUFFER, fb.capacity() * 4, fb, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, buffers[1]);
		glBufferData(GL_ARRAY_BUFFER, ib.capacity() * 2, ib, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		return buffers;
	}
	
	public Texture2D createTerrainNormalMap(Terrain terrain)
	{
		int segment = Plane.getInstance().getSegment();
		IntBuffer normalmapTextureBuffer = ByteBuffer.allocateDirect(segment*segment*4).order(ByteOrder.nativeOrder()).asIntBuffer();
		
		int[] frameBuffer = new int[1];
		int[] depthRenderBuffer = new int[1];
		int[] renderTextureBuffer = new int[1];
		IntBuffer textureBuffer;

		// generate
		glGenFramebuffers(1, frameBuffer, 0);
		glGenRenderbuffers(1, depthRenderBuffer, 0);
		glGenTextures(1, renderTextureBuffer, 0);

		// generate color texture
		glBindTexture(GL_TEXTURE_2D, renderTextureBuffer[0]);

		// parameters
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

		// create it
		// create an empty intbuffer first?
		int[] buf = new int[segment * segment];
		textureBuffer = ByteBuffer.allocateDirect(buf.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, segment, segment, 0, GL_RGBA, GL_UNSIGNED_BYTE, textureBuffer);

		glGenerateMipmap(GL_TEXTURE_2D);

		glBindRenderbuffer(GL_RENDERBUFFER, depthRenderBuffer[0]);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT16, segment, segment);
		
		// Bind Normal Buffer
		glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer[0]);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, renderTextureBuffer[0], 0);
		
		// attach render buffer as depth buffer
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderBuffer[0]);

		float[] projection = new float[16];
		float[] modelView = new float[16];
		
		int scale = terrain.getTerrainScale();
		int height = terrain.getTerrainMaxHeight();
		
		Matrix.orthoM(projection, 0, 0, scale, -scale, 0, 1, height*2);
		Matrix.setLookAtM(modelView, 0, 0, height*2, 0, 0, 0, 0, 0, 0, -1);

		glViewport(0, 0, segment, segment);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		ShaderProgram sprogram = Shader.AMBIENT.getProgram(7);
		int program = sprogram.getProgramID();
		
		glUseProgram(program);
		
		int vertex_handle = glGetAttribLocation(program, "vertex");
		int modelview_handle = glGetUniformLocation(program, "modelViewMatrix");
		int projection_handle = glGetUniformLocation(program, "projectionMatrix");
		
		int heightmap_handle = glGetUniformLocation(program, "heightmap");
		int terrainData_handle = glGetUniformLocation(program, "terrainData");
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, terrain.getHeightmap().getTextureBuffer());
		glUniform1i(heightmap_handle, 0);
		
		glUniform3f(terrainData_handle, height, segment, scale);

		glUniformMatrix4fv(modelview_handle, 1, false, modelView, 0);
		glUniformMatrix4fv(projection_handle, 1, false, projection, 0);

		glBindBuffer(GL_ARRAY_BUFFER, terrainBuffers[0]);
		glEnableVertexAttribArray(vertex_handle);
		glVertexAttribPointer(vertex_handle, 2, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, terrainBuffers[1]);
		glDrawElements(GL_TRIANGLES, Plane.getInstance().getIndicesCount(), GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(vertex_handle);
		
		// Unbind Normal
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
		// Bind Normal Again
		// Bind Normal Buffer
		glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer[0]);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, renderTextureBuffer[0], 0);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderBuffer[0]);
		// Read Pixels
		glReadPixels(0, 0, segment, segment, GL_RGBA, GL_UNSIGNED_BYTE, normalmapTextureBuffer);
		// Unbind Normal
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		
		int[] pixels = normalmapTextureBuffer.array();
		
		Texture2D normalmap = new Texture2D("normals", renderTextureBuffer[0], pixels, segment, segment);
		
		return normalmap;
	}
	
	private class MeshPrefab
	{
		private String rootName;
		private String name;
		private Mesh mesh;
		private boolean hasAnimation;
		private Material material;
		private Matrix4 axisRotation;
//		private 
	}
}
