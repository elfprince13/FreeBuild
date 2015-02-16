#version 330 core

uniform vec2 wOffset;
layout(location = 0) in vec2 vertPos;
layout(location = 1) in vec2 vertUV;
out vec2 uv;
void main() {
	gl_Position.xy = wOffset + vertPos;
    uv = vertUV;
}