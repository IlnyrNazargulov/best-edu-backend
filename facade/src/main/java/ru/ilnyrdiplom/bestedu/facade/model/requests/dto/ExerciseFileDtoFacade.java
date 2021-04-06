package ru.ilnyrdiplom.bestedu.facade.model.requests.dto;

import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;

import java.util.UUID;

public interface ExerciseFileDtoFacade {
    UUID getUuid();

    ExerciseFileType getExerciseFileType();
}
