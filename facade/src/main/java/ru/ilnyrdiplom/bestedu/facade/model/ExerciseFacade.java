package ru.ilnyrdiplom.bestedu.facade.model;

import java.time.Instant;

public interface ExerciseFacade {
    Integer getId();

    Instant getCreatedAt();

    String getName();

    int getOrderNumber();

    ExerciseFileFacade getContent();
}
