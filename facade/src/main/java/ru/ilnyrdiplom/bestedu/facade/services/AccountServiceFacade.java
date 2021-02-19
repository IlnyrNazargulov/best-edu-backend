package ru.ilnyrdiplom.bestedu.facade.services;

import ru.ilnyrdiplom.bestedu.facade.exceptions.AccountLoginException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.model.AccountFacade;
import ru.ilnyrdiplom.bestedu.facade.model.AccountStudentFacade;
import ru.ilnyrdiplom.bestedu.facade.model.AccountTeacherFacade;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.requests.ChangeUserInfoRequestFacade;
import ru.ilnyrdiplom.bestedu.facade.model.requests.RegisterRequestFacade;

public interface AccountServiceFacade {

    AccountTeacherFacade createAccountTeacher(RegisterRequestFacade registerRequestFacade) throws AccountLoginException;

    AccountStudentFacade createAccountStudent(RegisterRequestFacade registerRequestFacade) throws AccountLoginException;

    void registerRequestCode(String email) throws AccountLoginException;

    AccountFacade resetPassword(String email, String newPassword) throws EntityNotFoundException;

    AccountFacade changePassword(AccountIdentity accountIdentity, String newPassword) throws EntityNotFoundException;

    AccountFacade changeUserInfo(AccountIdentity accountIdentity, ChangeUserInfoRequestFacade changeUserInfoRequest)
            throws EntityNotFoundException;
}
