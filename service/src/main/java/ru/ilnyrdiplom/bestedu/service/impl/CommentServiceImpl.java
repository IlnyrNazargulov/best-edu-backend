package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.dal.model.Comment;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.dal.repositories.CommentRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleAccessDisciplineException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccountTypeException;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.CommentIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.ExerciseIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.requests.CommentRequestFacade;
import ru.ilnyrdiplom.bestedu.facade.services.CommentServiceFacade;
import ru.ilnyrdiplom.bestedu.service.service.AccountService;
import ru.ilnyrdiplom.bestedu.service.service.DisciplineService;
import ru.ilnyrdiplom.bestedu.service.service.ExerciseService;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentServiceFacade {
    private final CommentRepository commentRepository;
    private final AccountService accountService;
    private final DisciplineService disciplineService;
    private final ExerciseService exerciseService;

    @Override
    public Comment createComment(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            CommentRequestFacade commentRequest
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        Instant now = Instant.now();
        Account account = accountService.getAccount(accountIdentity);
        Discipline discipline = disciplineService.getDiscipline(accountIdentity, disciplineIdentity);
        Exercise exercise = exerciseService.getExerciseByDiscipline(discipline, exerciseIdentity);
        Comment parentComment = null;
        if (commentRequest.getParentId() != null) {
            parentComment = commentRepository.findById(commentRequest.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException(commentRequest.getParentId(), Comment.class));
        }
        Comment comment = Comment.builder()
                .createdAt(now)
                .exercise(exercise)
                .author(account)
                .parent(parentComment)
                .text(commentRequest.getText())
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteComment(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            CommentIdentity commentIdentity
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        Discipline discipline = disciplineService.getDiscipline(accountIdentity, disciplineIdentity);
        Exercise exercise = exerciseService.getExerciseByDiscipline(discipline, exerciseIdentity);
        Comment comment = commentRepository.findByExerciseAndId(exercise, commentIdentity.getId());
        if (comment == null) {
            throw new EntityNotFoundException(commentIdentity, Comment.class);
        }
        comment.setRemoved(true);
        return commentRepository.save(comment);
    }
}
