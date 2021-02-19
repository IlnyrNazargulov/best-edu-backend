package ru.ilnyrdiplom.bestedu.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("ru.ilnyrdiplom.bestedu.refresh-token")
public class RefreshTokenProperties {
    private int maxTokensPerHour;
}
