#version 330

in vec2 uv;

out vec4 color;

uniform sampler2D bitmapSampler;

void main()
{
    vec4 c = texture(bitmapSampler, uv);
    color = vec4(c.xyzw);
}
