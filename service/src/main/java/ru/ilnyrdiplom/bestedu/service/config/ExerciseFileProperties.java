package ru.ilnyrdiplom.bestedu.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("ru.ilnyrdiplom.bestedu.service.exercise-file")
public class ExerciseFileProperties {
    private String emptyFileText;
}
