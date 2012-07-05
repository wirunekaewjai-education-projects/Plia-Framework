package plia.scene.group.shading;

public class Shader
{
	protected ShaderProgram[] programs = new ShaderProgram[15];
	
	public ShaderProgram getProgram(int index)
	{
		return programs[index];
	}
	
	void loadShader()
	{
		
	}
	
	public static void warmUpAllShader()
	{
		AMBIENT.loadShader();
		DIFFUSE.loadShader();
	}
	
	public static final Shader AMBIENT = AmbientShader.getInstance();
	public static final Shader DIFFUSE = DiffuseShader.getInstance();
}
