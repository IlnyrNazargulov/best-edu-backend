package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.File;

import java.util.UUID;

public interface FileRepository extends CrudRepository<File, UUID> {
    @Transactional
    @Modifying
    @Query("update File f set f.isRemoved = :removed where f = :file")
    int updateRemoved(@Param("file") File file, @Param("removed") boolean isRemoved);

    @Query("select file from File file " +
            "join ExerciseFile exFile on exFile.file = file and exFile.exercise = :exercise " +
            "where file.uuid = :fileUuid")
    File findFileByExerciseAndId(Exercise exercise, UUID fileUuid);

}
