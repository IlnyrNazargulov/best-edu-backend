package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.model.Comment;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Comment findByExerciseAndId(Exercise exercise, int id);

    @Query("select c from Comment c " +
            "where c.exercise = :exercise and c.isRemoved = false")
    List<Comment> findComments(Exercise exercise);
}
