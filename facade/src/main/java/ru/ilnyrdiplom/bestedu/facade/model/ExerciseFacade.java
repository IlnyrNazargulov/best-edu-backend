package ru.ilnyrdiplom.bestedu.facade.model;

import java.time.Instant;

public interface ExerciseFacade {
    Instant getCreatedAt();

    String getName();

    String getContent();

    int getOrderNumber();
}
