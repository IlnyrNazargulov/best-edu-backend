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
            "where ef.exercise = :exercise and ef.exerciseFileType <> 'CONTENT'")
    List<ExerciseFile> findExerciseFiles(Exercise exercise);

    @Query("select ef from ExerciseFile ef " +
            "where ef.exercise = :exercise and ef.exerciseFileType = 'CONTENT'")
    ExerciseFile findExerciseContentFile(Exercise exercise);

    @Query("select ef from ExerciseFile ef " +
            "where ef.exercise = :exercise and ef.fileUuid = :fileId and ef.exerciseFileType <> 'CONTENT'")
    ExerciseFile findExerciseFile(Exercise exercise, UUID fileId);
}
