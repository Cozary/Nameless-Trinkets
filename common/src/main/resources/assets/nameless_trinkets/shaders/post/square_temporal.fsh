#version 330

uniform sampler2D InSampler;

in vec2 texCoord;

layout(std140) uniform SamplerInfo {
    vec2 OutSize;
    vec2 InSize;
};

layout(std140) uniform ColorConfig {
    vec3 RedMatrix;
    vec3 GreenMatrix;
    vec3 BlueMatrix;
};

layout(std140) uniform SquareConfig {
    float Progress;
    float BorderWidth;
    vec3 BorderColor;
};

out vec4 fragColor;

void main() {
    vec4 InTexel = texture(InSampler, texCoord);

    if (Progress <= 0.0) {
        fragColor = InTexel;
        return;
    }

    float RedValue = dot(InTexel.rgb, RedMatrix);
    float GreenValue = dot(InTexel.rgb, GreenMatrix);
    float BlueValue = dot(InTexel.rgb, BlueMatrix);
    vec3 ProcessedColor = vec3(RedValue, GreenValue, BlueValue);

    if (Progress >= 1.0) {
        fragColor = vec4(ProcessedColor, 1.0);
        return;
    }

    vec2 centered = abs(texCoord - vec2(0.5, 0.5));
    float aspect = OutSize.y > 0.0 ? (OutSize.x / OutSize.y) : (16.0 / 9.0);
    centered.x *= aspect;

    float squareDist = max(centered.x, centered.y);
    float halfBorder = max(BorderWidth, 0.005);

    float maxRadius = 0.5 * max(aspect, 1.0) + halfBorder;
    float currentRadius = Progress * maxRadius;

    if (squareDist < currentRadius - halfBorder) {
        fragColor = vec4(ProcessedColor, 1.0);
    } else if (squareDist <= currentRadius + halfBorder) {
        float rawFactor = 1.0 - (abs(squareDist - currentRadius) / halfBorder);
        float edgeFactor = smoothstep(0.0, 1.0, rawFactor);
        vec3 edgeGlow = mix(ProcessedColor, BorderColor * 1.25, edgeFactor);
        fragColor = vec4(edgeGlow, 1.0);
    } else {
        fragColor = InTexel;
    }
}
