package plia.framework.core;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glBufferData;
import static android.opengl.GLES20.glGenBuffers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import plia.framework.scene.Model;
import plia.framework.scene.Object3D;
import plia.framework.scene.obj3d.animation.Animation;
import plia.framework.scene.obj3d.geometry.Mesh;
import plia.framework.scene.obj3d.geometry.SkinnedMesh;
//import plia.framework.scene.obj3d.shading.Color4;
import plia.framework.scene.obj3d.shading.Material;
import plia.framework.scene.obj3d.shading.Shader;
import plia.framework.scene.obj3d.shading.Texture2D;

public class GameObjectManager
{
	private Context context;
	private ArrayList<String> assetFileNames = new ArrayList<String>();
	
	public void setContext(Context context)
	{
		this.context = context;
	}
	
	public void initialize()
	{
		this.loadAllFilesInAssets("");
	}
	
	public void destroy()
	{
		assetFileNames.clear();
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
	
	public static Texture2D loadTexture2D(String file)
	{
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
		FbxDroid[] droids = FbxDroid.importScene(fbx_path, instance.context);
		
		ArrayList<Model> models = new ArrayList<Model>();

		boolean hasAnimation = false;
		int fps = 30;

		for (int i = 0; i < droids.length; i++)
		{
			FbxDroid droid = droids[i];
			Model mdl = new Model(droid.getName());
			Mesh mesh = null;

			int[] meshBuffers = createMeshBuffer(droid.getVertices(), droid.getNormals(), droid.getUV(), droid.getIndices());

			if(droid.isSkinnedMesh())
			{
				int[] boneBuffers = genBonesBuffer(droid.getBoneWeights(), droid.getBoneIndices());
				mesh = new SkinnedMesh(droid.getNormalOffset(), droid.getUVOffset(), droid.getIndices().length);
				mesh.setBuffer(2, boneBuffers[0]);
				mesh.setBuffer(3, boneBuffers[1]);
			}
			else
			{
				mesh = new Mesh(droid.getNormalOffset(), droid.getUVOffset(), droid.getIndices().length);
			}
			
			mesh.setBuffer(0, meshBuffers[0]);
			mesh.setBuffer(1, meshBuffers[1]);
			
			mdl.setGeometry(mesh);

//			Log.e(droid.getName(), droid.hasAnimation()+"");
			if(droid.hasAnimation())
			{
				mesh.setMatrixPalette(droid.getMatrixPalette());
				mesh.setMatrixPaletteIndexOffset(droid.getStartFrame());
				mdl.setHasAnimation(true);
				
				hasAnimation = true;
				fps = droid.getFrameRate();
//				mdl.setAnimation(new Animation(mdl, droid.getStartFrame(), droid.getTotalFrame()));
//				mdl.getAnimation().setFrameRate(droid.getFrameRate());
			}
			else
			{
				mdl.setPosition(droid.getDefaultTranslation());
				mdl.setEulerAngles(droid.getDefaultRotation());
				mdl.setScale(droid.getDefaultScaling());
			}
			
//			mesh.setScaling(droid.getDefaultScaling());
//			mesh.setTranslation(droid.getDefaultTranslation());
//			mesh.setRotation(droid.getDefaultRotation());
//			
//			float[] TRS = new float[16];
//			Vector3 translation = mesh.getTranslation();
//			Vector3 rotation = mesh.getRotation();
//			Vector3 scaling = mesh.getScaling();
//			
//			Matrix.createTRS_zyx(TRS, translation.x, translation.y, translation.z, rotation.x, rotation.y, rotation.z, scaling.x, scaling.y, scaling.z);
//			
//			Vector4 bbmin = new Vector4();
//			Vector4 bbmax = new Vector4();
//			
//			Matrix.multiply(bbmin, TRS, new Vector4(droid.getMin().x, droid.getMin().y, droid.getMin().z, 0));
//			Matrix.multiply(bbmax, TRS, new Vector4(droid.getMax().x, droid.getMax().y, droid.getMax().z, 0));
//
//			Vector4 size = Vector4.subtract(bbmax, bbmin);
//			Vector4 extents = Vector4.scale(size, 0.5f);
//			Vector4 center = Vector4.add(extents, bbmin);
//			
//			mesh.getBounds().set(new Vector3(bbmin.x, bbmin.y, bbmin.z), new Vector3(bbmax.x, bbmax.y, bbmax.z));
//			mesh.getBounds().setCenter(center.x, center.y, center.z);
			
//			Log.e("", Matrix.toString(TRS));
//			Log.e(droid.getMin().toString(), droid.getMax().toString());
//			Log.e(bbmin.toString(), bbmax.toString());

			Material material = new Material();
			material.setShader(Shader.DIFFUSE);
			
//			Vector3 baseColor = droid.getBaseColor();
//			material.setBaseColor(baseColor.x, baseColor.y, baseColor.z);
			
			long start = System.nanoTime();

			
			String textureFileName = droid.getTextureFileName();
			if(textureFileName != null && !textureFileName.isEmpty())
			{
				for (int j = 0; j < instance.assetFileNames.size(); j++)
				{
					String path = instance.assetFileNames.get(j);
					if(path.contains(textureFileName))
					{
						Texture2D texture = loadTexture2D(path);
						
						if(texture != null)
						{
							material.setBaseTexture(texture);
							
						}
						
						break;
					}
				}
			}
			
			float end = (System.nanoTime() - start)/ 1000000f;
			Log.e("Load Time", end+" ms");
			
			mdl.setMaterial(material);
			models.add(mdl);
		}
		
		Object3D object3d = null;
		
		if(models.size() == 1)
		{
			object3d = models.get(0);
			object3d.setName(droids[0].getRootName());
			object3d.setAxisRotation(droids[0].getAxisRotation());

		}
		else if(models.size() > 1)
		{
			object3d = new Object3D(droids[0].getRootName());
			object3d.setAxisRotation(droids[0].getAxisRotation());

			for (int i = 0; i < models.size(); i++)
			{
				object3d.addChild(models.get(i));
			}
		}
		
		if(object3d != null && hasAnimation)
		{
			Animation animation = new Animation(0, 100);
			animation.setFrameRate(fps);
			object3d.setAnimation(animation);
			
			for (int i = 0; i < models.size(); i++)
			{
				Model model = models.get(i);
				if(model.hasAnimation())
				{
					model.setAnimation(animation);
				}
			}
		}

		return object3d;
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
	
//	private class Prefab
//	{
//		private Mesh mesh;
//		private Animation animation;
////		private 
//	}
}
