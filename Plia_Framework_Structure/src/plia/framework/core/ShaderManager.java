package plia.framework.core;

import android.opengl.GLES20;
import android.util.Log;

public class ShaderManager
{
	private int[][] shaderPrograms = new int[3][5];
	
	private ShaderManager()
	{
		
	}
	
	public void start(boolean debugMode)
	{
		if(debugMode)
		{
			shaderPrograms[0][0] = createProgram(getSrcOfAmbientShader01()); // Mesh without Texture / Line
//			shaderPrograms[0][1] = createProgram(getSrcOfAmbientShader02()); // Mesh with Texture
			shaderPrograms[0][2] = createProgram(getSrcOfAmbientShader03()); // Wired Box
			shaderPrograms[0][3] = createProgram(getSrcOfAmbientShader04()); // Wired Sphere (3-Axis Circle)
			shaderPrograms[0][4] = createProgram(getSrcOfAmbientShader05()); // Wired Sphere (Edge)
		}
		
//		shaderPrograms[1][0] = createProgram(getSrcOfDiffuse01()); // Mesh without Texture
//		shaderPrograms[1][1] = createProgram(getSrcOfDiffuse02()); // Skinned Mesh without Texture
		shaderPrograms[1][2] = createProgram(getSrcOfDiffuse03()); // Mesh with Texture
		shaderPrograms[1][3] = createProgram(getSrcOfDiffuse04()); // Skinned Mesh with Texture
	}
	
	public int getProgram(int shaderType, int geometryType)
	{
		return shaderPrograms[shaderType][geometryType];
	}
	
	private static ShaderManager instance = new ShaderManager();
	public static ShaderManager getInstance()
	{
		return instance;
	}
	
