package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.model.AccessDiscipline;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.facade.model.enums.AccessDisciplineStatus;

import java.util.List;

public interface AccessDisciplineRepository extends CrudRepository<AccessDiscipline, Integer> {
    @Query("from AccessDiscipline ad where ad.discipline = :discipline and ad.student = :account")
    AccessDiscipline findAccessDiscipline(Discipline discipline, Account account);

    @Query("from AccessDiscipline ad where ad.discipline = :discipline and ad.id = :id")
    AccessDiscipline findAccessDiscipline(Discipline discipline, int id);

    @Query("from AccessDiscipline ad where ad.discipline = :discipline and " +
            "(:status is null or ad.status = :status)")
    List<AccessDiscipline> findAccessDisciplines(Discipline discipline, AccessDisciplineStatus status);
}
