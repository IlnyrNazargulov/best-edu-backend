package ru.ilnyrdiplom.bestedu.facade.model;

import java.time.Instant;
import java.util.UUID;

public interface FileFacade {
    UUID getUuid();

    String getName();

    long getSize();

    String getFileName();

    Instant getCreatedAt();
}
