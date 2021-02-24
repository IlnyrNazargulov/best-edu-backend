package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findAccountByLogin(String login);

    @Query("select teacher from AccountTeacher teacher " +
            "where (:fullName is null " +
            "or lower(teacher.secondName || ' ' || teacher.firstName || ' ' || teacher.patronymic) like lower(cast(concat('%', :fullName, '%') as text)))")
    List<Account> findAccounts(String fullName, Pageable pageable);

}
