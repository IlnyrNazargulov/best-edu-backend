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
import ru.ilnyrdiplom.bestedu.facade.model.FileFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;
import ru.ilnyrdiplom.bestedu.facade.services.FileUploadServiceFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.ApiResponse;
import ru.ilnyrdiplom.bestedu.web.model.TokenPrincipal;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
@Secured(Role.TEACHER)
public class FileController {

    private final FileUploadServiceFacade fileService;

    @PostMapping("/files/")
    public ResponseEntity<ApiResponse<ExerciseFileFacade>> uploadExerciseFile(
            final @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal
    ) throws Exception {
        ExerciseFileFacade fileFacade;
        try (InputStream inputStream = file.getInputStream()) {
            fileFacade = fileService.uploadExerciseFile(
                    inputStream,
                    file.getOriginalFilename(),
                    tokenPrincipal.getAccountIdentity()
            );
        }
        catch (IOException e) {
            throw new FileUploadException("Error on getting input stream.", e);
        }
        catch (EntityNotFoundException ex) {
            throw new FileUploadException("Related entity is not found.", ex);
        }
        return ApiResponse.success(fileFacade);
    }

    @DeleteMapping("/files/{fileId}/")
    public ResponseEntity<ApiResponse<ExerciseFacade>> deleteExerciseFile(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable UUID fileId
    ) throws WrongAccountTypeException, EntityNotFoundException, ImpossibleAccessDisciplineException {
        fileService.deleteExerciseFile(tokenPrincipal.getAccountIdentity(), fileId);
        return ApiResponse.success();
    }
}
