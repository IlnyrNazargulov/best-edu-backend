package ru.ilnyrdiplom.bestedu.facade.model;

import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;

import java.time.Instant;
import java.time.LocalDate;

public interface AccountFacade {
    Integer getId();

    Role getRole();

    String getSecondName();

    String getFirstName();

    String getPatronymic();

    Instant getCreatedAt();

    String getLogin();

    Long getCountDisciplines();

    LocalDate getBirthdate();

    String getRank();

    String getInformation();
}
