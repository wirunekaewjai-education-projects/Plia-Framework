package plia.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import plia.core.math.Matrix4;
import plia.core.math.Vector3;
import plia.fbxplugin.fileio.FbxImporter;
import plia.fbxplugin.scene.FbxScene;
import plia.fbxplugin.scene.animation.FbxAnimCurveNode;
import plia.fbxplugin.scene.geometry.FbxCluster;
import plia.fbxplugin.scene.geometry.FbxMesh;
import plia.fbxplugin.scene.geometry.FbxNode;
import plia.fbxplugin.scene.geometry.FbxSkin;
import plia.fbxplugin.scene.shading.FbxFileTexture;
import plia.fbxplugin.scene.shading.FbxSurfaceLambert;
import plia.fbxplugin.scene.shading.FbxSurfaceMaterial;
import plia.fbxplugin.scene.shading.FbxTexture;
import plia.scene.animation.Animation;
import plia.scene.geometry.Mesh;
import plia.scene.geometry.SkinnedMesh;
import plia.scene.shading.Material;
import plia.scene.shading.Shader;
import plia.scene.shading.Texture2D;
import android.content.Context;
import android.util.Log;

public class FbxDroid
{
	
	
	public static ScenePrefab importScene(String fbx, Context context)
	{
		ScenePrefab scenePrefab = new ScenePrefab();
		
		try
		{

			String filename = fbx.substring(0, fbx.length() - 4);
			int indexOfSlash = filename.lastIndexOf("/");
			if(indexOfSlash > -1)
			{
				filename = filename.substring(indexOfSlash+1, filename.length());
			}
			
			
			
			Log.e("=================", "=====================");
			Log.e("Fbx Data", " ");
			Log.e("", "Root Name      : "+filename);
			
//			long start = System.nanoTime();
			
			FbxScene scene = FbxImporter.importScene(context.getAssets().open(fbx));

//			float end = (System.nanoTime() - start)/ 1000000f;
//			Log.e("Load Time", end+" ms");
			
			int fps = scene.globalSetting().getTimeMode().getFrameRate();
			
			float[] preRotation = { 1,0,0,0,	0,1,0,0,	0,0,1,0,	0,0,0,1 };
			
			Vector3 forward = new Vector3();
			Vector3 up = new Vector3();
			Vector3 right = new Vector3();
			
			int frontAxisSign = scene.globalSetting().getFrontAxisSign();
			
			switch(scene.globalSetting().getFrontAxis())
			{
				case 0 : forward.x = frontAxisSign; break;
				case 1 : forward.y = frontAxisSign; break;
				case 2 : forward.z = frontAxisSign; break;
			}
			
			int upAxisSign = scene.globalSetting().getUpAxisSign();
			
			switch(scene.globalSetting().getUpAxis())
			{
				case 0 : up.x = upAxisSign; break;
				case 1 : up.y = upAxisSign; break;
				case 2 : up.z = upAxisSign; break;
			}
			
			Vector3.cross(right, forward, up);
			
			preRotation[0] = right.x;
			preRotation[1] = right.y;
			preRotation[2] = right.z;
			
			preRotation[4] = forward.x;
			preRotation[5] = forward.y;
			preRotation[6] = forward.z;
			
			preRotation[8] = up.x;
			preRotation[9] = up.y;
			preRotation[10] = up.z;
			
			Matrix4 axisRotation = new Matrix4(preRotation);
			
			scenePrefab.setRootName(filename);
			scenePrefab.setAxisRotation(axisRotation);
			
			boolean hasAnimation = false;
			
//			Log.e("Rotation : "+filename, Matrix.toString(preRotation));
			
			int materialCount = scene.getMaterialCount();
			Material[] materials = new Material[materialCount];
			scenePrefab.setMaterials(materials);
			
			for (int i = 0; i < materialCount; i++)
			{
				FbxSurfaceMaterial surfaceMaterial = scene.getMaterial(i);
				materials[i] = new Material();
				materials[i].setShader(Shader.DIFFUSE);
				
				if(surfaceMaterial instanceof FbxSurfaceLambert)
				{
					Vector3 diffuse = ((FbxSurfaceLambert) surfaceMaterial).getDiffuse();
					materials[i].setBaseColor(diffuse.x, diffuse.y, diffuse.z);

					FbxTexture texture = ((FbxSurfaceLambert) surfaceMaterial).getDiffuseTexture();
					if(texture instanceof FbxFileTexture)
					{
						String textureFileName = ((FbxFileTexture) texture).getFileName();
						
						Texture2D texture2d = GameObjectManager.loadTexture2DWithFileName(textureFileName);
						
						if(texture2d != null)
						{
							materials[i].setBaseTexture(texture2d);
						}
					}
				}
			}
			
			int geometryCount = scene.getGeometryCount();
			if(geometryCount > 0)
			{
				Log.e("", "Geometry Count : "+geometryCount);
				Log.e("=================", "=====================");
				
				
				scenePrefab.setNodePrefabs(new NodePrefab[geometryCount]);
				
				FbxDroid[] datas = new FbxDroid[geometryCount];
				for (int i = 0; i < geometryCount; i++)
				{
					datas[i] = new FbxDroid(scene, scenePrefab, (FbxMesh) scene.getGeometry(i));
					scenePrefab.getNodePrefabs()[i] = datas[i].nodePrefab;

					if(datas[i].hasAnimation)
					{
						hasAnimation = true;
					}
					
					Log.e("Geometry No. : "+i, "Name           : "+scene.getGeometry(i).getNode(0).getName());
					Log.e("Mesh"			 , " ");
					Log.e(""				 , "Vertices       : "+datas[i].vertices.length);
					Log.e(""				 , "Normals        : "+datas[i].normals.length);
					Log.e(""				 , "UV             : "+datas[i].uv.length);
					Log.e(""				 , "Indices        : "+datas[i].indices.length);
					
					if(datas[i].isSkinnedMesh)
					{
						Log.e("Skinned Mesh", " ");
						Log.e("", "Bone Weights   : "+datas[i].boneWeights.length);
						Log.e("", "Bone Indices   : "+datas[i].boneIndices.length);
					}
					
					if(datas[i].hasAnimation)
					{
						Log.e("Animation", "  ");
						Log.e("", "First Keyframe : "+datas[i].startFrame);
						Log.e("", "Last Keyframe  : "+datas[i].endFrame);
						Log.e("", "Total Keyframe : "+datas[i].totalFrame);
					}
					Log.e("=================", "=====================");
				}
				
				
				if(hasAnimation)
				{
					Animation animation = new Animation(0, 100);
					animation.setFrameRate(fps);

					scenePrefab.setAnimation(animation);
				}
				
				return scenePrefab;
			}
			else
			{
				Log.e("=================", "=====================");
			}
		}
		catch (IOException e)
		{
			Log.e("Error", e.getMessage());
		}
		
		return null;
	}
	
