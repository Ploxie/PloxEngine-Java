#version 330

layout (location = 0) in vec3 inVertex;
layout (location = 1) in vec2 inUV;

out vec2 uv;

void main()
{
	uv = inUV;

	vec2 pos = inVertex.xy;
	pos.y = 1-pos.y;
	pos =  pos  * vec2(2) - vec2(1);

	gl_Position = vec4(pos, 0.0, 1.0);

}
