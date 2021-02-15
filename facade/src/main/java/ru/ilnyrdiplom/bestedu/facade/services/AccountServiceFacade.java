package ru.ilnyrdiplom.bestedu.facade.services;

import ru.ilnyrdiplom.bestedu.facade.exceptions.AccountLoginException;
import ru.ilnyrdiplom.bestedu.facade.model.AccountTeacherFacade;
import ru.ilnyrdiplom.bestedu.facade.model.requests.RegisterRequestFacade;

public interface AccountServiceFacade {

    AccountTeacherFacade createAccountTeacher(RegisterRequestFacade registerRequestFacade) throws AccountLoginException;
}
