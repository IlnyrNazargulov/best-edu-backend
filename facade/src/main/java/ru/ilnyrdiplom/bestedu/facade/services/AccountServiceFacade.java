package ru.ilnyrdiplom.bestedu.facade.services;

import org.springframework.data.domain.Pageable;
import ru.ilnyrdiplom.bestedu.facade.exceptions.AccountLoginException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongCredentialsException;
import ru.ilnyrdiplom.bestedu.facade.model.AccountFacade;
import ru.ilnyrdiplom.bestedu.facade.model.AccountStudentFacade;
import ru.ilnyrdiplom.bestedu.facade.model.AccountTeacherFacade;
import ru.ilnyrdiplom.bestedu.facade.model.RequestCodeStatusFacade;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.requests.RegisterRequestFacade;
import ru.ilnyrdiplom.bestedu.facade.model.requests.UpdateAccountRequestFacade;

import java.util.List;

public interface AccountServiceFacade {

    AccountTeacherFacade createAccountTeacher(RegisterRequestFacade registerRequestFacade, String email) throws AccountLoginException;

    AccountStudentFacade createAccountStudent(RegisterRequestFacade registerRequestFacade) throws AccountLoginException;

    RequestCodeStatusFacade registerRequestCode(String email);

    void resetPassword(String email, String newPassword) throws EntityNotFoundException;

    AccountFacade changePassword(AccountIdentity accountIdentity, String newPassword) throws EntityNotFoundException;

    AccountFacade updateAccount(AccountIdentity accountIdentity, UpdateAccountRequestFacade changeUserInfoRequest)
            throws EntityNotFoundException;

    AccountFacade getByCredentials(String email, String plainPassword) throws WrongCredentialsException;

    AccountFacade getAccount(AccountIdentity accountIdentity) throws EntityNotFoundException;

    List<? extends AccountFacade> getAccountTeachers(String fullName, Pageable pageable);

    AccountFacade deleteAccount(AccountIdentity accountIdentity) throws EntityNotFoundException;
}
