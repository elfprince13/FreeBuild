#version 330 core
uniform mat4x4 ViewProjection;
layout(location = 0) in vec4 vposition;
layout(location = 1) in mat4x4 txform;
layout(location = 5) in vec4 normal;
out vec2 hnorm;
out float dsfID;
void main() {
	dsfID = normal.w;
	vec4 tmp_normal = normal;
	tmp_normal.w = 0;
	tmp_normal = ViewProjection * txform * tmp_normal;
	
	vec3 np = normalize(normal.xyz + vec3(0,0,1));
	
	hnorm = np.xy;
	gl_Position = ViewProjection * txform * vposition;
}