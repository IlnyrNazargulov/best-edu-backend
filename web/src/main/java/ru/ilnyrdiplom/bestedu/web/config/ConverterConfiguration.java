package ru.ilnyrdiplom.bestedu.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;


@ConfigurationProperties("ru.ilnyrdiplom.bestedu.web.file")
@Getter
@Setter
public class ConverterConfiguration {

    private String fileBaseUrl;

    @PostConstruct
    public void init() {
        if (fileBaseUrl.charAt(fileBaseUrl.length() - 1) != '/') {
            fileBaseUrl += "/";
        }
    }
}