	private int createProgram(String[] src)
	{
		int vShader = loadShader(GLES20.GL_VERTEX_SHADER, src[0]);
		int fShader = loadShader(GLES20.GL_FRAGMENT_SHADER, src[1]);
		
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
	
	private int loadShader(int shaderType, String src)
	{
		int shader = GLES20.glCreateShader(shaderType);
		
		if(shader != 0)
		{
			GLES20.glShaderSource(shader, src);
			GLES20.glCompileShader(shader);
		}
		
		return shader;
	}
	
	private String[] getSrcOfAmbientShader01()
	{
		// Mesh without Texture / Line
		String vs = 
				"uniform mat4 modelViewProjectionMatrix;" +
				"" +
				"attribute vec4 vertex;" +
				"" +
				"void main()" +
				"{" +
				"	gl_Position = modelViewProjectionMatrix * vertex;" +
				"}";
		
		String fs = 
				"precision mediump float;" +
				"" +
				"uniform vec4 color;" +
				"" +
				"void main()" +
				"{" +
				"	gl_FragColor = color;" +
				"}";
		
		return new String[] { vs, fs };
	}
	
	private String[] getSrcOfAmbientShader02()
	{
		// Mesh with Texture
		String vs = 
				"uniform mat4 modelViewProjectionMatrix;" +
				"" +
				"attribute vec4 vertex;" +
				"attribute vec2 uv;" +
				"" +
				"varying vec2 uvCoord;" +
				"" +
				"void main()" +
				"{" +
				"	uvCoord = uv;" +
				"	gl_Position = modelViewProjectionMatrix * vertex;" +
				"}";
		
		String fs = 
				"precision mediump float;" +
				"" +
				"uniform sampler2D baseTexture;" +
				"" +
				"varying vec2 uvCoord;" +
				"" +
				"void main()" +
				"{" +
				"	gl_FragColor = texture2D(baseTexture, uvCoord);" +
				"}";
		
		return new String[] { vs, fs };
	}
	
	private String[] getSrcOfAmbientShader03()
	{
		// Wired Box
		String vs = 
				"uniform mat4 modelViewProjectionMatrix;" +
				"uniform vec3 size;" +
				"" +
				"attribute vec4 vertex;" +
				"" +
				"void main()" +
				"{" +
				"	vec4 V = vec4(0.0);" +
				"	V.x = vertex.x * size.x;" +
				"	V.y = vertex.y * size.y;" +
				"	V.z = vertex.z * size.z;" +
				"	V.w = 1.0;" +
				"	gl_Position = modelViewProjectionMatrix * V;" +
				"}";
		
		String fs = 
				"precision mediump float;" +
				"" +
				"uniform vec4 color;" +
				"" +
				"void main()" +
				"{" +
				"	gl_FragColor = color;" +
				"}";

		return new String[] { vs, fs };
	}
	
	private String[] getSrcOfAmbientShader04()
	{
		// Wired Sphere (3-Axis Circle)
		String vs = 
				"uniform mat4 modelViewProjectionMatrix;" +
				"uniform vec3 eye;" +
				"uniform vec4 position;" +
				"uniform float radius;" +
				"" +
				"attribute vec4 vertex;" +
				"" +
				"varying float intensity;" +
				"" +
				"void main()" +
				"{" +
				"	vec3 vr = (vertex * radius).xyz;" +
				"	vec4 V = vec4(vr, 1.0);" +
				"	vec3 vp = vr + position.xyz;" +
				"	vec3 N = normalize(vr);" +
				"	vec3 E = normalize(eye - vp);" +
				"	float d = dot(N, E);" +
				"" +
				"	if(d < 0.0)" +
				"	{" +
				"		intensity = 0.5;" +
				"	}" +
				"	else" +
				"		intensity = 1.0;" +
				"" +
				"	gl_Position = modelViewProjectionMatrix * V;" +
				"}";
		
		String fs = 
				"precision mediump float;" +
				"" +
				"uniform vec4 color;" +
				"varying float intensity;" +
				"" +
				"void main()" +
				"{" +
				"	vec4 final_color = color * intensity;" +
				"	gl_FragColor = final_color;" +
				"}";
		
		return new String[] { vs, fs };
	}
	
	private String[] getSrcOfAmbientShader05()
	{
		// Wired Sphere (Edge)
		String vs = 
				"uniform mat4 modelViewProjectionMatrix;" +
				"uniform vec3 eye;" +
				"uniform vec4 position;" +
				"uniform float radius;" +
				"" +
				"varying float d;" +
				"" +
				"attribute vec4 vertex;" +
				"" +
				"void main()" +
				"{" +
				"	vec3 vr = (vertex * radius).xyz;" +
				"	vec4 V = vec4(vr, 1.0);" +
				"	vec3 vp = vr + position.xyz;" +
				"	vec3 N = normalize(vr);" +
				"	vec3 E = normalize(eye - vp);" +
				"	d = dot(N, E);" +
				"	gl_Position = modelViewProjectionMatrix * V;" +
				"}";
		
		String fs = 
				"precision mediump float;" +
				"" +
				"uniform vec4 color;" +
				"varying float d;" +
				"" +
				"void main()" +
				"{" +
				"	if(d < 0.125 && d > 0.0)" +
				"	{" +
				"		gl_FragColor = color;" +
				"	}" +
				"	else" +
				"		gl_FragColor = vec4(0.0);" +
				"}";
		
		return new String[] { vs, fs };
	}
	
	//
	
	
	
	private String[] getSrcOfDiffuse01()
	{
		// Mesh without Texture
		String vs = 
				"const int MAX_LIGHTS = 8;" +
				"uniform vec4 lightPosition[MAX_LIGHTS];" +
				"uniform vec4 lightColor[MAX_LIGHTS];" +
				"uniform float lightRange[MAX_LIGHTS];" +
				"uniform mat4 modelViewProjectionMatrix;" +
				"uniform mat4 modelViewMatrix;" +
				"uniform mat3 normalMatrix;" +
				"uniform float numberOfLights;" +
				"varying vec4 Idif;" +
				"attribute vec4 vertex;" +
				"attribute vec3 normal;" +
				"void main()" +
				"{" +
				"	Idif = vec4(0.0);" +
				"	vec4 V = modelViewMatrix * vertex;" +
				"	vec3 N = normalMatrix * normal;" +
				"	int count = int(numberOfLights);" +
				"	for(int i = 0; i<count; ++i)" +
				"	{" +
				"		vec4 lightDir = lightPosition[i];" +
				"		vec3 L = normalize( lightDir.xyz - (V.xyz * lightDir.w) );" +
				"		float lambertTerm = dot(N, L);" +
				"		if(lambertTerm > 0.0)" +
				"		{" +
				"			if(lightDir.w > 0.0)" +
				"			{" +
				"				float dist = length(L);" +
				"				float att = 1.0 - (dist / lightRange[i]);" +
				"				lambertTerm *= att;" +
				"			}" +
				"			Idif += lightColor[i] * lambertTerm;" +
				"		}" +
				"	}" +
				"	gl_Position = modelViewProjectionMatrix * vertex;" +
				"}";
		
		String fs = 
				"precision mediump float;" +
				"" +
				"uniform vec4 baseColor;" +
				"varying vec4 Idif;" +
				"void main()" +
				"{" +
				"	gl_FragColor = baseColor * Idif;" +
				"}";
		
		return new String[] { vs, fs };
	}
	
	private String[] getSrcOfDiffuse02()
	{
		//Skinned Mesh without Texture
		String vs = 
				"const int MAX_LIGHTS = 8;" +
				"const int MATRICES_SIZE = 128;" +
				"uniform vec4 lightPosition[MAX_LIGHTS];" +
				"uniform vec4 lightColor[MAX_LIGHTS];" +
				"uniform float lightRange[MAX_LIGHTS];" +
				"uniform mat4 modelViewProjectionMatrix;" +
				"uniform mat4 modelViewMatrix;" +
				"uniform mat3 normalMatrix;" +
				"uniform float numberOfLights;" +
				"varying vec4 Idif;" +
				"attribute vec4 vertex;" +
				"attribute vec3 normal;" +
				"uniform vec4 matrixPalette[MATRICES_SIZE];" +
				"attribute vec4 boneIndexes;" +
				"attribute vec4 boneWeights;" +
				"void main()" +
				"{" +
				"	Idif = vec4(0.0);" +
				"	vec4 skinnedPosition = vec4(0.0);" +
				"	vec3 skinnedNormal = vec3(0.0);" +
				"	for(int i = 0; i < 2; ++i)" +
				"	{" +
				"		float wt = boneWeights[i];" +
				"		if(wt > 0.0)" +
				"		{" +
				"			vec4 tmpV;" +
				"			int indx = int(boneIndexes[i]) * FOUR;" +
				"			tmpV.x = dot(matrixPalette[indx], vertex);" +
				"			tmpV.y = dot(matrixPalette[indx + ONE], vertex);" +
				"			tmpV.z = dot(matrixPalette[indx + TWO], vertex);" +
				"			tmpV.w = vertex.w;" +
				"" +
				"			skinnedPosition += wt*tmpV;" +
				"			vec3 tmpN;" +
				"			tmpN.x = dot(matrixPalette[indx].xyz, normal);" +
				"			tmpN.y = dot(matrixPalette[indx + ONE].xyz, normal);" +
				"			tmpN.z = dot(matrixPalette[indx + TWO].xyz, normal);" +
				"			skinnedNormal += wt*tmpN;" +
				"" +
				"		}" +
				"	}" +
				"	vec4 V = modelViewMatrix * skinnedPosition;" +
				"	vec3 N = normalMatrix * skinnedNormal;" +
				"	int count = int(numberOfLights);" +
				"	for(int i = 0; i<count; ++i)" +
				"	{" +
				"		vec4 lightDir = lightPosition[i];" +
				"		vec3 L = normalize( lightDir.xyz - (V.xyz * lightDir.w) );" +
				"		float lambertTerm = dot(N, L);" +
				"		if(lambertTerm > 0.0)" +
				"		{" +
				"			if(lightDir.w > 0.0)" +
				"			{" +
				"				float dist = length(L);" +
				"				float att = 1.0 - (dist / lightRange[i]);" +
				"				lambertTerm *= att;" +
				"			}" +
				"			Idif += lightColor[i] * lambertTerm;" +
				"		}" +
				"	}" +
				"	gl_Position = modelViewProjectionMatrix * vertex;" +
				"}";
		
		String fs = 
				"precision mediump float;" +
				"" +
				"uniform vec4 baseColor;" +
				"varying vec4 Idif;" +
				"void main()" +
				"{" +
				"	gl_FragColor = baseColor * Idif;" +
				"}";
		
		return new String[] { vs, fs };
	}
	
	private String[] getSrcOfDiffuse03()
	{
		// Mesh with Texture
		String vs = 
				"const int MAX_LIGHTS = 8;" +
				"uniform vec4 lightPosition[MAX_LIGHTS];" +
				"uniform vec4 lightColor[MAX_LIGHTS];" +
				"uniform float lightRange[MAX_LIGHTS];" +
				"uniform mat4 modelViewProjectionMatrix;" +
				"uniform mat4 modelViewMatrix;" +
				"uniform mat3 normalMatrix;" +
				"uniform float numberOfLights;" +
				"varying vec2 uvCoord;" +
				"varying vec4 Idif;" +
				"attribute vec4 vertex;" +
				"attribute vec3 normal;" +
				"attribute vec2 uv;" +
				"void main()" +
				"{" +
				"	Idif = vec4(0.0);" +
				"	vec4 V = modelViewMatrix * vertex;" +
				"	vec3 N = normalMatrix * normal;" +
				"	int count = int(numberOfLights);" +
				"	for(int i = 0; i<count; ++i)" +
				"	{" +
				"		vec4 lightDir = lightPosition[i];" +
				"		vec3 L = normalize( lightDir.xyz - (V.xyz * lightDir.w) );" +
				"		float lambertTerm = dot(N, L);" +
				"		if(lambertTerm > 0.0)" +
				"		{" +
				"			if(lightDir.w > 0.0)" +
				"			{" +
				"				float dist = length(L);" +
				"				float att = 1.0 - (dist / lightRange[i]);" +
				"				lambertTerm *= att;" +
				"			}" +
				"			Idif += lightColor[i] * lambertTerm;" +
				"		}" +
				"	}" +
				"	uvCoord = uv;" +
				"	gl_Position = modelViewProjectionMatrix * vertex;" +
				"}";
		
		String fs = 
				"precision mediump float;" +
				"" +
				"uniform sampler2D baseTexture;" +
				"varying vec2 uvCoord;" +
				"varying vec4 Idif;" +
				"void main()" +
				"{" +
				"	gl_FragColor = texture2D(baseTexture, uvCoord) * Idif;" +
				"}";
		
		return new String[] { vs, fs };
	}
	
	private String[] getSrcOfDiffuse04()
	{
		//Skinned Mesh with Texture
		
		String vs = 
				"const int MAX_LIGHTS = 8;" +
				"const int MATRICES_SIZE = 128;" +
				"uniform vec4 lightPosition[MAX_LIGHTS];" +
				"uniform vec4 lightColor[MAX_LIGHTS];" +
				"uniform float lightRange[MAX_LIGHTS];" +
				"uniform mat4 modelViewProjectionMatrix;" +
				"uniform mat4 modelViewMatrix;" +
				"uniform mat3 normalMatrix;" +
				"uniform float numberOfLights;" +
				"varying vec2 uvCoord;" +
				"varying vec4 Idif;" +
				"attribute vec4 vertex;" +
				"attribute vec3 normal;" +
				"attribute vec2 uv;" +
				"uniform vec4 matrixPalette[MATRICES_SIZE];" +
				"attribute vec4 boneIndexes;" +
				"attribute vec4 boneWeights;" +
				"void main()" +
				"{" +
				"	Idif = vec4(0.0);" +
				"	vec4 skinnedPosition = vec4(0.0);" +
				"	vec3 skinnedNormal = vec3(0.0);" +
				"	for(int i = 0; i < 4; ++i)" +
				"	{" +
				"		float wt = boneWeights[i];" +
				"		if(wt > 0.0)" +
				"		{" +
				"			vec4 tmpV;" +
				"			int indx = int(boneIndexes[i]) * 4;" +
				"			tmpV.x = dot(matrixPalette[indx], vertex);" +
				"			tmpV.y = dot(matrixPalette[indx + 1], vertex);" +
				"			tmpV.z = dot(matrixPalette[indx + 2], vertex);" +
				"			tmpV.w = vertex.w;" +
				"			skinnedPosition += wt*tmpV;" +
				"" +
				"			vec3 tmpN;" +
				"			tmpN.x = dot(matrixPalette[indx].xyz, normal);" +
				"			tmpN.y = dot(matrixPalette[indx + 1].xyz, normal);" +
				"			tmpN.z = dot(matrixPalette[indx + 2].xyz, normal);" +
				"			skinnedNormal += wt*tmpN;" +
				"		}" +
				"	}" +
				"	vec4 V = modelViewMatrix * skinnedPosition;" +
				"	vec3 N = normalMatrix * skinnedNormal;" +
				"	int count = int(numberOfLights);" +
				"	for(int i = 0; i<count; ++i)" +
				"	{" +
				"		vec4 lightDir = lightPosition[i];" +
				"		vec3 L = normalize( lightDir.xyz - (V.xyz * lightDir.w) );" +
				"		float lambertTerm = dot(N, L);" +
				"		if(lambertTerm > 0.0)" +
				"		{" +
				"			if(lightDir.w > 0.0)" +
				"			{" +
				"				float dist = length(L);" +
				"				float att = 1.0 - (dist / lightRange[i]);" +
				"				lambertTerm *= att;" +
				"			}" +
				"			Idif += lightColor[i] * lambertTerm;" +
				"		}" +
				"	}" +
				"	uvCoord = uv;" +
				"	gl_Position = modelViewProjectionMatrix * vertex;" +
				"}";
		
		String fs = 
				"precision mediump float;" +
				"uniform sampler2D baseTexture;" +
				"varying vec2 uvCoord;" +
				"varying vec4 Idif;" +
				"void main()" +
				"{" +
				"	gl_FragColor = texture2D(baseTexture, uvCoord) * Idif;" +
				"}";
		
		return new String[] { vs, fs };
	}
}
