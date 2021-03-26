package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisciplineRequest {
    private String name;
    @JsonProperty("isPublic")
    private boolean isPublic;
    private String description;
}
