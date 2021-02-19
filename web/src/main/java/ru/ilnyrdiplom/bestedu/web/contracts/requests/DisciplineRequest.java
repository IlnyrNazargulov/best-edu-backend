package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisciplineRequest {
    private String name;
    private boolean isPublic;
}
