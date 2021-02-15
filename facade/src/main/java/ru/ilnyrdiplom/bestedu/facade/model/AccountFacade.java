package ru.ilnyrdiplom.bestedu.facade.model;

import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;

import java.time.Instant;

public interface AccountFacade {
    Integer getId();

    Role getRole();

    String getSecondName();

    String getFirstName();

    String getPatronymic();

    Instant getCreatedAt();
}
