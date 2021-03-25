package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
public class DisciplineRequest {
    private String name;
    @JsonProperty("isPublic")
    private boolean isPublic;
}
