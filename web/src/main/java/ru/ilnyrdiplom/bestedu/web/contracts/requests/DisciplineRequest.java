package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class DisciplineRequest {
    private String name;
    @JsonProperty("isPublic")
    private boolean isPublic;
    @Length(max = 500)
    private String description;
    @JsonProperty("isRemoved")
    private boolean isRemoved;
}
