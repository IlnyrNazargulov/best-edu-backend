package ru.ilnyrdiplom.bestedu.facade.model;

import java.time.Instant;

public interface NotificationFacade {
    Instant getCreatedAt();

    String getText();
}
