#define MAX_LIGHT 6

const int ZERO = 0;
const int ONE = 1;
const int TWO = 2;

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;
uniform mat4 normalMatrix;
uniform mat4 transformationMatrix;

attribute vec4 vertex;
attribute vec3 normal;
attribute vec2 uv;

uniform mat4 light[MAX_LIGHT];
uniform vec3 eyePosition;

varying vec4 vertex_color;
varying vec2 uvCoord;

void skinningPosition(mat4 matrix, vec4 position, int index, out vec4 skinnedPosition)
{
	vec4 tmp = vec4(0.0);
	tmp.x = dot(matrix[index].xyz, position.xyz);
	tmp.y = dot(matrix[index+ONE].xyz, position.xyz);
	tmp.z = dot(matrix[index+TWO].xyz, position.xyz);
	tmp.w = 1.0;
	
	skinnedPosition += tmp;
}

void skinningNormal(mat4 matrix, vec3 position, int index, out vec3 skinnedNormal)
{
	vec3 tmp = vec3(0.0);
	tmp.x = dot(matrix[index].xyz, position.xyz);
	tmp.y = dot(matrix[index+ONE].xyz, position.xyz);
	tmp.z = dot(matrix[index+TWO].xyz, position.xyz);
	
	skinnedNormal += tmp;
}


void main()
{
	vec4 skinnedPosition = transformationMatrix*(vec4(vertex.xyz, 1.0));
	vec3 skinnedNormal = vec3(0.0);

	skinningNormal(transformationMatrix, normal, 0, skinnedNormal);
	
	vec4 aPosition = projectionMatrix*modelViewMatrix*skinnedPosition;

	vec3 eye = eyePosition-(skinnedPosition.xyz);
	vec3 N = normalize(skinnedNormal);

	vec4 color = vec4(1.0);
	
	vec4 Iamp = color*0.2;
	vec4 Idif = vec4(0.0);
	vec4 Ispec = vec4(0.0);

	for(int i = 0; i<MAX_LIGHT; ++i)
	{
		vec3 L = normalize(vec3(light[i][0].xyz - (skinnedPosition.xyz*light[i][0].w)));
		float lambertTerm = dot(N, L);
		if(lambertTerm > 0.0)
		{
			Idif += color*light[i][1]*lambertTerm;
			vec3 E = normalize(eye);
	     	vec3 R = reflect(-L, N);
	     	float specular = pow(max(dot(R, E), 0.0), light[i][3].x);
			Ispec += light[i][2]*specular;
		}
	}

	vertex_color =  Iamp + Idif + Ispec;
	uvCoord = uv;

	gl_Position = aPosition;
	
}