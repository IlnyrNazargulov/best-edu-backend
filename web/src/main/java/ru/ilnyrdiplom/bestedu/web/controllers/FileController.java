package ru.ilnyrdiplom.bestedu.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.FileUploadException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleAccessDisciplineException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccountTypeException;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFacade;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFileFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;
import ru.ilnyrdiplom.bestedu.facade.services.FileUploadServiceFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.ApiResponse;
import ru.ilnyrdiplom.bestedu.web.model.TokenPrincipal;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
@Secured(Role.TEACHER)
public class FileController {

    private final FileUploadServiceFacade fileService;

    @PostMapping("/exercises/{exerciseId}/files/")
    public ResponseEntity<ApiResponse<ExerciseFileFacade>> uploadExerciseFile(
            final @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable int exerciseId
    ) throws Exception {
        ExerciseFileFacade exerciseFile;
        try (InputStream inputStream = file.getInputStream()) {
            exerciseFile = fileService.uploadExerciseFile(
                    inputStream,
                    file.getOriginalFilename(),
                    tokenPrincipal.getAccountIdentity(),
                    () -> exerciseId
            );
        }
        catch (IOException e) {
            throw new FileUploadException("Error on getting input stream.", e);
        }
        catch (EntityNotFoundException ex) {
            throw new FileUploadException("Related entity is not found.", ex);
        }
        return ApiResponse.success(exerciseFile);
    }

    @DeleteMapping("/exercises/{exerciseId}/files/{fileId}/")
    public ResponseEntity<ApiResponse<ExerciseFacade>> deleteExerciseFile(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable int exerciseId,
            @PathVariable UUID fileId
    ) throws WrongAccountTypeException, EntityNotFoundException, ImpossibleAccessDisciplineException {
        fileService.deleteExerciseFile(tokenPrincipal.getAccountIdentity(), () -> exerciseId, fileId);
        return ApiResponse.success();
    }

    @Secured({Role.TEACHER, Role.STUDENT})
    @GetMapping("/exercise/{exerciseId}/files/")
    public ResponseEntity<ApiResponse<List<? extends ExerciseFileFacade>>> getExerciseFiles(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable int exerciseId
    ) throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        List<? extends ExerciseFileFacade> exerciseFiles = fileService
                .getExerciseFiles(tokenPrincipal.getAccountIdentity(), () -> exerciseId);
        return ApiResponse.success(exerciseFiles);
    }

}
