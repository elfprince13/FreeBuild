in vec2 hnorm;
in float dsfID;

layout(location = 0) out vec4 geomData;
layout(location = 1) out float depthData;

void main() {
	geomData.xy = hnorm;
	geomData.z = dsfID;
	geomData.w = gl_FragCoord.z;
	depthData = gl_FragCoord.z;
}