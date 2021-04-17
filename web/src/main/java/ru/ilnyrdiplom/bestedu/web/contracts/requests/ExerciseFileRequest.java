package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import lombok.Getter;
import lombok.Setter;
import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;

import java.util.UUID;

@Getter
@Setter
public class ExerciseFileRequest {
    private UUID uuid;
    private ExerciseFileType type;
}
