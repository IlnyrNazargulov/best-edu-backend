package ru.ilnyrdiplom.bestedu.facade.model;

import java.time.Instant;

public interface DisciplineFacade {
    Instant getCreatedAt();

    String getName();

    boolean isPublic();

    boolean isRemoved();

    String getDescription();
}
