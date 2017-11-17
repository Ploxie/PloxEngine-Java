#version 330

layout (location = 0) in vec3 inVertex;
layout (location = 1) in vec3 inColor;

out vec3 color;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main()
{
	color = inColor;

	gl_Position = projectionMatrix * viewMatrix* modelMatrix * vec4(inVertex.xyz, 1.0);

}
