precision mediump float;

uniform sampler2D texture;
uniform float hasTexture;

varying vec4 vertex_color;
varying vec2 uvCoord;

void main()
{
	if(hasTexture > 1.0)
	{
		vec4 color = texture2D(texture, uvCoord);
		gl_FragColor = color*vertex_color;
	}
	else
	{
		gl_FragColor = vertex_color;
	}
}