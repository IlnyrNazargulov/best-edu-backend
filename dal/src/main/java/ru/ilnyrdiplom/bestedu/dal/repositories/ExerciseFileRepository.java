package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.ExerciseFile;

import java.util.List;
import java.util.UUID;

public interface ExerciseFileRepository extends CrudRepository<ExerciseFile, UUID> {
    List<ExerciseFile> findByExercise(Exercise exercise);
}
