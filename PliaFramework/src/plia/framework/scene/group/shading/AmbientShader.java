package plia.framework.scene.group.shading;


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
		instance.programs[7] = new ShaderProgram(getAmbientSrc08());
		instance.programs[8] = new ShaderProgram(getAmbientSrc09());
		instance.programs[9] = new ShaderProgram(getAmbientSrc10());
		instance.programs[10] = new ShaderProgram(getAmbientSrc11());
	}
	
	private static AmbientShader instance = new AmbientShader();
	static AmbientShader getInstance()
	{
		return instance;
	}
	
	private static final String boneAttributeAndMatrixPaletteUniform = 
			"const int MATRICES_SIZE = 96;" +
			"uniform mat4 matrixPalette[MATRICES_SIZE];" +
			"attribute vec4 boneIndices;" +
			"attribute vec4 boneWeights;" +
			"attribute float boneCount;";
	
	private static final String vertexSkinningLoop = 
			"	vec4 skinnedPosition = vec4(0.0);" +
			"	vec3 skinnedNormal = vec3(0.0);" +
			"	" +
			"	int bCount = int(boneCount);" +
			"	for(int b = 0; b < bCount; ++b)" +
			"	{" +
			"		float wt = boneWeights[b];" +
			"		if(wt > 0.0)" +
			"		{" +
			"			int indx = int(boneIndices[b]);" +
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
				boneAttributeAndMatrixPaletteUniform +
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
				boneAttributeAndMatrixPaletteUniform +
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
	
	private static String[] getAmbientSrc08()
	{
		// Gen Terrain NormalMap
		String vs = 
				"uniform mat4 modelViewProjectionMatrix;" +
				"" +
				"uniform vec3 terrainData;" +
				"uniform sampler2D heightmap;" +
				"" +
				"attribute vec2 vertex;" +
				"" +
				"varying vec4 vertex_color;" +
				"" +
				"vec3 displacement(float x, float z)" +
				"{" +
				"	float u = min(0.99, max(0.01, x/terrainData.z));" +
				"	float v = min(0.99, max(0.01, z/terrainData.z));" +
				"" +
				"	vec2 coord = vec2(u, v);" +
				"	vec4 displace = texture2D(heightmap, coord);" +
				"	float height = displace.x * terrainData.x;" +
				"" +
				"	return vec3(x, height, z);" +
				"}" +
				"" +
				"vec3 normalSurface(vec3 a, vec3 b, vec3 c)" +
				"{" +
				"	return normalize( cross(a, b) + cross(b, c) + cross(c, a) );" +
				"}" +
				"" +
				"void main()" +
				"{" +
				"	float segSize = terrainData.z / terrainData.y;" +
				"	vec2 point = vertex*segSize;" +
				"" +
				"	vec3 vert[7];" +
				"" +
				"	vert[0] = displacement(point.x, 		point.y);" +
				"	vert[1] = displacement(point.x, 		point.y+segSize);" +
				"	vert[2] = displacement(point.x+segSize, point.y+segSize);" +
				"	vert[3] = displacement(point.x+segSize, point.y);" +
				"	vert[4] = displacement(point.x, 		point.y-segSize);" +
				"	vert[5] = displacement(point.x-segSize, point.y-segSize);" +
				"	vert[6] = displacement(point.x-segSize, point.y);" +
				"" +
				"	vec3 n1 = normalSurface(vert[0], vert[1], vert[2]);" +
				"	vec3 n2 = normalSurface(vert[0], vert[2], vert[3]);" +
				"	vec3 n3 = normalSurface(vert[0], vert[3], vert[4]);" +
				"	vec3 n4 = normalSurface(vert[0], vert[4], vert[5]);" +
				"	vec3 n5 = normalSurface(vert[0], vert[5], vert[6]);" +
				"	vec3 n6 = normalSurface(vert[0], vert[6], vert[1]);" +
				"" +
				"	vec3 N = (n1+n2+n3+n4+n5+n6)/6.0;" +
				"	vec3 C = normalize(N);" +
				"	vertex_color = vec4(((C.gbr/2.0) + 0.5), 1.0);" +
				"" +
				"	vec4 position = vec4(vertex.x, vertex.y, 0.0, 1.0);" +
				"" +
				"	gl_Position = modelViewProjectionMatrix * position;" +
				"}";
				
		
		String fs = 
				"precision mediump float;" +
				"" +
				"varying vec4 vertex_color;" +
				"" +
				"void main()" +
				"{" +
				"	gl_FragColor = vertex_color;" +
				"}";
				
		
		return new String[] { vs, fs };
	}
	
	private static String[] getAmbientSrc09()
	{
		//Mesh Without Texture / Line
				String vs = 
						"uniform mat4 modelViewProjectionMatrix;" +
						"" +
						"attribute vec2 vertex;" +
						"" +
						"void main()" +
						"{" +
						"	vec4 position = vec4(vertex.x, vertex.y, 0.0, 1.0);" +
						"	gl_Position = modelViewProjectionMatrix * position;" +
						"}";
				
				return new String[] { vs, fsWithOutTexture };
	}
	
	private static String[] getAmbientSrc10()
	{
		//Grid
		String vs = 
				"uniform mat4 modelViewProjectionMatrix;" +
				"" +
				"attribute vec2 vertex;" +
				"attribute vec4 color;" +
				"" +
				"varying vec4 vertex_color;" +
				"" +
				"void main()" +
				"{" +
				"	vertex_color =  color;" +
				"	gl_Position = modelViewProjectionMatrix * vec4(vertex.x, vertex.y, 0.0, 1.0);" +
				"}";
				
		String fs = 
				"precision mediump float;" +
				"" +
				"varying vec4 vertex_color;" +
				"" +
				"void main()" +
				"{" +
				"	gl_FragColor = vertex_color;" +
				"}";
				
		return new String[] { vs, fs };
	}
	
	private static String[] getAmbientSrc11()
	{
		//Grid
		String vs = 
				"uniform mat4 modelViewProjectionMatrix;" +
				"" +
				"attribute vec4 vertex;" +
				"attribute vec4 color;" +
				"" +
				"varying vec4 vertex_color;" +
				"" +
				"void main()" +
				"{" +
				"	vertex_color =  color;" +
				"	gl_Position = modelViewProjectionMatrix * vertex;" +
				"}";
				
		String fs = 
				"precision mediump float;" +
				"" +
				"varying vec4 vertex_color;" +
				"" +
				"void main()" +
				"{" +
				"	gl_FragColor = vertex_color;" +
				"}";
				
		return new String[] { vs, fs };
	}
}
