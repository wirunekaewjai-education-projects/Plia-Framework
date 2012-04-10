package plia.scenegraph;

public class GameObject extends SceneNode
{
	private int instanceID;
	
	public String name;
	public String tag;

	public GameObject()
	{
		name = "GameObject";
		tag = "";
	}

	public int getInstanceID()
	{
		return instanceID;
	}
}
