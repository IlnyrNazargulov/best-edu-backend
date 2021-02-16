package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.model.RequestCode;

public interface RequestCodeRepository extends CrudRepository<RequestCode, Integer> {
    RequestCode findTopRequestCodeByEmailOrderByCreatedAt(String login);
}
