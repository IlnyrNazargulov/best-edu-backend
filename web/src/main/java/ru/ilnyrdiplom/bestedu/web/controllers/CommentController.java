package ru.ilnyrdiplom.bestedu.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleAccessDisciplineException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccountTypeException;
import ru.ilnyrdiplom.bestedu.facade.model.CommentFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;
import ru.ilnyrdiplom.bestedu.facade.services.CommentServiceFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.requests.CommentRequest;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.ApiResponse;
import ru.ilnyrdiplom.bestedu.web.model.TokenPrincipal;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
@Secured({Role.TEACHER, Role.STUDENT})
public class CommentController {
    private final CommentServiceFacade commentService;

    @PostMapping("/disciplines/{disciplineId}/exercises/{exerciseId}/comments/")
    public ResponseEntity<ApiResponse<CommentFacade>> addExercise(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @RequestBody CommentRequest commentRequest,
            @PathVariable int disciplineId,
            @PathVariable int exerciseId
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        CommentFacade comment = commentService
                .createComment(tokenPrincipal.getAccountIdentity(), () -> disciplineId, () -> exerciseId, commentRequest);
        return ApiResponse.success(comment);
    }

    @Secured(Role.TEACHER)
    @DeleteMapping("/disciplines/{disciplineId}/exercises/{exerciseId}/comments/{commentId}/")
    public ResponseEntity<ApiResponse<CommentFacade>> deleteComment(@AuthenticationPrincipal TokenPrincipal tokenPrincipal,
                                                                    @PathVariable int disciplineId,
                                                                    @PathVariable int exerciseId,
                                                                    @PathVariable int commentId
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        CommentFacade comment = commentService.deleteComment(
                tokenPrincipal.getAccountIdentity(),
                () -> disciplineId,
                () -> exerciseId,
                () -> commentId
        );
        return ApiResponse.success(comment);
    }
}
