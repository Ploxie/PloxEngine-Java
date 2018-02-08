#version 330

in vec2 uv;

out vec4 outColor;

uniform sampler2D bitmapSampler;
uniform vec4 color;

void main()
{
    vec4 c = texture(bitmapSampler, uv);
    outColor = c;
}
