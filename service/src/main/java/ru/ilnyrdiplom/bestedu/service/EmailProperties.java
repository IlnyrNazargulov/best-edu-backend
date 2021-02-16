package ru.ilnyrdiplom.bestedu.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("ru.ilnyrdiplom.bestedu.email")
public class EmailProperties {
    private String addressee;
    private String template;
    private String subject;
}
