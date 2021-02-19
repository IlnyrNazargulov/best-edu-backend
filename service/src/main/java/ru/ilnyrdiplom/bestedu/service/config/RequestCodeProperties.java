package ru.ilnyrdiplom.bestedu.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("ru.ddg.penoplex.service.request-code")
public class RequestCodeProperties {
    private int maxCodeRequestsPerInterval;
    private long requestCodeInterval;
    private long minInterval;
}
