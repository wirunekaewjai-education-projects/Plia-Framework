package library.graphics;

import static android.opengl.GLES20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;

public class Shader
{
	private int program;
	
	public Shader(String vShaderSrc, String fShaderSrc)
	{
		createProgram(vShaderSrc, fShaderSrc);
	}
	
	public Shader(int vShaderId, int fShaderId, Context context)
	{
		String vShaderSrc = new String(), fShaderSrc = new String();
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(vShaderId)));
			
			String t;
			while((t = br.readLine()) != null)
			{
				vShaderSrc += t+" \n";
			}

			br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(fShaderId)));
			
			while((t = br.readLine()) != null)
			{
				fShaderSrc += t+" \n";
			}
			
		}catch(IOException ex)
		{
			
		}
		
		createProgram(vShaderSrc, fShaderSrc);
	}

	private void createProgram(String vShaderSrc, String fShaderSrc)
	{
		int vShader = loadShader(GL_VERTEX_SHADER, vShaderSrc);
		int fShader = loadShader(GL_FRAGMENT_SHADER, fShaderSrc);
		
		program = glCreateProgram();
		
		
		glAttachShader(program, vShader);
		glAttachShader(program, fShader);

		glLinkProgram(program);
	}
	
	private int loadShader(int shaderType, String src)
	{
		int shader = glCreateShader(shaderType);
		
		if(shader != 0)
		{
			glShaderSource(shader, src);
			glCompileShader(shader);
		}
		
		return shader;
	}
	
	public int getProgram()
	{
		return program;
	}
	
}
