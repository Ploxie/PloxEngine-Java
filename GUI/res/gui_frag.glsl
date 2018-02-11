#version 330

in vec2 uv;

uniform vec2 size;
uniform vec4 backgroundColor;
uniform vec4 borderColor;
uniform vec4 color;
uniform vec2 borderThickness;
uniform sampler2D texture;

float borders(vec2 uv, vec2 borderThickness, vec2 size) {
	float leftBorder = step(uv.x, borderThickness.x / size.x);
	float rightBorder = 1.0f - step(uv.x, 1.0f - (borderThickness.x / size.x));
	float bottomBorder = step(uv.y, (borderThickness.y / size.y));
	float topBorder = 1.0f - step(uv.y, 1.0f - (borderThickness.y / size.y));

	return rightBorder + leftBorder + topBorder + bottomBorder;
}

float gradient(float v) {
	return (v * 0.5f + 0.5f);
}

void main() {

	vec4 result;
	vec2 coord = uv;

	float borders = borders(uv, borderThickness, size);
	float background = 1.0f - borders;
	float gradient = gradient(uv.y);

	vec4 textureColor = texture2D(texture, uv);

	vec4 bg = (backgroundColor) + (textureColor * textureColor.w) + (color * textureColor.w);
	result = (bg * background);
	result.xyz *= gradient;
	result += borderColor * borders;
	gl_FragColor = result;
}
