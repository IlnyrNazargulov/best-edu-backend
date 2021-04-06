package ru.ilnyrdiplom.bestedu.facade.model.requests;

import ru.ilnyrdiplom.bestedu.facade.model.requests.dto.ExerciseFileDtoFacade;

import java.util.List;

public interface ExerciseRequestFacade {
    String getName();

    int getOrderNumber();

    String getContent();

    List<? extends ExerciseFileDtoFacade> getExerciseFiles();
}
