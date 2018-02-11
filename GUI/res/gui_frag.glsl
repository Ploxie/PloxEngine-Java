#version 330

in vec2 uv;

uniform vec2 size;
uniform vec4 backgroundColor;
uniform vec4 borderColor;
uniform vec4 color;
uniform vec2 borderThickness;
uniform sampler2D texture;

/*float roundedCorner(vec2 uv, float intensity, float softness) {
	vec2 coords = (((uv * (size * intensity)) - .5) * 1.0f + 0.5f);
	return smoothstep(0, pow(softness * 0.2, 4), (coords.x * coords.y));
}

float roundedCorners(vec2 uv, float intensity, float softness) {
	float bottomLeft = roundedCorner(vec2(uv.x, uv.y), intensity, softness);
	float topLeft = roundedCorner(vec2(uv.x, 1 - uv.y), intensity, softness);
	float bottomRight = roundedCorner(vec2(1 - uv.x, 1 - uv.y), intensity,
			softness);
	float topRight = roundedCorner(vec2(1 - uv.x, uv.y), intensity, softness);
	return bottomLeft * bottomRight * topLeft * topRight;
}*/

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

	vec4 bg = (backgroundColor * background) + (textureColor * textureColor.w) + (color * textureColor.w);
	result = (bg * background);
	result.xyz *= gradient;
	result += borderColor * borders;
	gl_FragColor = result;
}
