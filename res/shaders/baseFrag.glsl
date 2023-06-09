#version 330 core

in vec2 pass_texCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;

in float visibility;

out vec4 fragColor;

uniform vec3 lightColour;
uniform vec3 ambientLight;
uniform vec3 skyColour;

uniform sampler2D albedoSampler;

void main() {
    vec4 albedo_colour = texture(albedoSampler, pass_texCoords);

    if(albedo_colour.a < 0.5){
        discard;
    }

    vec3 normal = normalize(surfaceNormal);
    vec3 unitLightDir = normalize(toLightVector);

    float nDot1 = dot(normal, unitLightDir);
    float brightness = max(nDot1, 0.0);

    vec3 diffuse = lightColour * brightness + ambientLight;

    fragColor = vec4(diffuse, 1.0) * albedo_colour;
    fragColor = mix(vec4(skyColour, 1.0), fragColor, visibility);
}