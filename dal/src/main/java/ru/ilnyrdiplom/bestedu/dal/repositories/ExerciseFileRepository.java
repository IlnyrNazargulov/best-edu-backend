package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.ExerciseFile;

import java.util.List;
import java.util.UUID;

public interface ExerciseFileRepository extends CrudRepository<ExerciseFile, UUID> {
    @Query("select ef from ExerciseFile ef " +
            "join File f on ef.file = f and f.isRemoved = false " +
            "where ef.exercise = :exercise")
    List<ExerciseFile> findExerciseFile(Exercise exercise);
}