	//
	private FbxMesh mesh;

	// Scene
	private FbxScene scene;
	
	// Type
	private boolean isSkinnedMesh = false;
	private boolean hasAnimation = false;
	
	// Mesh
	private float[] vertices;
	private float[] normals;
	private float[] uv;
	private int[] indices;
	
	private Mesh meshObject;
	
	// Skinned Mesh
	private float[] boneWeights;
	private short[] boneIndices;
	
	// Animation
	private int startFrame;
	private int endFrame;
	private int totalFrame;
	private float[][] matrixPalette;
	
	// Transform Default
	private Vector3 defaultTranslation = new Vector3();
	private Vector3 defaultRotation = new Vector3();
	private Vector3 defaultScaling = new Vector3();

	// Bounding Box
	private Vector3 max = new Vector3();
	private Vector3 min = new Vector3();
	
	// Additional
	private ArrayList<Integer> oi = new ArrayList<Integer>();
	private ArrayList<Integer> ni = new ArrayList<Integer>();
	private FbxCluster[] clustersArr;
	private ArrayList<FbxNode> rootnode = new ArrayList<FbxNode>();
	private HashMap<FbxNode, Integer> map = new HashMap<FbxNode, Integer>();
	
	// Skinned
	private ArrayList<FbxCluster> clusters;
	
	// Prefab
	private NodePrefab nodePrefab;
	private ScenePrefab scenePrefab;

	private FbxDroid(FbxScene scene, ScenePrefab scenePrefab, FbxMesh mesh)
	{
		this.scene = scene;
		this.scenePrefab = scenePrefab;
		this.mesh = mesh;

		this.loadMesh();
	}

