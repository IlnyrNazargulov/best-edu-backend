package ru.ilnyrdiplom.bestedu.facade.model;

import java.time.Instant;

public interface ExerciseWithoutContentFacade {
    int getId();

    Instant getCreatedAt();

    String getName();

    int getOrderNumber();
}
