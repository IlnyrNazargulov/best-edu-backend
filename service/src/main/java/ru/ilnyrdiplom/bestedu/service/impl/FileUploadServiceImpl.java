package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.ExerciseFile;
import ru.ilnyrdiplom.bestedu.dal.model.File;
import ru.ilnyrdiplom.bestedu.dal.model.Image;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.ExerciseFileRepository;
import ru.ilnyrdiplom.bestedu.dal.repositories.FileRepository;
import ru.ilnyrdiplom.bestedu.dal.repositories.ImageRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.*;
import ru.ilnyrdiplom.bestedu.facade.model.ImageFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.ExerciseIdentity;
import ru.ilnyrdiplom.bestedu.facade.services.FileUploadServiceFacade;
import ru.ilnyrdiplom.bestedu.service.config.FileProperties;
import ru.ilnyrdiplom.bestedu.service.service.AccountService;
import ru.ilnyrdiplom.bestedu.service.service.ExerciseService;
import ru.ilnyrdiplom.bestedu.service.service.FileUploadService;
import ru.ilnyrdiplom.bestedu.service.service.RandomService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileUploadServiceImpl implements FileUploadServiceFacade, FileUploadService {
    private final FileProperties fileProperties;

    private final RandomService randomService;
    private final AccountService accountService;
    private final ExerciseService exerciseService;

    private final FileRepository fileRepository;
    private final ExerciseFileRepository exerciseFileRepository;
    private final ImageRepository imageRepository;

    @Override
    public ExerciseFile uploadExerciseFile(
            InputStream exerciseFileInputStream,
            String fileName,
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            ExerciseIdentity exerciseIdentity,
            ExerciseFileType exerciseFileType
    ) throws FileSizeExceededException, FileUploadException, ImpossibleAccessDisciplineException, EntityNotFoundException, WrongAccountTypeException {
        final String contentType = extractContentType(fileName);
        if (getFileSize(exerciseFileInputStream) > fileProperties.getMaxImageSize()) {
            throw new FileSizeExceededException();
        }
        Exercise exercise = exerciseService
                .getAvailableExercise(accountIdentity, disciplineIdentity, exerciseIdentity);
        File uploadedFile = createFile(accountIdentity, exerciseFileInputStream, fileName);
        ExerciseFile exerciseFile = exerciseFileRepository.save(new ExerciseFile(uploadedFile, exercise, exerciseFileType));
        try {
            saveFileToDisk(uploadedFile, exerciseFileInputStream);
        }
        catch (FileUploadException e) {
            fileRepository.updateRemoved(uploadedFile, true);
            throw e;
        }
        return exerciseFile;
    }

    @Override
    public ImageFacade uploadImage(
            InputStream imageInputStream,
            String fileName,
            AccountIdentity accountIdentity
    )
            throws FileUploadException, EntityNotFoundException, FileSizeExceededException {
        final String contentType = extractContentType(fileName);
        if (getFileSize(imageInputStream) > fileProperties.getMaxImageSize()) {
            throw new FileSizeExceededException();
        }
        File uploadedFile = createFile(accountIdentity, imageInputStream, fileName);
        Image image = imageRepository.save(new Image(uploadedFile, contentType));

        try {
            saveFileToDisk(uploadedFile, imageInputStream);
        }
        catch (FileUploadException e) {
            fileRepository.updateRemoved(uploadedFile, true);
            throw e;
        }
        return image;
    }

    @Override
    public ExerciseFile createEmptyExerciseContentFile(
            InputStream inputStream,
            AccountTeacher teacher,
            Exercise exercise
    ) throws FileUploadException {
        Instant now = Instant.now();
        UUID uuid = randomService.generateUUID();

        File uploadedFile = new File(uuid, teacher, "content_" + exercise.getId(), "md", now, 0);
        ExerciseFile exerciseFile = exerciseFileRepository.save(new ExerciseFile(uploadedFile, exercise, ExerciseFileType.CONTENT));
        try {
            saveFileToDisk(uploadedFile, inputStream);
        }
        catch (FileUploadException e) {
            fileRepository.updateRemoved(uploadedFile, true);
            throw e;
        }
        return exerciseFile;
    }

    @Override
    public void updateExerciseContentFile(
            ExerciseFile exerciseFile,
            InputStream inputStream
    )
            throws ImpossibleUpdateExerciseFileException {
        final java.io.File destination = Paths.get(
                fileProperties.getUploadsPath(),
                exerciseFile.getFile().getUuid().toString() + "." + exerciseFile.getFile().getExtension()
        ).toFile();

        try {
            FileUtils.copyInputStreamToFile(inputStream, destination);
        }
        catch (IOException e) {
            throw new ImpossibleUpdateExerciseFileException();
        }
    }

    private void saveFileToDisk(File dbFile, final InputStream inputStream) throws FileUploadException {
        final java.io.File destination = Paths.get(
                fileProperties.getUploadsPath(),
                dbFile.getUuid().toString() + "." + dbFile.getExtension()
        ).toFile();

        try {
            FileUtils.copyInputStreamToFile(inputStream, destination);
        }
        catch (IOException e) {
            throw new FileUploadException("Error on saving common file to the disk.", e);
        }
    }

    private File createFile(AccountIdentity accountIdentity,
                            InputStream fileInputStream,
                            String fileName
    )
            throws FileUploadException, EntityNotFoundException {
        Instant now = Instant.now();
        final long size = getFileSize(fileInputStream);
        Account owner = accountService.getAccount(accountIdentity);
        String extension = getExtension(fileName);
        UUID uuid = randomService.generateUUID();
        return new File(uuid, owner, fileName, extension, now, size);
    }

    private String getExtension(String fileName) {
        String[] nameParts = fileName.split("\\.");
        return nameParts.length > 1 ? nameParts[nameParts.length - 1] : "";
    }

    private long getFileSize(InputStream fileInputStream) throws FileUploadException {
        try {
            return fileInputStream.available();
        }
        catch (final IOException e) {
            throw new FileUploadException("Error on detecting file size.", e);
        }
    }

    private String extractContentType(String fileName) {
        Tika tika = new Tika();
        return tika.detect(fileName);
    }
}
