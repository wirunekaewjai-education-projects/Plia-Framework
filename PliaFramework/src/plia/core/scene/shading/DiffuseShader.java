package plia.core.scene.shading;

final class DiffuseShader extends Shader
{
	
	private DiffuseShader()
	{
		
	}
	
	@Override
	void loadShader()
	{
		instance.programs[0] = new ShaderProgram(getDiffuseSrc01());
		instance.programs[1] = new ShaderProgram(getDiffuseSrc02());
		instance.programs[2] = new ShaderProgram(getDiffuseSrc03());
		instance.programs[3] = new ShaderProgram(getDiffuseSrc04());
		instance.programs[5] = new ShaderProgram(getDiffuseSrc06());
		instance.programs[6] = new ShaderProgram(getDiffuseSrc07());
	}
	
	private static DiffuseShader instance = new DiffuseShader();
	static DiffuseShader getInstance()
	{
		return instance;
	}
	
	private static final String matrixUniform = 
			"uniform mat4 modelViewProjectionMatrix;" +
			"uniform mat4 worldMatrix;" +
			"uniform mat3 normalMatrix;";
	
	private static final String terrainMatrixUniform = 
			"uniform mat4 modelViewProjectionMatrix;" +
			"uniform mat4 worldMatrix;";
	
	private static final String lightAttribute = 
			"const int MAX_LIGHTS = 6;" +
			"uniform vec4 lightPosition[MAX_LIGHTS];" +
			"uniform vec4 lightColor[MAX_LIGHTS];" +
			"uniform float lightRange[MAX_LIGHTS];" +
			"uniform float lightIntensity[MAX_LIGHTS];" +
			"uniform float lightAbsorbMultiplier;" +
			"uniform float lightCount;";
	
	private static final String vertexAndNormalAttribute = 
			"attribute vec4 vertex;" +
			"attribute vec3 normal;";
	
	private static final String vertexNormalAndUVAttribute = 
			 vertexAndNormalAttribute +
			"attribute vec2 uv;";

	private static final String boneAttributeAndMatrixPaletteUniform = 
			"const int MATRICES_SIZE = 96;" +
			"uniform mat4 matrixPalette[MATRICES_SIZE];" +
			"attribute vec4 boneIndices;" +
			"attribute vec4 boneWeights;" +
			"attribute float boneCount;";

	private static final String iDifVarying = 
			"varying lowp vec4 Idif;";
	
	private static final String uvVarying = 
			"varying lowp vec2 uvCoord;";
	
	private static final String initialIDif = 
			"	Idif = vec4(0.0);";
	
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
			"			skinnedNormal += wt * vec3(mat3(matrix) * normal);" +
			"		}" +
			"	}";
	
	private static String initialVN_WithoutSkinning = 
			"vec4 V = worldMatrix * vertex;" +
			"vec3 N = normalMatrix * normal;";

	private static String initialVN_WithSkinning = 
			"vec4 V = worldMatrix * skinnedPosition;" +
			"vec3 N = normalMatrix * skinnedNormal;";
	
	private static String initalVN_Terrain = 
			"vec4 V = worldMatrix * position;" +
			"vec3 N = normal;";
	
	private static String initalVN_Terrain2 = 
			"vec4 V = worldMatrix * vertex;" +
			"vec3 N = normal;";
	
	private static String lightLoop = 
			"	int lCount = int(lightCount);" +
			"	for(int l = 0; l < lCount; ++l)" +
			"	{" +
			"		vec4 lightDir = lightPosition[l];" +
			"		vec3 lp = lightDir.xyz - (V.xyz * lightDir.w);" +
			"		vec3 L = normalize( lp );" +
			"		float lambertTerm = dot(N, L);" +
			"		if(lambertTerm > 0.0)" +
			"		{" +
			"			if(lightDir.w > 0.0)" +
			"			{" +
			"				float dist = length(lp);" +
			"				float att = dist / lightRange[l];" +
			"				lambertTerm /= max(0.01, att);" +
			"			}" +
			"			Idif += lightColor[l] * lambertTerm * lightIntensity[l] * lightAbsorbMultiplier;" +
			"		}" +
			"	}";
	
	private static final String initialUVCoordVarying = 
			"	uvCoord = uv;";

	//
	private static final String diffuseMap = 
			"uniform sampler2D diffuseMap;";
	
	private static final String normalMap = 
			"uniform sampler2D normalMap;";
	
	private static final String heightMap = 
			"uniform sampler2D heightMap;";
	
//	private static final String displacementAttribute = 
//			"uniform float size;" +
//			"uniform float height;";
	
	private static String fsWithTexture = 
			"precision mediump float;" +
			"" +
			diffuseMap +
			uvVarying +
			iDifVarying +
			"" +
			"void main()" +
			"{" +
			"	vec4 texColor = texture2D(diffuseMap, uvCoord);" +
			"	texColor.r *= Idif.r;" +
			"	texColor.g *= Idif.g;" +
			"	texColor.b *= Idif.b;" +
			"	texColor.a *= Idif.a;" +
			"	gl_FragColor = texColor;" +
			"}";

