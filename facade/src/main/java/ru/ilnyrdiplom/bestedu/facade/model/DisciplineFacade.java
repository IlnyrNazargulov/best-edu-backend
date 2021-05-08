package ru.ilnyrdiplom.bestedu.facade.model;

import java.time.Instant;

public interface DisciplineFacade {
    Integer getId();

    Instant getCreatedAt();

    String getName();

    boolean isPublic();

    boolean isVisible();

    boolean isRemoved();

    String getDescription();

    AccountTeacherFacade getTeacher();
}
