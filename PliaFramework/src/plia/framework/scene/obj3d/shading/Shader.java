package plia.framework.scene.obj3d.shading;

import java.nio.Buffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import plia.framework.math.Matrix3;
import plia.framework.math.Matrix4;
import plia.framework.math.Vector2;
import plia.framework.math.Vector3;
import plia.framework.math.Vector4;
import android.opengl.GLES20;
import android.util.Log;

public class Shader
{
	private int[] datas = new int[96];
	
	protected int[] programs = new int[10];
	
	private static final float[] matrix3 = new float[9];
	private static final float[] matrix4 = new float[16];
	
	public Shader(String vs, String fs)
	{
		datas[0] = createProgram(vs, fs);
		getLocation(datas[0]);
	}
	
	public int getProgram(int index)
	{
		return programs[index];
	}
	
	public void use()
	{
		GLES20.glUseProgram(datas[0]);
	}
	
	public void setAttribPointer(int name, int size, int stride, int offset, VariableType type)
	{
		int location = datas[name];
		
		int vtype = GLES20.GL_FLOAT;
		
		switch (type)
		{
			case SHORT: vtype = GLES20.GL_SHORT; break;
			case INT: vtype = GLES20.GL_INT; break;
			default: break;
		}
		
		GLES20.glVertexAttribPointer(location, size, vtype, false, stride, offset);
	}
	
	public void setAttribPointer(int name, int size, int stride, int offset, Buffer ptr)
	{
		int location = datas[name];
		
		int type = GLES20.GL_FLOAT;
		
		if(ptr instanceof ShortBuffer)
		{
			type = GLES20.GL_SHORT;
		}
		else if(ptr instanceof IntBuffer)
		{
			type = GLES20.GL_INT;
		}
		
		GLES20.glVertexAttribPointer(location, size, type, false, stride, ptr);
	}
	
	public void setUniform(int name, Object object)
	{
		int location = datas[name];
		
		if(object instanceof Integer)
		{
			GLES20.glUniform1i(location, (Integer) object);
		}
		else if(object instanceof Float)
		{
			GLES20.glUniform1f(location, (Float) object);
		}
		else if(object instanceof Vector2)
		{
			Vector2 v = (Vector2) object;
			GLES20.glUniform2f(location, v.x, v.y);
		}
		else if(object instanceof Vector3)
		{
			Vector3 v = (Vector3)object;
			GLES20.glUniform3f(location, v.x, v.y, v.z);
		}
		else if(object instanceof Vector4)
		{
			Vector4 v = (Vector4)object;
			GLES20.glUniform4f(location, v.x, v.y, v.z, v.w);
		}
		else if(object instanceof Matrix3)
		{
			((Matrix3)object).copyTo(matrix3);
			GLES20.glUniformMatrix3fv(location, 1, false, matrix3, 0);
		}
		else if(object instanceof Matrix4)
		{
			((Matrix4)object).copyTo(matrix4);
			GLES20.glUniformMatrix4fv(location, 1, false, matrix4, 0);
		}
	}
	
	private void getLocation(int program)
	{
		for (int i = 0; i < datas.length; i++)
		{
			datas[i] = -2;
		}
		
		datas[VERTEX_ATTRIBUTE]		  = GLES20.glGetAttribLocation(program, "vertex");
		datas[NORMAL_ATTRIBUTE]		  = GLES20.glGetAttribLocation(program, "normal");
		datas[UV_ATTRIBUTE]			  = GLES20.glGetAttribLocation(program, "uv");
		
		datas[BONE_INDEXES_ATTRIBUTE] = GLES20.glGetAttribLocation(program, "boneIndices");
		datas[BONE_WEIGHTS_ATTRIBUTE] = GLES20.glGetAttribLocation(program, "boneWeights");
			
		datas[BONE_COUNT] 			  = GLES20.glGetUniformLocation(program, "boneCount");
		datas[MATRIX_PALETTE] 		  = GLES20.glGetUniformLocation(program, "matrixPalette");
		
		datas[MODELVIEW_PROJECTION_MATRIX] 	= GLES20.glGetUniformLocation(program, "modelViewProjectionMatrix");
		datas[MODELVIEW_MATRIX] 		  	= GLES20.glGetUniformLocation(program, "modelViewMatrix");
		datas[PROJECTION_MATRIX] 		  	= GLES20.glGetUniformLocation(program, "projectionMatrix");
		datas[NORMAL_MATRIX] 		 		= GLES20.glGetUniformLocation(program, "normalMatrix");
		
		datas[COLOR] 		= GLES20.glGetUniformLocation(program, "color");
		datas[DIFFUSE_MAP] 	= GLES20.glGetUniformLocation(program, "diffuseMap");
		datas[NORMAL_MAP]  	= GLES20.glGetUniformLocation(program, "normalMap");
		datas[HEIGHT_MAP] 	= GLES20.glGetUniformLocation(program, "heightMap");
		
		datas[LIGHT_COUNT] 			= GLES20.glGetUniformLocation(program, "lightCount");
		
		for (int i = 0; i < 8; i++)
		{
			datas[LIGHT_POSITION_0+i] 	= GLES20.glGetUniformLocation(program, "lightPosition["+i+"]");
			datas[LIGHT_COLOR_0+i] 		= GLES20.glGetUniformLocation(program, "lightColor["+i+"]");
			datas[LIGHT_INTENSITY_0+i] 	= GLES20.glGetUniformLocation(program, "lightIntensity["+i+"]");
			datas[LIGHT_RADIUS_0+i] 	= GLES20.glGetUniformLocation(program, "lightRadius["+i+"]");
		}
	}

