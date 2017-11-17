#version 330

in vec2 uv;

out vec4 outColor;

uniform vec3 color;

void main()
{
	outColor = vec4(color, 1);
}
