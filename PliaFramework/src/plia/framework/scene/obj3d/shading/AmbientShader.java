package plia.framework.scene.obj3d.shading;


final class AmbientShader extends Shader
{
	
	private AmbientShader()
	{
		
	}
	
	@Override
	void loadShader()
	{
		instance.programs[0] = new ShaderProgram(getAmbientSrc01());
		instance.programs[1] = new ShaderProgram(getAmbientSrc02());
		instance.programs[2] = new ShaderProgram(getAmbientSrc03());
		instance.programs[3] = new ShaderProgram(getAmbientSrc04());
		instance.programs[4] = new ShaderProgram(getAmbientSrc05());
		instance.programs[5] = new ShaderProgram(getAmbientSrc06());
		instance.programs[6] = new ShaderProgram(getAmbientSrc07());
	}
	
	private static AmbientShader instance = new AmbientShader();
	static AmbientShader getInstance()
	{
		return instance;
	}
	
	private static final String vertexSkinningLoop = 
			"	vec4 skinnedPosition = vec4(0.0);" +
			"	" +
			"	int bCount = int(boneCount);" +
			"	for(int i = 0; i < bCount; i++)" +
			"	{" +
			"		float wt = boneWeights[i];" +
			"		if(wt > 0.0)" +
			"		{" +
			"			int indx = int(boneIndices[i]);" +
			"			mat4 matrix = matrixPalette[indx];" +
			"			skinnedPosition += wt * vec4(vec3(matrix * vertex), vertex.w);" +
			"		}" +
			"	}";
	
	private static String fsWithTexture = 
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

	private static String fsWithOutTexture = 
			"precision mediump float;" +
			"" +
			"uniform vec4 color;" +
			"" +
			"void main()" +
			"{" +
			"	gl_FragColor = color;" +
			"}";
	
	
	
	///////////////////////////////////////////
	
	private static String[] getAmbientSrc01()
	{
		//Mesh Without Texture / Line
		String vs = 
				"uniform mat4 modelViewProjectionMatrix;" +
				"" +
				"attribute vec4 vertex;" +
				"" +
				"void main()" +
				"{" +
				"	gl_Position = modelViewProjectionMatrix * vertex;" +
				"}";
		
		return new String[] { vs, fsWithOutTexture };
	}
	
	private static String[] getAmbientSrc02()
	{
		// Skinned-Mesh without Texture
		String vs = 
				"uniform mat4 modelViewProjectionMatrix;" +
				"" +
				"attribute vec4 vertex;" +
				"" +
				"void main()" +
				"{" +
					vertexSkinningLoop +
				"	gl_Position = modelViewProjectionMatrix * skinnedPosition;" +
				"}";
		
		return new String[] { vs, fsWithOutTexture };
	}
	
	private static String[] getAmbientSrc03()
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

		return new String[] { vs, fsWithTexture };
	}

	private static String[] getAmbientSrc04()
	{
		// Skinned-Mesh with Texture
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
					vertexSkinningLoop +
				"	uvCoord = uv;" +
				"	gl_Position = modelViewProjectionMatrix * skinnedPosition;" +
				"}";

		return new String[] { vs, fsWithTexture };
	}
	
	private static String[] getAmbientSrc05()
	{
		// Wired Box
		String vs = 
				"uniform mat4 modelViewProjectionMatrix;" +
				"uniform vec3 size;" +
				"uniform vec3 center;" +
				"" +
				"attribute vec4 vertex;" +
				"" +
				"void main()" +
				"{" +
				"	vec4 V = vec4(0.0);" +
				"	V.x = center.x + (vertex.x * size.x);" +
				"	V.y = center.y + (vertex.y * size.y);" +
				"	V.z = center.z + (vertex.z * size.z);" +
				"	V.w = 1.0;" +
				"	gl_Position = modelViewProjectionMatrix * V;" +
				"}";

		return new String[] { vs, fsWithOutTexture };
	}
	
	private static String[] getAmbientSrc06()
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
	
	private static String[] getAmbientSrc07()
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
}
