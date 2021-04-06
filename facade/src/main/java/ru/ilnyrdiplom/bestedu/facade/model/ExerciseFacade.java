package ru.ilnyrdiplom.bestedu.facade.model;

import java.time.Instant;
import java.util.List;

public interface ExerciseFacade {
    Integer getId();

    Instant getCreatedAt();

    String getName();

    int getOrderNumber();

    List<? extends ExerciseFileFacade> getExerciseFiles();
}
