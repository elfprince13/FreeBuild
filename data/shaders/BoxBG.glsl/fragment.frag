#version 330 core
uniform vec4 bgColor;
uniform sampler2D bgTexture;
out vec4 outColor;
in vec2 uv;

void main() {
    vec4 texCol;
    texCol = texture(bgTexture, uv);
    outColor = mix(bgColor,texCol,texCol.a);
}