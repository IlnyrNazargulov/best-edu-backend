package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;

public interface DisciplineRepository extends CrudRepository<Discipline, Integer> {
    Discipline findDisciplineByNameAndTeacher(String name, AccountTeacher teacher);

    Discipline findDisciplineByIdAndTeacher(int id, AccountTeacher teacher);
}
