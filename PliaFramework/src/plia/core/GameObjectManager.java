package plia.core;

import static android.opengl.GLES20.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;
//import plia.framework.scene.obj3d.shading.Color4;
import plia.core.scene.Button;
import plia.core.scene.DisplacementTerrain;
import plia.core.scene.Group;
import plia.core.scene.Model;
import plia.core.scene.Sprite;
import plia.core.scene.StaticTerrain;
import plia.core.scene.Terrain;
import plia.core.scene.animation.Animation;
import plia.core.scene.geometry.Plane;
import plia.core.scene.geometry.Quad;
import plia.core.scene.shading.Shader;
import plia.core.scene.shading.ShaderProgram;
import plia.core.scene.shading.Texture2D;

public class GameObjectManager
{
	private Context context;
	private ArrayList<String> assetFileNames = new ArrayList<String>();
	private HashMap<String, Texture2D> texturesList = new HashMap<String, Texture2D>();
	
	private HashMap<String, Bitmap> bitmapList = new HashMap<String, Bitmap>();
	private HashMap<String, ScenePrefab> scenePrefabs = new HashMap<String, ScenePrefab>();
	
	private ArrayList<Terrain> terrains = new ArrayList<Terrain>();
	
	private ArrayList<Terrain> getNormalQueue = new ArrayList<Terrain>();
//	private ArrayList<Terrain> getTextureQueue = new ArrayList<Terrain>();
	
	private ArrayList<Texture2D> textsTex = new ArrayList<Texture2D>(); 
	private ArrayList<Bitmap> textsBmp = new ArrayList<Bitmap>();
	
	private int[] terrainBuffers = new int[2];
	
	private boolean isInitialized = false;
	
	private boolean isUpdateState = false;
	
	public void setContext(Context context)
	{
		this.context = context;
	}
	
	public void start()
	{
		createTerrainBuffer();
		Quad.createBuffer();
	}
	
	public void initialize()
	{
		if(!isInitialized)
		{
			Log.e("OM", "On Initialize");
			this.loadAllFilesInAssets("");
			isInitialized = true;
		}
	}
	
	public void resume()
	{
		Log.e("OM", "On Resume");
		
		for (ScenePrefab prefab : scenePrefabs.values())
		{
			prefab.resume();
		}
		
		for (Terrain terrain : terrains)
		{
			if(terrain instanceof StaticTerrain)
			{
				((StaticTerrain) terrain).getMesh().resume();
			}
		}
		
		for (Terrain terrain : getNormalQueue)
		{
//			createTerrainHeightMap(terrain);
			createTerrainNormalMap(terrain);
		}

		String[] bitmapKeys = new String[bitmapList.size()];
		bitmapList.keySet().toArray(bitmapKeys);
		
		for (int i = 0; i < bitmapKeys.length; i++)
		{
			String key = bitmapKeys[i];
			Bitmap bitmap = bitmapList.get(key);
			
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
			
			if(texturesList.containsKey(key))
			{
				Texture2D texture2d = texturesList.get(key);
				texture2d.setTextureBuffer(tex[0]);
				Log.e("Tex2D : "+texture2d.hashCode(), "On Resume");
			}
		}
	}
	
	public void update()
	{
		if(!isUpdateState)
		{
			isUpdateState = true;
		}
		
		int textsSize = textsTex.size();
		for (int i = 0; i < textsSize; i++)
		{
			Texture2D texture = textsTex.remove(0);
			Bitmap bitmap = textsBmp.remove(0);
			
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
			
			texture.setTextureBuffer(tex[0]);
		}
	}
	
