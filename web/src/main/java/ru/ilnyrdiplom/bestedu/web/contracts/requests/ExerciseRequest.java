package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import lombok.Getter;
import lombok.Setter;
import ru.ilnyrdiplom.bestedu.facade.model.requests.ExerciseRequestFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.requests.dto.ExerciseFileDto;

import java.util.List;

@Getter
@Setter
public class ExerciseRequest implements ExerciseRequestFacade {
    private String name;
    private int orderNumber;

    private String content;

    private List<ExerciseFileDto> exerciseFiles;
}
