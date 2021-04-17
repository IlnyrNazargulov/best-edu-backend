package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import lombok.Getter;
import lombok.Setter;
import ru.ilnyrdiplom.bestedu.facade.model.requests.ExerciseRequestFacade;

@Getter
@Setter
public class ExerciseRequest implements ExerciseRequestFacade {
    private String name;
    private int orderNumber;
}
