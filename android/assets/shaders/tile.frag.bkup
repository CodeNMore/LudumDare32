#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

uniform sampler2D u_texture;

varying LOWP vec4 v_color;
varying vec2 v_texCoords;

//LIGHTING
uniform vec2 lightPosition;
uniform vec3 lightColor;
uniform int lightIntensity;
uniform int lightShowness;

void main(){
	vec4 originalColor = texture2D(u_texture, v_texCoords);
	float dist = distance(lightPosition, gl_FragCoord.xy) + lightShowness;
	float att = 1.0 / dist;
	vec4 mixedColor = vec4(att, att, att, 1.0);
	mixedColor = mix(originalColor * vec4(lightColor, 1.0), mixedColor, 0.1);
	mixedColor /= dist / lightIntensity;
	
	//gl_FragColor = originalColor;
	gl_FragColor = mixedColor * v_color;
}