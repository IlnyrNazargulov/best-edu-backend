package ru.ilnyrdiplom.bestedu.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ilnyrdiplom.bestedu.facade.exceptions.*;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFileFacade;
import ru.ilnyrdiplom.bestedu.facade.model.ImageFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;
import ru.ilnyrdiplom.bestedu.facade.services.FileUploadServiceFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.ApiResponse;
import ru.ilnyrdiplom.bestedu.web.model.TokenPrincipal;
import ru.ilnyrdiplom.bestedu.web.utils.ValidExerciseFileTypeForCreate;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
@Secured(Role.TEACHER)
@Validated
public class FileController {

    private final FileUploadServiceFacade fileUploadService;

    @PostMapping(value = "/disciplines/{disciplineId}/exercises/{exerciseId}/exercise-files/{exerciseFileType}/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ExerciseFileFacade>> createExerciseFile(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable int disciplineId,
            @PathVariable int exerciseId,
            @Valid @ValidExerciseFileTypeForCreate(anyOf = {ExerciseFileType.CODE}) @PathVariable ExerciseFileType exerciseFileType,
            final @RequestParam("file") MultipartFile file
    ) throws ImpossibleAccessDisciplineException, FileSizeExceededException, FileUploadException, WrongAccountTypeException {
        ExerciseFileFacade exerciseFile;
        try (InputStream inputStream = file.getInputStream()) {
            exerciseFile = fileUploadService.uploadExerciseFile(
                    inputStream,
                    file.getOriginalFilename(),
                    tokenPrincipal.getAccountIdentity(),
                    () -> disciplineId,
                    () -> exerciseId,
                    exerciseFileType
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

    @PostMapping(value = "/images/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ImageFacade>> uploadExerciseFile(
            final @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal
    ) throws Exception {
        ImageFacade image;
        try (InputStream inputStream = file.getInputStream()) {
            image = fileUploadService.uploadImage(
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
        return ApiResponse.success(image);
    }
}
