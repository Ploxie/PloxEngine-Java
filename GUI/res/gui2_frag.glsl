#version 330

in vec2 uv;

uniform ivec2 screenDimensions;
uniform vec4 backgroundColor;
uniform vec4 borderColor;
uniform int borderThickness;
uniform vec2 scale;
uniform sampler2D texture;

float borders(vec2 uv, int borderThickness) {

	float leftBorder = step(uv.x, ((borderThickness) / float(screenDimensions.x) / scale.x));
	float rightBorder = 1.0f - step(uv.x, 1.0f - ((borderThickness ) / float(screenDimensions.x) / scale.x));
	float bottomBorder = step(uv.y, ((borderThickness) / float(screenDimensions.y) / scale.y));
	float topBorder = 1.0f - step(uv.y, 1.0f - ((borderThickness) / float(screenDimensions.y) / scale.y));

	return rightBorder + leftBorder + topBorder + bottomBorder;
}

float gradient(float v) {
	return (v * 0.5f + 0.5f);
}

void main() {

	vec4 result;
	vec2 coord = uv;

	float borders = borders(uv, borderThickness);
	float background = 1.0f - borders;
	float gradient = gradient(uv.y);

	vec4 textureColor = texture2D(texture, uv);

	vec4 bg = (backgroundColor) + (textureColor * textureColor.w) + (vec4(1,1,1,1)  * textureColor.w);
	result = (bg * background);
	result.xyz *= gradient;
	result += borderColor * borders;
	gl_FragColor = result;
}