	private static int createProgram(String vs, String fs)
	{
		int vShader = loadShader(GLES20.GL_VERTEX_SHADER, vs);
		int fShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fs);
		
		int program = GLES20.glCreateProgram();
		
		GLES20.glAttachShader(program, vShader);
		GLES20.glAttachShader(program, fShader);
		GLES20.glLinkProgram(program);

		int[] linkStatus = new int[1];
		
		GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
		if (linkStatus[0] != GLES20.GL_TRUE) 
		{
			Log.e("Shader : "+program, "Could not link _program: ");
			Log.e("Shader : "+program, GLES20.glGetProgramInfoLog(program));
			GLES20.glDeleteProgram(program);
			program = 0;
			return 0;
		}

		return program;
	}
	
	private static int loadShader(int shaderType, String src)
	{
		int shader = GLES20.glCreateShader(shaderType);
		
		if(shader != 0)
		{
			GLES20.glShaderSource(shader, src);
			GLES20.glCompileShader(shader);
		}
		
		return shader;
	}
	
//	public int get(int index)
//	{
//		return datas[index];
//	}
	
	public enum VariableType
	{
		SHORT, INT, FLOAT, 
		VECTOR2, VECTOR3, VECTOR4,
		MATRIX3, MATRIX4
	}

	public static final int PROGRAM						 = 0;
	
	public static final int VERTEX_ATTRIBUTE			 = 1;
	public static final int NORMAL_ATTRIBUTE			 = 2;
	public static final int UV_ATTRIBUTE				 = 3;
	public static final int BONE_INDEXES_ATTRIBUTE		 = 4;
	public static final int BONE_WEIGHTS_ATTRIBUTE		 = 5;
	
	public static final int BONE_COUNT					 = 6;
	public static final int MATRIX_PALETTE				 = 7;
	
	public static final int MODELVIEW_PROJECTION_MATRIX  = 11;
	public static final int MODELVIEW_MATRIX			 = 12;
	public static final int PROJECTION_MATRIX			 = 13;
	public static final int NORMAL_MATRIX				 = 14;
	
	public static final int COLOR		 				 = 21;
	public static final int DIFFUSE_MAP					 = 22;
	public static final int NORMAL_MAP					 = 23;
	public static final int HEIGHT_MAP					 = 24;
	
	public static final int LIGHT_COUNT					 = 31;
	
	public static final int LIGHT_POSITION_0			 = 32;
	public static final int LIGHT_POSITION_1			 = 33;
	public static final int LIGHT_POSITION_2			 = 34;
	public static final int LIGHT_POSITION_3			 = 35;
	public static final int LIGHT_POSITION_4			 = 36;
	public static final int LIGHT_POSITION_5			 = 37;
	public static final int LIGHT_POSITION_6			 = 38;
	public static final int LIGHT_POSITION_7			 = 39;
	public static final int LIGHT_POSITION_8			 = 40;
	
	public static final int LIGHT_COLOR_0				 = 41;
	public static final int LIGHT_COLOR_1				 = 42;
	public static final int LIGHT_COLOR_2				 = 43;
	public static final int LIGHT_COLOR_3				 = 44;
	public static final int LIGHT_COLOR_4				 = 45;
	public static final int LIGHT_COLOR_5				 = 46;
	public static final int LIGHT_COLOR_6				 = 47;
	public static final int LIGHT_COLOR_7				 = 48;
	public static final int LIGHT_COLOR_8				 = 49;
	
	public static final int LIGHT_RADIUS_0				 = 51;
	public static final int LIGHT_RADIUS_1				 = 52;
	public static final int LIGHT_RADIUS_2				 = 53;
	public static final int LIGHT_RADIUS_3				 = 54;
	public static final int LIGHT_RADIUS_4				 = 55;
	public static final int LIGHT_RADIUS_5				 = 56;
	public static final int LIGHT_RADIUS_6				 = 57;
	public static final int LIGHT_RADIUS_7				 = 58;
	public static final int LIGHT_RADIUS_8				 = 59;
	
	public static final int LIGHT_INTENSITY_0			 = 61;
	public static final int LIGHT_INTENSITY_1			 = 62;
	public static final int LIGHT_INTENSITY_2			 = 63;
	public static final int LIGHT_INTENSITY_3			 = 64;
	public static final int LIGHT_INTENSITY_4			 = 65;
	public static final int LIGHT_INTENSITY_5			 = 66;
	public static final int LIGHT_INTENSITY_6			 = 67;
	public static final int LIGHT_INTENSITY_7			 = 68;
	public static final int LIGHT_INTENSITY_8			 = 69;
	
	
	
}
