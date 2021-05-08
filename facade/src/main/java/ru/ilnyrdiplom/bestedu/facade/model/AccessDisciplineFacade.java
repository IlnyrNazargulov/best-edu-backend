package ru.ilnyrdiplom.bestedu.facade.model;

import ru.ilnyrdiplom.bestedu.facade.model.enums.AccessDisciplineStatus;

public interface AccessDisciplineFacade {
    Integer getId();

    AccountFacade getStudent();

    AccessDisciplineStatus getStatus();

    DisciplineFacade getDiscipline();
}
