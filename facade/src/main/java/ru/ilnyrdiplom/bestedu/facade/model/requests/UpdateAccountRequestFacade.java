package ru.ilnyrdiplom.bestedu.facade.model.requests;

import java.time.LocalDate;

public interface UpdateAccountRequestFacade {
    String getSecondName();

    String getFirstName();

    String getPatronymic();

    LocalDate getBirthdate();

    String getRank();

    String getInformation();
}
