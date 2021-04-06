package ru.ilnyrdiplom.bestedu.web.contracts.requests.dto;

import lombok.Getter;
import lombok.Setter;
import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;
import ru.ilnyrdiplom.bestedu.facade.model.requests.dto.ExerciseFileDtoFacade;

import java.util.UUID;

@Getter
@Setter
public class ExerciseFileDto implements ExerciseFileDtoFacade {
    private UUID uuid;
    private ExerciseFileType exerciseFileType;
}
