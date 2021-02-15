package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.AccountRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.AccountLoginException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.requests.RegisterRequestFacade;
import ru.ilnyrdiplom.bestedu.facade.services.AccountServiceFacade;
import ru.ilnyrdiplom.bestedu.service.service.AccountService;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountServiceFacade, AccountService {

    private final AccountRepository accountRepository;
    private final PasswordService passwordService;

    @Override
    public AccountTeacher createAccountTeacher(RegisterRequestFacade registerRequestFacade) throws AccountLoginException {
        Instant now = Instant.now();
        Account existAccount = accountRepository.findAccountByLogin(registerRequestFacade.getLogin());
        if (existAccount != null) {
            throw new AccountLoginException(registerRequestFacade.getLogin());
        }
        String hashPassword = passwordService.hashPassword(registerRequestFacade.getPlainPassword());
        AccountTeacher accountTeacher = new AccountTeacher(
                now,
                registerRequestFacade.getLogin(),
                hashPassword,
                registerRequestFacade.getSecondName(),
                registerRequestFacade.getFirstName(),
                registerRequestFacade.getPatronymic()
        );
        return accountRepository.save(accountTeacher);
    }

    @Override
    public Account getAccount(AccountIdentity accountIdentity) throws EntityNotFoundException {
        return accountRepository.findById(accountIdentity.getId())
                .orElseThrow(() -> new EntityNotFoundException(accountIdentity, Account.class));
    }
}
