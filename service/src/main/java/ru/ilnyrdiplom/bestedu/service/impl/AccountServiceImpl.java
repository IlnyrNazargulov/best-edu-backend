package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ilnyrdiplom.bestedu.dal.model.RequestCode;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountStudent;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.AccountRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.AccountLoginException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongCredentialsException;
import ru.ilnyrdiplom.bestedu.facade.model.RequestCodeStatusFacade;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.requests.RegisterRequestFacade;
import ru.ilnyrdiplom.bestedu.facade.model.requests.UpdateAccountRequestFacade;
import ru.ilnyrdiplom.bestedu.facade.services.AccountServiceFacade;
import ru.ilnyrdiplom.bestedu.service.service.AccountService;
import ru.ilnyrdiplom.bestedu.service.service.EmailService;
import ru.ilnyrdiplom.bestedu.service.service.RequestCodeService;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountServiceFacade, AccountService {

    private final AccountRepository accountRepository;
    private final PasswordService passwordService;
    private final EmailService verificationEmailService;
    private final RequestCodeService requestCodeService;

    @Override
    public Account getByCredentials(String email, String plainPassword) throws WrongCredentialsException {
        Account existAccount = accountRepository.findAccountByLogin(email);
        if (existAccount == null) {
            throw new WrongCredentialsException(email);
        }
        if (passwordService.comparePassword(plainPassword, existAccount.getPasswordHash())) {
            return existAccount;
        }
        else {
            throw new WrongCredentialsException(email, true);
        }
    }

    @Override
    public AccountTeacher createAccountTeacher(RegisterRequestFacade registerRequestFacade, String email) throws AccountLoginException {
        Instant now = Instant.now();
        Account existAccount = accountRepository.findAccountByLogin(email);
        if (existAccount != null) {
            throw new AccountLoginException(email);
        }
        String hashPassword = passwordService.hashPassword(registerRequestFacade.getPlainPassword());
        AccountTeacher accountTeacher = new AccountTeacher(
                now,
                email,
                hashPassword,
                registerRequestFacade.getSecondName(),
                registerRequestFacade.getFirstName(),
                registerRequestFacade.getPatronymic()
        );
        return accountRepository.save(accountTeacher);
    }

    @Override
    public AccountStudent createAccountStudent(RegisterRequestFacade registerRequestFacade) throws AccountLoginException {
        Instant now = Instant.now();
        Account existAccount = accountRepository.findAccountByLogin(registerRequestFacade.getLogin());
        if (existAccount != null) {
            throw new AccountLoginException(registerRequestFacade.getLogin());
        }
        String hashPassword = passwordService.hashPassword(registerRequestFacade.getPlainPassword());
        AccountStudent accountStudent = new AccountStudent(
                now,
                registerRequestFacade.getLogin(),
                hashPassword,
                registerRequestFacade.getSecondName(),
                registerRequestFacade.getFirstName(),
                registerRequestFacade.getPatronymic()
        );
        return accountRepository.save(accountStudent);
    }

    @Override
    public Account getAccount(AccountIdentity accountIdentity) throws EntityNotFoundException {
        return accountRepository.findById(accountIdentity.getId())
                .orElseThrow(() -> new EntityNotFoundException(accountIdentity, Account.class));
    }

    @Override
    public List<Account> getAccountTeachers(String fullName, Pageable pageable) {
        return accountRepository.findAccounts(fullName, pageable);
    }

    @Override
    public AccountTeacher getAccountTeacher(AccountIdentity accountIdentity) throws EntityNotFoundException {
        Account account = accountRepository.findById(accountIdentity.getId())
                .orElseThrow(() -> new EntityNotFoundException(accountIdentity, Account.class));
        if (!(account instanceof AccountTeacher)) {
            throw new EntityNotFoundException(accountIdentity, AccountTeacher.class);
        }
        return (AccountTeacher) account;
    }

    @Override
    public AccountStudent getAccountStudent(AccountIdentity accountIdentity) throws EntityNotFoundException {
        Account account = accountRepository.findById(accountIdentity.getId())
                .orElseThrow(() -> new EntityNotFoundException(accountIdentity, Account.class));
        if (!(account instanceof AccountStudent)) {
            throw new EntityNotFoundException(accountIdentity, AccountTeacher.class);
        }
        return (AccountStudent) account;
    }

    @Override
    public RequestCodeStatusFacade registerRequestCode(String email) {
        Instant now = Instant.now();
        RequestCode requestCode = requestCodeService.create(email, now);
        RequestCodeStatusFacade requestCodeStatus = requestCodeService.tryRequestNewCodeOrGetExisting(requestCode);
        if (!requestCodeStatus.isCodeSent()) {
            return requestCodeStatus;
        }
        verificationEmailService.sendVerifyMessage(email, requestCode.getCode());
        requestCodeService.save(requestCode);
        return requestCodeStatus;
    }

    @Override
    @Transactional
    public void resetPassword(String email, String newPassword) throws EntityNotFoundException {
        Account existAccount = accountRepository.findAccountByLogin(email);
        if (existAccount == null) {
            throw new EntityNotFoundException(email, Account.class);
        }
        String newHashPassword = passwordService.hashPassword(newPassword);
        existAccount.setPasswordHash(newHashPassword);
    }

    @Override
    @Transactional
    public Account changePassword(AccountIdentity accountIdentity, String newPassword) throws EntityNotFoundException {
        Account existAccount = getAccount(accountIdentity);
        String newHashPassword = passwordService.hashPassword(newPassword);
        existAccount.setPasswordHash(newHashPassword);
        return existAccount;
    }

    @Override
    @Transactional
    public Account updateAccount(AccountIdentity accountIdentity, UpdateAccountRequestFacade changeUserInfoRequest)
            throws EntityNotFoundException {
        Account existAccount = getAccount(accountIdentity);
        existAccount.setSecondName(changeUserInfoRequest.getSecondName());
        existAccount.setPatronymic(changeUserInfoRequest.getPatronymic());
        existAccount.setFirstName(changeUserInfoRequest.getFirstName());
        return existAccount;
    }
}
