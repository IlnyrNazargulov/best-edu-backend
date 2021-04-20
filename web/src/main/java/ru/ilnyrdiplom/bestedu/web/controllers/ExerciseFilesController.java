package ru.ilnyrdiplom.bestedu.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ilnyrdiplom.bestedu.facade.exceptions.*;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFileFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;
import ru.ilnyrdiplom.bestedu.facade.services.ExerciseFileServiceFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.ApiResponse;
import ru.ilnyrdiplom.bestedu.web.model.TokenPrincipal;

import java.util.List;
import java.util.UUID;

@Secured({Role.TEACHER})
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ExerciseFilesController {
    private final ExerciseFileServiceFacade exerciseFileService;

    @GetMapping("/disciplines/{disciplineId}/exercises/{exerciseId}/exercise-files/")
    public ResponseEntity<ApiResponse<List<? extends ExerciseFileFacade>>> getExerciseFiles(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable int disciplineId,
            @PathVariable int exerciseId
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        List<? extends ExerciseFileFacade> exerciseFiles = exerciseFileService.getExerciseFiles(
                tokenPrincipal.getAccountIdentity(),
                () -> disciplineId,
                () -> exerciseId
        );
        return ApiResponse.success(exerciseFiles);
    }

    @DeleteMapping("/disciplines/{disciplineId}/exercises/{exerciseId}/exercise-files/{fileId}/")
    public ResponseEntity<ApiResponse<ExerciseFileFacade>> deleteExerciseFile(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable int disciplineId,
            @PathVariable int exerciseId,
            @PathVariable UUID fileId
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        ExerciseFileFacade exerciseFile = exerciseFileService.deleteExerciseFile(
                tokenPrincipal.getAccountIdentity(),
                () -> disciplineId,
                () -> exerciseId,
                fileId
        );
        return ApiResponse.success(exerciseFile);
    }

    @Secured(Role.TEACHER)
    @PutMapping(value = "/disciplines/{disciplineId}/exercises/{exerciseId}/content/", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<ApiResponse<ExerciseFileFacade>> updateContentExerciseFile(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @RequestBody String content,
            @PathVariable int disciplineId,
            @PathVariable int exerciseId
    ) throws WrongAccountTypeException, ImpossibleAccessDisciplineException, EntityNotFoundException, ImpossibleUpdateExerciseFileException, FileUploadException {
        ExerciseFileFacade exerciseFileFacade = exerciseFileService
                .updateExerciseContent(tokenPrincipal.getAccountIdentity(), () -> disciplineId, () -> exerciseId, content);
        return ApiResponse.success(exerciseFileFacade);
    }
}
