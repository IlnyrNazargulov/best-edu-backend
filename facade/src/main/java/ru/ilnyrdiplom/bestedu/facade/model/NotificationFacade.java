package ru.ilnyrdiplom.bestedu.facade.model;

import java.time.Instant;

public interface NotificationFacade {
    Integer getId();

    Instant getCreatedAt();

    String getText();
}
