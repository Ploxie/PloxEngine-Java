#version 330

in vec4 ocolor;

out vec4 outColor;

void main()
{
	outColor = vec4(ocolor.xyz,1);
}
