package ru.ilnyrdiplom.bestedu.service.service;

import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;

public interface AccountService {
    Account getAccount(AccountIdentity accountIdentity) throws EntityNotFoundException;

    AccountTeacher getAccountTeacher(AccountIdentity accountIdentity) throws EntityNotFoundException;
}
