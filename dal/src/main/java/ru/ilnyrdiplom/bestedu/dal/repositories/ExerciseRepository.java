package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.dto.ExerciseWithoutContent;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;

import java.util.List;

public interface ExerciseRepository extends CrudRepository<Exercise, Integer> {
    Exercise findByDisciplineAndNameAndIsRemovedFalse(Discipline discipline, String name);

    Exercise findByDisciplineAndIdAndIsRemovedFalse(Discipline discipline, int id);

    @Query("select new ru.ilnyrdiplom.bestedu.dal.dto.ExerciseWithoutContent(ex.id, ex.name, ex.createdAt, ex.orderNumber) " +
            "from Exercise ex " +
            "where ex.discipline = :discipline " +
            "and ex.isRemoved = false")
    List<ExerciseWithoutContent> findByDiscipline(Discipline discipline);
}
