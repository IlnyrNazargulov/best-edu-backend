package ru.ilnyrdiplom.bestedu.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ilnyrdiplom.bestedu.facade.exceptions.*;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFacade;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseWithoutContentFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;
import ru.ilnyrdiplom.bestedu.facade.services.ExerciseServiceFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.requests.ExerciseRequest;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.ApiResponse;
import ru.ilnyrdiplom.bestedu.web.model.TokenPrincipal;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseServiceFacade exerciseService;

    @Secured(Role.TEACHER)
    @PostMapping("/disciplines/{disciplineId}/exercises/")
    public ResponseEntity<ApiResponse<ExerciseFacade>> createExercise(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @RequestBody ExerciseRequest exerciseRequest,
            @PathVariable int disciplineId
    ) throws ExerciseAlreadyExistsException, WrongAccountTypeException, ImpossibleAccessDisciplineException, EntityNotFoundException,
            ImpossibleCreateExerciseFileException, FileUploadException {
        ExerciseFacade exercise = exerciseService
                .createExercise(tokenPrincipal.getAccountIdentity(), () -> disciplineId, exerciseRequest);
        return ApiResponse.success(exercise);
    }

    @Secured(Role.TEACHER)
    @PutMapping("/disciplines/{disciplineId}/exercises/{exerciseId}/")
    public ResponseEntity<ApiResponse<ExerciseFacade>> updateExercise(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @RequestBody ExerciseRequest exerciseRequest,
            @PathVariable int disciplineId,
            @PathVariable int exerciseId
    ) throws WrongAccountTypeException, ImpossibleAccessDisciplineException, EntityNotFoundException, ExerciseAlreadyExistsException {
        ExerciseFacade exercise = exerciseService
                .updateExercise(tokenPrincipal.getAccountIdentity(), () -> disciplineId, () -> exerciseId, exerciseRequest);
        return ApiResponse.success(exercise);
    }

    @Secured({Role.TEACHER, Role.STUDENT})
    @GetMapping("/disciplines/{disciplineId}/exercises/{exerciseId}/")
    public ResponseEntity<ApiResponse<ExerciseFacade>> getExercise(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable int disciplineId,
            @PathVariable int exerciseId
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        ExerciseFacade exercise = exerciseService
                .getExercise(tokenPrincipal.getAccountIdentity(), () -> disciplineId, () -> exerciseId);
        return ApiResponse.success(exercise);
    }

    @Secured({Role.TEACHER, Role.STUDENT})
    @GetMapping("/disciplines/{disciplineId}/exercises/")
    public ResponseEntity<ApiResponse<List<? extends ExerciseWithoutContentFacade>>> getExercises(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable int disciplineId
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        List<? extends ExerciseWithoutContentFacade> exercises = exerciseService
                .getExercises(tokenPrincipal.getAccountIdentity(), () -> disciplineId);
        return ApiResponse.success(exercises);
    }

    @Secured({Role.TEACHER})
    @DeleteMapping("/disciplines/{disciplineId}/exercises/{exerciseId}/")
    public ResponseEntity<ApiResponse<ExerciseFacade>> deleteExercise(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable int disciplineId,
            @PathVariable int exerciseId
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        ExerciseFacade exercise = exerciseService
                .deleteExercise(tokenPrincipal.getAccountIdentity(), () -> disciplineId, () -> exerciseId);
        return ApiResponse.success(exercise);
    }
}