	public void destroy()
	{
		Log.e("OM", "On Destroy");
		assetFileNames.clear();
		
		for (Texture2D texture2d : texturesList.values())
		{
			texture2d.destroy();
		}
		texturesList.clear();
		
		terrains.clear();
		
		getNormalQueue.clear();
		
		for (ScenePrefab scenePrefab : scenePrefabs.values())
		{
			scenePrefab.destroy();
		}
		
		scenePrefabs.clear();

//		for (Bitmap bitmap : bitmapList.values())
//		{
//			bitmap.
//		}
		
		bitmapList.clear();
		
		isInitialized = false;
		instance = null;
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
			Log.e("Error", e.getMessage());
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
	
	
	
	private static GameObjectManager instance;
	static GameObjectManager getInstance()
	{
		if(instance == null)
		{
			instance = new GameObjectManager();
		}
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
			
//			int[] tex = new int[1];
//			
//			GLES20.glGenTextures(1, tex, 0);
//			
//			// generate color texture
//			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex[0]);
//
//			// parameters
//			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
//			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
//			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
//			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
//
//			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
//			GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
//			
//			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
			
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
			
			Texture2D texture = new Texture2D(fileName, -1, pixels, bitmap.getWidth(), bitmap.getHeight());
			instance.texturesList.put(file, texture);
			instance.bitmapList.put(file, bitmap);
			
			return texture;
		} 
		catch (IOException e)
		{
			Log.e("Error", e.getMessage());
		}
		
		return null;
	}
	
	public static Terrain createTerrain(String heightmapSrc, int maxHeight, int scale)
	{
		Texture2D heightmap = loadTexture2D(heightmapSrc);

		Terrain terrain = new DisplacementTerrain(heightmap, maxHeight, scale);
		
		instance.getNormalQueue.add(terrain);
		

		return terrain;
	}
	
	public static Terrain createStaticTerrain(String heightmapSrc, int maxHeight, int scale, int segment)
	{
		Texture2D heightmap = loadTexture2D(heightmapSrc);

//		Terrain terrain = new DisplacementTerrain(heightmap, maxHeight, scale);
		StaticTerrain terrain = new StaticTerrain(heightmap, maxHeight, scale, segment);
		instance.getNormalQueue.add(terrain);
		
		instance.terrains.add(terrain);

		return terrain;
	}
	
	public static Texture2D createText(String text, float textSize, int textColor)
	{
		Paint paint = new Paint();
	    paint.setTextSize(textSize);
	    paint.setColor(textColor);
	    paint.setTextAlign(Paint.Align.CENTER);
	    
	    Rect bounds = new Rect();
	    paint.getTextBounds("  "+text+"  ", 0, text.length()+4, bounds);
	    
	    int width = bounds.width();
	    int height = bounds.height();
	    
	    int x = (width / 2);
	    int y = Math.abs(bounds.top);

	    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap);
	    canvas.drawText(text, x, y, paint);
		
		int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
	    bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
	    
	    Texture2D texture = new Texture2D(text, -1, pixels, bitmap.getWidth(), bitmap.getHeight());
	    
	    if(instance.isUpdateState)
	    {
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
			
			texture.setTextureBuffer(tex[0]);
	    }
	    else
	    {
	    	instance.textsTex.add(texture);
		    instance.textsBmp.add(bitmap);
	    }
	    
	    return texture;
	}

	public static Group loadModel(String fbx_path)
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
		
		Group object3d = null;
		
		if(models.size() == 1)
		{
			object3d = models.get(0);
			object3d.setName(scenePrefab.getRootName());

		}
		else if(models.size() > 1)
		{
			object3d = new Group(scenePrefab.getRootName());
			object3d.setAnimation(animation);

			for (int i = 0; i < models.size(); i++)
			{
				object3d.addChild(models.get(i));
			}
		}
		
		return object3d;
	}
	
	public static Sprite createSprite()
	{
		return new Sprite();
	}
	
	public static Sprite createSprite(String imgSrcFileName)
	{
		Texture2D texture2d = loadTexture2D(imgSrcFileName);
		
		Sprite imageView = new Sprite();
		imageView.setImageSrc(texture2d);
		
		return imageView;
	}
	
	public static Button createButton()
	{
		return new Button();
	}
	
	public static Button createButton(String imgSrcFileName)
	{
		Texture2D texture2d = loadTexture2D(imgSrcFileName);
		
		Button imageView = new Button();
		imageView.setImageSrc(texture2d);
		
		return imageView;
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
		
		Terrain.setTerrainBuffer(instance.terrainBuffers);
	}
	
