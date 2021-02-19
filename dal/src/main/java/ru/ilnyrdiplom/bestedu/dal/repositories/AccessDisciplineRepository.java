package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.model.AccessDiscipline;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountStudent;

public interface AccessDisciplineRepository extends CrudRepository<AccessDiscipline, Integer> {
    AccessDiscipline findAccessDisciplineByDisciplineAndStudent(Discipline discipline, AccountStudent accountStudent);
}
