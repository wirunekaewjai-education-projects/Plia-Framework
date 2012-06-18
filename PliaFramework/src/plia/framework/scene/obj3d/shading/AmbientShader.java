package plia.framework.scene.obj3d.shading;

final class AmbientShader
{
	
	private AmbientShader()
	{
		
	}
	
	private static AmbientShader instance = new AmbientShader();
	static AmbientShader getInstance()
	{
		return instance;
	}
}
