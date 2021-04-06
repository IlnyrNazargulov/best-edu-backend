package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.dal.model.ExerciseFile;
import ru.ilnyrdiplom.bestedu.dal.model.File;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.ExerciseFileRepository;
import ru.ilnyrdiplom.bestedu.dal.repositories.FileRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.*;
import ru.ilnyrdiplom.bestedu.facade.model.enums.ExerciseFileType;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.services.FileUploadServiceFacade;
import ru.ilnyrdiplom.bestedu.service.config.FileProperties;
import ru.ilnyrdiplom.bestedu.service.service.AccountService;
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
    private final RandomService randomService;
    private final AccountService accountService;
    private final FileRepository fileRepository;
    private final ExerciseFileRepository exerciseFileRepository;
    private final FileProperties fileProperties;


    @Override
    public void deleteExerciseFile(
            AccountIdentity accountIdentity,
            UUID fileUuid
    ) throws EntityNotFoundException {
        AccountTeacher accountTeacher = accountService.getAccountTeacher(accountIdentity);
        File file = fileRepository.findFileByUuidAndOwner(fileUuid, accountTeacher);
        if (file == null) {
            throw new EntityNotFoundException(fileUuid, File.class);
        }
        fileRepository.updateRemoved(file, true);
    }

    @Override
    @Transactional
    public ExerciseFile createExerciseContentFile(AccountTeacher teacher, Exercise exercise) throws ImpossibleCreateExerciseFileException {
        Instant now = Instant.now();
        UUID uuid = randomService.generateUUID();
        File file = new File(uuid, teacher, "content_" + exercise.getId(), "txt", now, 0);
        ExerciseFile exerciseFile = exerciseFileRepository.save(new ExerciseFile(file, exercise, ExerciseFileType.CONTENT));
        final java.io.File destination = Paths.get(
                fileProperties.getUploadsPath(),
                file.getUuid().toString() + "." + file.getExtension()
        ).toFile();
        try {
            destination.createNewFile();
        }
        catch (IOException e) {
            throw new ImpossibleCreateExerciseFileException();
        }
        return exerciseFile;
    }

    public void updateExerciseContentFile(ExerciseFile exerciseFile, InputStream inputStream)
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

    @Override
    public ExerciseFile uploadExerciseFile(
            InputStream fileInputStream,
            String fileName,
            AccountIdentity accountIdentity
    )
            throws FileUploadException, EntityNotFoundException, FileSizeExceededException {
        final String contentType = extractContentType(fileName);
        if (getFileSize(fileInputStream) > fileProperties.getMaxImageSize()) {
            throw new FileSizeExceededException();
        }
        File uploadedFile = createFile(accountIdentity, fileInputStream, fileName);
        ExerciseFile exerciseFile = exerciseFileRepository.save(new ExerciseFile(uploadedFile));

        try {
            saveFileToDisk(uploadedFile, fileInputStream);
        }
        catch (FileUploadException e) {
            fileRepository.updateRemoved(uploadedFile, true);
            throw e;
        }
        return exerciseFile;
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
