#version 330

layout (location = 0) in vec3 inVertex;
layout (location = 1) in vec3 inColor;

out vec4 ocolor;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform vec3 bounds;

void main()
{
	ocolor = vec4(inColor.xyz, 1.0);

	gl_Position = projectionMatrix * viewMatrix* modelMatrix * vec4(inVertex.xyz * bounds, 1.0);

}
