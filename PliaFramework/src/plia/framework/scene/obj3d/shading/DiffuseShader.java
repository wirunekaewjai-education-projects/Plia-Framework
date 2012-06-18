package plia.framework.scene.obj3d.shading;

final class DiffuseShader
{
	
	private DiffuseShader()
	{
		
	}
	
	private static DiffuseShader instance = new DiffuseShader();
	static DiffuseShader getInstance()
	{
		return instance;
	}
}