	private void loadMesh()
	{
		ArrayList<Float> nv = new ArrayList<Float>();
		
		float[] vertices1 = mesh.getVertices();
		float[] vertices2 = null;
		float[] normals1 = mesh.getNormals();
		float[] normals2 = null;
		float[] uv1 = mesh.getUV();
		float[] uv2 = null;
		int[] indices = mesh.getIndices();
		int[] uvIndices = mesh.getUVIndices();
		
		// Identify Vertices Indices
		for (int i = 0; i < indices.length; i++)
		{
			if(indices[i] < 0)
			{
				indices[i] = ((indices[i] * -1) - 1);
			}
		}

		// Flip Y of UV
		for (int i = 1; i < uv1.length; i += 2)
		{
			uv1[i] = 1 - uv1[i];
		}
		
		int last = vertices1.length / 3;
		int count = 0;
		int[] t = new int[uvIndices.length];

		for (int i = 0; i < t.length; i++)
		{
			t[indices[i]] = uvIndices[i];
		}

		for (int i = 0; i < t.length; i++)
		{
			if (t[indices[i]] != uvIndices[i])
			{
				count++;

				int indx0 = indices[i] * 3;
				nv.add(vertices1[indx0]);
				nv.add(vertices1[indx0 + 1]);
				nv.add(vertices1[indx0 + 2]);

				oi.add(indices[i]);
				ni.add(last);

				indices[i] = last;
				last++;
			}
		}

		int v1length = vertices1.length;
		int uvlength = (v1length / 3) * 2;

		int length = v1length + (count * 3);
		vertices2 = new float[length];
		normals2 = new float[length];
		uv2 = new float[uvlength + (count * 2)];

		System.arraycopy(vertices1, 0, vertices2, 0, v1length);

		for (int i = v1length; i < vertices2.length; i++)
		{
			vertices2[i] = nv.get(i - v1length);
		}

		for(int i = 0; i < indices.length; i++)
		{
			int index1 = indices[i] * 3;
			int index2 = i * 3;

			normals2[index1] = normals1[index2];
			normals2[index1 + 1] = normals1[index2 + 1];
			normals2[index1 + 2] = normals1[index2 + 2];

			int index3 = indices[i] * 2;
			int index4 = uvIndices[i] * 2;

			uv2[index3] = uv1[index4];
			uv2[index3 + 1] = uv1[index4 + 1];
		}

		//
		this.vertices = vertices2;
		this.normals = normals2;
		this.uv = uv2;
		this.indices = indices;

		FbxNode mn = mesh.getNode(0);
		Vector3 defaultT = mn.getLclTranslation();
		Vector3 defaultR = mn.getLclRotation();
		Vector3 defaultS = mn.getLclScaling();
		
		defaultTranslation = new Vector3(defaultT.x, defaultT.y, defaultT.z);
		defaultRotation = new Vector3(defaultR.x, defaultR.y, defaultR.z);
		defaultScaling = new Vector3(defaultS.x, defaultS.y, defaultS.z);
		
		// Find Max Bounds
		for (int i = 0; i < vertices2.length / 3; i++)
		{
			int indx = i * 3;
			float x = vertices2[indx];
			float y = vertices2[indx + 1];
			float z = vertices2[indx + 2];
			
			max.x = Math.max(x, max.x);
			max.y = Math.max(y, max.y);
			max.z = Math.max(z, max.z);
			
			min.x = Math.min(x, min.x);
			min.y = Math.min(y, min.y);
			min.z = Math.min(z, min.z);
			
//			Log.e("Vertices : "+i, x+", "+ y+", "+z);
		}
		
//		// Material Zone
		FbxSurfaceMaterial surfaceMaterial = mesh.getNode(0).getMaterial();
		Material material = null;
		
		int sceneMaterialCount = scene.getMaterialCount();
		for (int j = 0; j < sceneMaterialCount; j++)
		{
			if(scene.getMaterial(j) == surfaceMaterial)
			{
				material = scenePrefab.getMaterials()[j];
			}
		}
		
		if(material == null)
		{
			material = scenePrefab.getDefaultMaterial();
		}

		// Skinned + Animation Zone
		int deformerCount = mesh.getDeformerCount();
		if(deformerCount > 0)
		{
			isSkinnedMesh = true;
			loadSkin();
		}
		else
		{
			FbxNode n = mesh.getNode(0);
			
			if(n != null)
			{
				FbxAnimCurveNode aT = n.getAnimCurveNodeT();
				FbxAnimCurveNode aR = n.getAnimCurveNodeR();
				FbxAnimCurveNode aS = n.getAnimCurveNodeS();
				
				int tt = 0, tr = 0, ts = 0, st = 0, sr = 0, ss = 0;
				
				if(aT != null)
				{
					tt = aT.getTotalFrame();
					st = aT.getStartFrame();
				}
				if(aR != null)
				{
					tr = aR.getTotalFrame();
					sr = aR.getStartFrame();
				}
				if(aS != null)
				{
					ts = aS.getTotalFrame();
					ss = aS.getStartFrame();
				}
				
				totalFrame = Math.max(Math.max(tt, tr), ts);
				if(totalFrame == tt)
				{
					startFrame = st;
				}
				else if(totalFrame == tr)
				{
					startFrame = sr;
				}
				else if(totalFrame == ts)
				{
					startFrame = ss;
				}

				if(totalFrame > 0)
				{
					endFrame = (totalFrame + startFrame) - 1;
					hasAnimation = true;

					matrixPalette = new float[totalFrame][16];
					for (int frame = startFrame; frame < endFrame+1; frame++)
					{
						int i = frame - startFrame;

						if((n.getAnimCurveNodeT() == null))
						{
							T[0] = 0;
							T[1] = 0;
							T[2] = 0;
						}
						else
						{
							n.getAnimCurveNodeT().getValue(T, frame);
						}
						
						if((n.getAnimCurveNodeR() == null))
						{
							R[0] = 0;
							R[1] = 0;
							R[2] = 0;
						}
						else
						{
							n.getAnimCurveNodeR().getValue(R, frame);
						}
						
						if((n.getAnimCurveNodeS() == null))
						{
							S[0] = 1;
							S[1] = 1;
							S[2] = 1;
						}
						else
						{
							n.getAnimCurveNodeS().getValue(S, frame);
						}
						
						Matrix4.createTranslation(translation, T[0], T[1], T[2]);
						Matrix4.createRotationX(rx, R[0]);
						Matrix4.createRotationY(ry, R[1]);
						Matrix4.createRotationZ(rz, R[2]);
						
						Matrix4.multiply(rzy, rz, ry);
						Matrix4.multiply(rotation, rzy, rx);
						Matrix4.createScale(scaling, S[0], S[1], S[2]);

						Matrix4.multiply(TR, translation, rotation);
						Matrix4.multiply(TRS, TR, scaling);

						TRS.copyTo(matrixPalette[i]);
					}
				}
			}
			else
			{
				totalFrame = 0;
				startFrame = 0;
				hasAnimation = false;
			}
		}
		
		
		// Create Mesh
		if(isSkinnedMesh)
		{
			meshObject = new SkinnedMesh(vertices, normals, uv, indices, boneWeights, boneIndices);
//			meshObject.setBuffer(2, boneBuffers[0]);
//			meshObject.setBuffer(3, boneBuffers[1]);
		}
		else
		{
			meshObject = new Mesh(vertices, normals, uv, indices);
		}
		
//		meshObject.setBuffer(0, meshBuffers[0]);
//		meshObject.setBuffer(1, meshBuffers[1]);
		
		if(hasAnimation)
		{
			meshObject.setMatrixPalette(getMatrixPalette());
			meshObject.setMatrixPaletteIndexOffset(getStartFrame());
		}
		
		meshObject.setMin(min);
		meshObject.setMax(max);
		
		nodePrefab = new NodePrefab();
		nodePrefab.setName(getName());
		nodePrefab.setMesh(meshObject);
		nodePrefab.setMaterial(material);
		nodePrefab.setHasAnimation(hasAnimation);
	}
	
