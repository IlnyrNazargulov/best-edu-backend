package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.dal.model.File;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.ExerciseFileRepository;
import ru.ilnyrdiplom.bestedu.dal.repositories.FileRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.service.service.FileService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    public File getFile(UUID uuid, AccountTeacher teacher) throws EntityNotFoundException {
        File file = fileRepository.findFileByUuidAndOwner(uuid, teacher);
        if (file == null) {
            throw new EntityNotFoundException(uuid, File.class);
        }
        return file;
    }
}
