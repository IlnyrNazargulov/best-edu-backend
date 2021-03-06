package ru.ilnyrdiplom.bestedu.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties("ru.ilnyrdiplom.bestedu.security")
public class SecurityProperties {
    private Duration accessTokenTTL;
    private Duration refreshTokenTTL;
    private Duration temporaryTokenTTL;
    private String signingKey;
    private String issuer;
}
