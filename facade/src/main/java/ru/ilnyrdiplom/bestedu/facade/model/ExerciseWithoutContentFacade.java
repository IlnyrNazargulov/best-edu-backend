package ru.ilnyrdiplom.bestedu.facade.model;

import java.time.Instant;

public interface ExerciseWithoutContentFacade {
    Instant getCreatedAt();

    String getName();

    int getOrderNumber();
}
