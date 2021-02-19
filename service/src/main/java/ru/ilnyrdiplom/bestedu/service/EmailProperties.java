package ru.ilnyrdiplom.bestedu.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("ru.ilnyrdiplom.bestedu.email")
public class EmailProperties {
    private String serviceEmail;
    private String templateValidationEmail;
    private String subjectValidationEmail;
}
