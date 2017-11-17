#version 330

layout (location = 0) in vec3 position0;

uniform vec3 color;

void main()
{
	gl_Position = vec4(position0, 1);
}











