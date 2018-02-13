#version 330

layout (location = 0) in vec3 inVertex;
layout (location = 1) in vec2 inUV;

out vec2 uv;

uniform mat4 transformMatrix;

void main()
{
	uv = inUV;



	vec4 outVertex = transformMatrix * vec4(inVertex, 1);

	outVertex.y = 1-outVertex.y;
	outVertex.xyz =  outVertex.xyz  * vec3(2) - vec3(1);

	gl_Position = outVertex;

}
