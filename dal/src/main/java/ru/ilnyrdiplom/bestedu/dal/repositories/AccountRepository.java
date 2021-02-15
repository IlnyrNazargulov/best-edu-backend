package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findAccountByLogin(String login);
}
