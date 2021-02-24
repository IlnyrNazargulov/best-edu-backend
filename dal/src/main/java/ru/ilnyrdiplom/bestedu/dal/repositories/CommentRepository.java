package ru.ilnyrdiplom.bestedu.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ilnyrdiplom.bestedu.dal.model.Comment;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Comment findByExerciseAndId(Exercise exercise, int id);
}