//	private static Texture2D createTerrainHeightMap(Terrain terrain)
//	{
////		glEnable(GL_DEPTH_TEST);
////		glEnable(GL_CULL_FACE);
//		
//		int segment = Plane.getInstance().getSegment();
//		IntBuffer normalmapTextureBuffer = ByteBuffer.allocateDirect(segment*segment*4).order(ByteOrder.nativeOrder()).asIntBuffer();
//		
//		int[] frameBuffer = new int[1];
//		int[] depthRenderBuffer = new int[1];
//		int[] renderTextureBuffer = new int[1];
//		IntBuffer textureBuffer;
//
//		// generate
//		glGenFramebuffers(1, frameBuffer, 0);
//		glGenRenderbuffers(1, depthRenderBuffer, 0);
//		glGenTextures(1, renderTextureBuffer, 0);
//
//		// generate color texture
//		glBindTexture(GL_TEXTURE_2D, renderTextureBuffer[0]);
//
//		// parameters
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//
//		// create it
//		// create an empty intbuffer first?
//		int[] buf = new int[segment * segment];
//		textureBuffer = ByteBuffer.allocateDirect(buf.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
//
//		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, segment, segment, 0, GL_RGBA, GL_UNSIGNED_BYTE, textureBuffer);
//
//		glGenerateMipmap(GL_TEXTURE_2D);
//
//		glBindRenderbuffer(GL_RENDERBUFFER, depthRenderBuffer[0]);
//		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT16, segment, segment);
//		
//		// Bind Normal Buffer
//		glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer[0]);
//		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, renderTextureBuffer[0], 0);
//		
//		// attach render buffer as depth buffer
//		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderBuffer[0]);
//
//		float[] projection = new float[16];
//		float[] modelView = new float[16];
//		float[] mvp = new float[16];
//		
//		int scale = terrain.getTerrainScale();
//		int height = terrain.getTerrainMaxHeight();
//		
//		Matrix.orthoM(projection, 0, 0, segment, 0, segment, 1, 10);
//		Matrix.setLookAtM(modelView, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0);
//		Matrix.multiplyMM(mvp, 0, projection, 0, modelView, 0);
//
//		glViewport(0, 0, segment, segment);
//		glClearColor(0.3f, 0.6f, 0.9f, 1);
//		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//		
//		ShaderProgram sprogram = Shader.AMBIENT.getProgram(7);
//		int program = sprogram.getProgramID();
//		
//		glUseProgram(program);
//		
//		glUniform1f(glGetUniformLocation(program, "gH"), 1);
//		
//		int vertex_handle = glGetAttribLocation(program, "vertex");
//		int mvp_handle = glGetUniformLocation(program, "modelViewProjectionMatrix");
//		
//		int heightmap_handle = glGetUniformLocation(program, "heightmap");
//		int terrainData_handle = glGetUniformLocation(program, "terrainData");
//		
//		glActiveTexture(GL_TEXTURE0);
//		glBindTexture(GL_TEXTURE_2D, terrain.getHeightmap().getTextureBuffer());
//		glUniform1i(heightmap_handle, 0);
//		
//		glUniform3f(terrainData_handle, height, segment, scale);
//
//		glUniformMatrix4fv(mvp_handle, 1, false, mvp, 0);
//		
//		glBindBuffer(GL_ARRAY_BUFFER, instance.terrainBuffers[0]);
//		glEnableVertexAttribArray(vertex_handle);
//		glVertexAttribPointer(vertex_handle, 2, GL_FLOAT, false, 0, 0);
//
//		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, instance.terrainBuffers[1]);
//		glDrawElements(GL_TRIANGLES, Plane.getInstance().getIndicesCount(), GL_UNSIGNED_INT, 0);
//		
//		glBindBuffer(GL_ARRAY_BUFFER, 0);
//		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
//		
//		glDisableVertexAttribArray(vertex_handle);
//		
//		// Unbind Normal
//		glBindFramebuffer(GL_FRAMEBUFFER, 0);
//		
//		// Bind Normal Again
//		// Bind Normal Buffer
//		glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer[0]);
//		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, renderTextureBuffer[0], 0);
//		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderBuffer[0]);
//		// Read Pixels
//		glReadPixels(0, 0, segment, segment, GL_RGBA, GL_UNSIGNED_BYTE, normalmapTextureBuffer);
//		// Unbind Normal
//		glBindFramebuffer(GL_FRAMEBUFFER, 0);
//		glBindTexture(GLES20.GL_TEXTURE_2D, 0);
//		
//		int[] pixels = new int[normalmapTextureBuffer.capacity()];
//		for (int i = 0; i < normalmapTextureBuffer.capacity(); i++)
//		{
//			pixels[i] = normalmapTextureBuffer.get(i);
////			Log.e(i+"", Color.red(pixels[i])+", "+Color.green(pixels[i])+", "+Color.blue(pixels[i]));
//		}
//		
//		Bitmap bitmap = Bitmap.createBitmap(pixels, segment, segment, Config.RGB_565);
//
//		Texture2D heightmap = new Texture2D("heights", renderTextureBuffer[0], pixels, segment, segment);
//		Terrain.setHeightMapTo(terrain, heightmap);
//		
//		String key = ""+terrain.hashCode();
//		
//		instance.texturesList.put(key, heightmap);
//		instance.bitmapList.put(key, bitmap);
//		
//		return heightmap;
//	}
	
	private static Texture2D createTerrainNormalMap(Terrain terrain)
	{
//		glEnable(GL_DEPTH_TEST);
//		glEnable(GL_CULL_FACE);
		
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
		float[] mvp = new float[16];
		
		int scale = terrain.getTerrainScale();
		int height = terrain.getTerrainMaxHeight();
		
		Matrix.orthoM(projection, 0, 0, segment, 0, segment, 1, 10);
		Matrix.setLookAtM(modelView, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0);
		Matrix.multiplyMM(mvp, 0, projection, 0, modelView, 0);

		glViewport(0, 0, segment, segment);
		glClearColor(0.3f, 0.6f, 0.9f, 1);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		ShaderProgram sprogram = Shader.AMBIENT.getProgram(7);
		int program = sprogram.getProgramID();
		
		glUseProgram(program);
		glUniform1f(glGetUniformLocation(program, "gH"), 0);
		
		int vertex_handle = glGetAttribLocation(program, "vertex");
		int mvp_handle = glGetUniformLocation(program, "modelViewProjectionMatrix");
		
		int heightmap_handle = glGetUniformLocation(program, "heightmap");
		int terrainData_handle = glGetUniformLocation(program, "terrainData");
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, terrain.getHeightmap().getTextureBuffer());
		glUniform1i(heightmap_handle, 0);
		
		glUniform3f(terrainData_handle, height, segment, scale);

		glUniformMatrix4fv(mvp_handle, 1, false, mvp, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, instance.terrainBuffers[0]);
		glEnableVertexAttribArray(vertex_handle);
		glVertexAttribPointer(vertex_handle, 2, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, instance.terrainBuffers[1]);
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
		glBindTexture(GLES20.GL_TEXTURE_2D, 0);
		
		int[] pixels = new int[normalmapTextureBuffer.capacity()];
		for (int i = 0; i < normalmapTextureBuffer.capacity(); i++)
		{
			pixels[i] = normalmapTextureBuffer.get(i);
//			Log.e(i+"", Color.red(pixels[i])+", "+Color.green(pixels[i])+", "+Color.blue(pixels[i]));
		}
		
		Bitmap bitmap = Bitmap.createBitmap(pixels, segment, segment, Config.RGB_565);

		Texture2D normalmap = new Texture2D("normals", renderTextureBuffer[0], pixels, segment, segment);
		Terrain.setNormalMapTo(terrain, normalmap);
		
		String key = ""+terrain.hashCode();
		
		instance.texturesList.put(key, normalmap);
		instance.bitmapList.put(key, bitmap);
		
		return normalmap;
	}
}
