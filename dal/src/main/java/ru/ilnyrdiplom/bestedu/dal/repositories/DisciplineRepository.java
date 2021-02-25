package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;

import java.util.List;

public interface DisciplineRepository extends CrudRepository<Discipline, Integer> {
    Discipline findDisciplineByNameAndTeacherAndIsRemovedFalse(String name, AccountTeacher teacher);

    Discipline findDisciplineByIdAndTeacherAndIsRemovedFalse(int id, AccountTeacher teacher);

    @Query("select di from Discipline di " +
            "join AccountTeacher teacher on di.teacher = teacher " +
            "where (:nameDiscipline is null or lower(di.name) like lower(cast(concat('%', :nameDiscipline, '%') as text))) " +
            "and (:teacher is null or teacher = :teacher) " +
            "and (:teacherFullName is null " +
            "or lower(teacher.secondName || ' ' || teacher.firstName || ' ' || teacher.patronymic) like lower(cast(concat('%', :teacherFullName, '%') as text))) " +
            "and (di.isPublic = true or exists(select ad from AccessDiscipline ad where ad.discipline = di and ad.student = :currentAccount)) " +
            "and (di.isRemoved = false)")
    List<Discipline> findDisciplines(Account currentAccount, AccountTeacher teacher, String teacherFullName, String nameDiscipline);
}
