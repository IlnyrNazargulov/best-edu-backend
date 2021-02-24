package ru.ilnyrdiplom.bestedu.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties("ru.ilnyrdiplom.bestedu.file")
public class FileProperties {
    private String uploadsPath;

    private Duration minUserFileLifetime;

    private long maxImageSize;
}