	private void loadSkin()
	{
		for (int d = 0; d < mesh.getDeformerCount(); d++)
		{
			FbxSkin skin = (FbxSkin)mesh.getDeformer(d);
			
			clusters = skin.getClusters();
			
			ArrayList<Integer> addOnIndices = new ArrayList<Integer>();
			ArrayList<Float> addOnWeights = new ArrayList<Float>();
			
			for (FbxCluster cluster : clusters)
			{
				int[] indices1 = cluster.getIndices();
				float[] weights1 = cluster.getWeights();
				
				if(indices1 == null)
				{
//					removableCluster.add(cluster);
					continue;
				}
				
				for (int i = 0; i < indices1.length; i++)
				{
					int key = indices1[i];
					
					for (int j = 0; j < oi.size(); j++)
					{
						if(key == oi.get(j))
						{
							addOnIndices.add(ni.get(j));
							addOnWeights.add(weights1[i]);
						}
					}
				}
				
				int size = addOnIndices.size() + indices1.length;

				int[] lastIndices = new int[size];
				float[] lastWeights = new float[size];

				System.arraycopy(indices1, 0, lastIndices, 0, indices1.length);
				System.arraycopy(weights1, 0, lastWeights, 0, weights1.length);
				
				for (int i = weights1.length; i < lastWeights.length; i++)
				{
					int indx = i - weights1.length;
					lastIndices[i] = addOnIndices.get(indx);
					lastWeights[i] = addOnWeights.get(indx);
				}
				
				cluster.setIndices(lastIndices);
				cluster.setWeights(lastWeights);
				
				addOnIndices.clear();
				addOnWeights.clear();
			}
		}
		
//		clusters.removeAll(removableCluster);

		int tableSize = (vertices.length / 3) * 4;
		short[] boneIndexTable = new short[tableSize];
		float[] boneWeightTable = new float[tableSize];
		
		for (int i = 0; i < clusters.size(); i++)
		{
			FbxCluster fbxCluster = clusters.get(i);
			FbxNode n = fbxCluster.getAssociateModel();
			
			if(n != null)
			{
				FbxAnimCurveNode T = n.getAnimCurveNodeT();
				FbxAnimCurveNode R = n.getAnimCurveNodeR();
				FbxAnimCurveNode S = n.getAnimCurveNodeS();
				
				int t = 0, r = 0, s = 0, st = 0, sr = 0, ss = 0;
				
				if(T != null)
				{
					t = T.getTotalFrame();
					st = T.getStartFrame();
				}
				if(R != null)
				{
					r = R.getTotalFrame();
					sr = R.getStartFrame();
				}
				if(S != null)
				{
					s = S.getTotalFrame();
					ss = S.getStartFrame();
				}
				
				totalFrame = Math.max(Math.max(t, r), s);
				if(totalFrame == t)
				{
					startFrame = st;
				}
				else if(totalFrame == r)
				{
					startFrame = sr;
				}
				else if(totalFrame == s)
				{
					startFrame = ss;
				}
				
				if(totalFrame > 0)
				{
					endFrame = (totalFrame + startFrame) - 1;
					hasAnimation = true;
				}
			}
			else
			{
				totalFrame = 0;
				startFrame = 0;
				hasAnimation = false;
			}

			map.put(n, i);
			
			if(n != null)
			{
				if(n.isRoot())
				{
					FbxNode root = n;
					if(!rootnode.contains(root))
					{
						rootnode.add(root);
					}
				}
				else
				{
					FbxNode root = findRootBone(n);
					if(!rootnode.contains(root))
					{
						rootnode.add(root);
					}
				}
			}
			
			int[] cIndices = fbxCluster.getIndices();
			
			if(cIndices == null)
			{
				continue;
			}
			
			for (int j = 0; j < cIndices.length; j++)
			{
				int location = cIndices[j] * 4;
				for (int k = location; k < location + 4; k++)
				{
					if(boneWeightTable[k] <= 0.00000001f)
					{
						boneIndexTable[k] = (short) i;
						boneWeightTable[k] = fbxCluster.getWeights()[j];
						break;
					}
				}
			}
		}
		
		this.boneWeights = boneWeightTable;
		this.boneIndices = boneIndexTable;
		
		clustersArr = new FbxCluster[clusters.size()];
		clusters.toArray(clustersArr);

		if(hasAnimation && totalFrame > 0)
		{
			matrixPalette = new float[totalFrame][];

			for (int frame = startFrame; frame < endFrame+1; frame++)
			{
				int i = frame - startFrame;
				matrixPalette[i] = new float[clusters.size() * 16];
				for (int j = 0; j < rootnode.size(); j++)
				{
					FbxNode node = rootnode.get(j);
					recursive(frame, node, new Matrix4());
				}
			}
		}
		
		// Gen Bone Buffer
//		boneBuffers = genBonesBuffer(boneWeights, boneIndices);
	}
	
