package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.dto.ExerciseWithoutContent;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;

import java.util.List;

public interface ExerciseRepository extends CrudRepository<Exercise, Integer> {
    Exercise findByDisciplineAndName(Discipline discipline, String name);

    Exercise findByDisciplineAndId(Discipline discipline, int id);

    @Query("select new ru.ilnyrdiplom.bestedu.dal.dto.ExerciseWithoutContent(ex.id, ex.name, ex.createdAt, ex.orderNumber) " +
            "from Exercise ex " +
            "where ex.discipline = :discipline")
    List<ExerciseWithoutContent> findByDiscipline(Discipline discipline);

    @Query("select ex from Exercise ex " +
            "join Discipline di on di = ex.discipline and di.teacher = :teacher " +
            "where ex.id = :id")
    Exercise findByAccountAndId(AccountTeacher teacher, int id);
}