	private static String fsWithOutTexture = 
			"precision mediump float;" +
			"" +
			"uniform vec4 color;" +
			iDifVarying +
			"" +
			"void main()" +
			"{" +
			"	gl_FragColor = color * Idif; " +
			"}";
	
	///////////////////////////////
	private static String[] getDiffuseSrc01()
	{
		// Mesh Without Texture
		String vs =
				matrixUniform +
				lightAttribute +
				vertexAndNormalAttribute +
				iDifVarying +
				"" +
				"void main()" +
				"{" +
					initialIDif +
					initialVN_WithoutSkinning +
					lightLoop +
				"" +
				"	gl_Position = modelViewProjectionMatrix * V;" +
				"}";

		return new String[] {vs, fsWithOutTexture};
	}
	
	private static String[] getDiffuseSrc02()
	{
		// Skinned-Mesh Without Texture
		String vs =
				matrixUniform +
				lightAttribute +
				vertexAndNormalAttribute +
				boneAttributeAndMatrixPaletteUniform +
				iDifVarying +
				"" +
				"void main()" +
				"{" +
					initialIDif +
					vertexSkinningLoop +
					initialVN_WithSkinning +
					lightLoop +
				"" +
				"	gl_Position = modelViewProjectionMatrix * V;" +
				"}";

		return new String[] {vs, fsWithOutTexture};
	}
	
	private static String[] getDiffuseSrc03()
	{
		// Mesh With Texture
		String vs =
				matrixUniform +
				lightAttribute +
				vertexNormalAndUVAttribute +
				iDifVarying +
				uvVarying +
				"" +
				"void main()" +
				"{" +
					initialIDif +
					initialVN_WithoutSkinning +
					lightLoop +
					initialUVCoordVarying +
				"	gl_Position = modelViewProjectionMatrix * V;" +
				"}";

		return new String[] {vs, fsWithTexture};
	}
	
	private static String[] getDiffuseSrc04()
	{
		// Skinned-Mesh With Texture
		String vs =
				matrixUniform +
				lightAttribute +
				vertexNormalAndUVAttribute +
				boneAttributeAndMatrixPaletteUniform +
				iDifVarying +
				uvVarying +
				"" +
				"void main()" +
				"{" +
					initialIDif +
					vertexSkinningLoop +
					initialVN_WithSkinning +
					lightLoop +
					initialUVCoordVarying +
				"	gl_Position = modelViewProjectionMatrix * V;" +
				"}";

		return new String[] {vs, fsWithTexture};
	}
	
	private static String[] getDiffuseSrc06()
	{
		// Terrain With Texture
		String vs = 
				terrainMatrixUniform + 
				lightAttribute + 
				heightMap +
				normalMap +
				iDifVarying +
				uvVarying +
				"uniform vec3 terrainData;" +
				"attribute vec4 vertex;" +
				"" +
				"void main()" +
				"{" +
					initialIDif +

				"	float segSize = terrainData.z / terrainData.y;" +
				"	float u = clamp(vertex.x / terrainData.y, 0.0, 1.0);" +
				"	float v = clamp(vertex.y / terrainData.y, 0.0, 1.0);" +
				"	vec2 uv = vec2(u, v);" +
				
				"	vec4 displace = texture2D(heightMap, uv);" +
				"	vec3 normal = vec3((texture2D(normalMap, uv).xyz - 0.5) * 2.0);" +

				"	float height = displace.x * terrainData.x;" +

				"	vec4 position = vec4(vertex.x * segSize, vertex.y * segSize, height, 1.0);" +
				
					initalVN_Terrain +
					lightLoop +
					initialUVCoordVarying +
				"	gl_Position = modelViewProjectionMatrix * V;" +
				"}";
		
		return new String[] { vs, fsWithTexture };
	}
	
	private static String[] getDiffuseSrc07()
	{
		// Static Terrain With Texture
		String vs = 
				terrainMatrixUniform + 
				lightAttribute + 
				normalMap +
				iDifVarying +
				uvVarying +
				"uniform vec3 terrainData;" +
				"attribute vec4 vertex;" +
				"" +
				"void main()" +
				"{" +
					initialIDif +

				"	float segSize = terrainData.z / terrainData.y;" +
				"	float u = clamp(vertex.x / terrainData.z, 0.0, 1.0);" +
				"	float v = clamp(vertex.y / terrainData.z, 0.0, 1.0);" +
				"	vec2 uv = vec2(u, v);" +
				
				"	vec3 normal = vec3((texture2D(normalMap, uv).xyz - 0.5) * 2.0);" +

					initalVN_Terrain2 +
					lightLoop +
					initialUVCoordVarying +
				"	gl_Position = modelViewProjectionMatrix * V;" +
				"}";
		
		return new String[] { vs, fsWithTexture };
	}
}