	private FbxNode findRootBone(FbxNode node)
	{
		FbxNode parent = node.getParent();
		
		if(parent != null)
		{
			return findRootBone(parent);
		}
		
		return node;
	}
	
	private static Matrix4 AbsoluteTransform = new Matrix4();
//	private static Matrix4 Transform = new Matrix4();
	private static Matrix4 TRS = new Matrix4();
	private static float[] T = new float[3];
	private static float[] R = new float[3];
	private static float[] S = new float[3];
	
	private static Matrix4 rx = new Matrix4();
	private static Matrix4 ry = new Matrix4();
	private static Matrix4 rz = new Matrix4();
	private static Matrix4 rzy = new Matrix4();
	
	private static Matrix4 translation = new Matrix4();
	private static Matrix4 rotation = new Matrix4();
	private static Matrix4 scaling = new Matrix4();
	
	private static Matrix4 TR = new Matrix4();
	private static Matrix4 clusterTransform = new Matrix4();
	private static float[] temp = new float[16];
	
	private void recursive(int frame, FbxNode node, Matrix4 parentWorld)
	{
		Integer i = map.get(node);

		if((node.getAnimCurveNodeT() == null))
		{
			translation.setIdentity();
		}
		else
		{
			node.getAnimCurveNodeT().getValue(T, frame);
			translation.setTranslation(T[0], T[1], T[2]);
		}
		
		if((node.getAnimCurveNodeR() == null))
		{
			rotation.setIdentity();
		}
		else
		{
			node.getAnimCurveNodeR().getValue(R, frame);
			Matrix4.createRotationX(rx, R[0]);
			Matrix4.createRotationY(ry, R[1]);
			Matrix4.createRotationZ(rz, R[2]);
			
			Matrix4.multiply(rzy, rz, ry);
			Matrix4.multiply(rotation, rzy, rx);
		}
		
		if((node.getAnimCurveNodeS() == null))
		{
			scaling.setIdentity();
		}
		else
		{
			node.getAnimCurveNodeS().getValue(S, frame);
			Matrix4.createScale(scaling, S[0], S[1], S[2]);
		}

		Matrix4.multiply(TR, translation, rotation);
		Matrix4.multiply(TRS, TR, scaling);
		
//		Matrix4.createTRS_ZYX(TRS, T[0], T[1], T[2], R[0], R[1], R[2], S[0], S[1], S[2]);
		Matrix4.multiply(AbsoluteTransform, parentWorld, TRS);

		if(i != null)
		{
			clusterTransform.set(clustersArr[i].getTransform());
			
			Matrix4 Transform = new Matrix4();
			Matrix4.multiply(Transform, AbsoluteTransform, clusterTransform);
			
			Transform.copyTo(temp);
			System.arraycopy(temp, 0, matrixPalette[(frame - startFrame)], i * 16, 16);
		}
		
		for (int j = 0; j < node.getChildCount(); j++)
		{
			recursive(frame, node.getChild(j), new Matrix4(AbsoluteTransform));
		}
	}
	
	public Mesh getMeshObject()
	{
		return meshObject;
	}

	public String getName()
	{
		return mesh.getNode(0).getName();
	}
	
	public boolean hasAnimation()
	{
		return hasAnimation;
	}
	
	public boolean isSkinnedMesh()
	{
		return isSkinnedMesh;
	}
	
	public int getStartFrame()
	{
		return startFrame;
	}
	
	public int getEndFrame()
	{
		return endFrame;
	}
	
	public int getTotalFrame()
	{
		return totalFrame;
	}
	
	public float[][] getMatrixPalette()
	{
		return matrixPalette;
	}
	
	public int getNormalOffset()
	{
		return vertices.length * 4;
	}
	
	public int getUVOffset()
	{
		return (vertices.length + normals.length) * 4;
	}
	
	public Vector3 getDefaultTranslation()
	{
		return defaultTranslation;
	}
	
	public Vector3 getDefaultRotation()
	{
		return defaultRotation;
	}
	
	public Vector3 getDefaultScaling()
	{
		return defaultScaling;
	}
	
	public Vector3 getMax()
	{
		return max;
	}
	
	public Vector3 getMin()
	{
		return min;
	}
}
