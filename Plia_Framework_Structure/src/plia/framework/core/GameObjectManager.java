package plia.framework.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import plia.framework.gameobject.Camera;
import plia.framework.gameobject.Light;
import plia.framework.gameobject.Model;
import plia.framework.gameobject.Terrain;
import plia.framework.gameobject.fbxobject.FbxMesh;

public class GameObjectManager
{
	private HashMap<String, FbxMesh> meshes = new HashMap<String, FbxMesh>();
	private List<Model> models = new ArrayList<Model>();
	private List<Light> lights = new ArrayList<Light>();
	private Camera mainCamera;
	private Terrain terrain;

	public void removeAll()
	{
		
	}
	
	public void update()
	{
		
	}
	
	public void render()
	{
		
	}
	
	public void setContext(Context context)
	{
		
	}

	private static GameObjectManager instance = new GameObjectManager();
	public static GameObjectManager getInstance()
	{
		return instance;
	}
}
