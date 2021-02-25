package ru.ilnyrdiplom.bestedu.facade.model;

import java.time.Instant;

public interface CommentFacade {
    Instant getCreatedAt();

    String getText();

    Integer getParentId();
}
