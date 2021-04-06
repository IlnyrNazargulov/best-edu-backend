package ru.ilnyrdiplom.bestedu.service.service;

import ru.ilnyrdiplom.bestedu.dal.model.File;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;

import java.util.UUID;

public interface FileService {
    File getFile(UUID uuid, AccountTeacher teacher) throws EntityNotFoundException;
}
