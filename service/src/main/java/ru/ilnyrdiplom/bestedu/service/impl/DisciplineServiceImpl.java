package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ilnyrdiplom.bestedu.dal.model.AccessDiscipline;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountStudent;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.AccessDisciplineRepository;
import ru.ilnyrdiplom.bestedu.dal.repositories.DisciplineRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.DisciplineAlreadyExistsException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleAccessDisciplineException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccountTypeException;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.services.DisciplineServiceFacade;
import ru.ilnyrdiplom.bestedu.service.service.AccountService;
import ru.ilnyrdiplom.bestedu.service.service.DisciplineService;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DisciplineServiceImpl implements DisciplineServiceFacade, DisciplineService {
    private final AccountService accountService;
    private final DisciplineRepository disciplineRepository;
    private final AccessDisciplineRepository accessDisciplineRepository;

    @Override
    public Discipline createDiscipline(AccountIdentity accountIdentity, String name, boolean isPublic)
            throws EntityNotFoundException, DisciplineAlreadyExistsException {
        Instant now = Instant.now();
        AccountTeacher accountTeacher = accountService.getAccountTeacher(accountIdentity);
        Discipline existDiscipline = disciplineRepository
                .findDisciplineByNameAndTeacherAndIsRemovedFalse(name, accountTeacher);
        if (existDiscipline != null) {
            throw new DisciplineAlreadyExistsException(name, accountIdentity);
        }
        Discipline discipline = new Discipline(accountTeacher, now, name, isPublic);
        return disciplineRepository.save(discipline);
    }

    @Override
    public List<Discipline> getDisciplines(
            AccountIdentity accountIdentity,
            AccountIdentity teacherIdentity,
            String teacherFullName,
            String nameDiscipline
    ) throws EntityNotFoundException {
        Account account = accountService.getAccount(accountIdentity);
        AccountTeacher teacher = null;
        if (teacherIdentity.getId() != null) {
            teacher = accountService.getAccountTeacher(teacherIdentity);
        }
        return disciplineRepository.findDisciplines(account, teacher, teacherFullName, nameDiscipline);
    }

    @Override
    public Discipline getDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException, ImpossibleAccessDisciplineException, WrongAccountTypeException {
        Account account = accountService.getAccount(accountIdentity);
        if (account instanceof AccountTeacher) {
            return getDisciplineByTeacher((AccountTeacher) account, disciplineIdentity);
        }
        if (account instanceof AccountStudent) {
            return getDisciplineByStudent((AccountStudent) account, disciplineIdentity);
        }
        throw new WrongAccountTypeException();
    }

    @Override
    @Transactional
    public Discipline updateDiscipline(
            AccountIdentity accountIdentity,
            DisciplineIdentity disciplineIdentity,
            String newName,
            boolean isPublic
    )
            throws EntityNotFoundException {
        AccountTeacher accountTeacher = accountService.getAccountTeacher(accountIdentity);
        Discipline discipline = getDisciplineByTeacher(accountTeacher, disciplineIdentity);
        discipline.setName(newName);
        discipline.setRemoved(isPublic);
        return discipline;
    }

    @Override
    @Transactional
    public Discipline deleteDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException {
        AccountTeacher accountTeacher = accountService.getAccountTeacher(accountIdentity);
        Discipline discipline = getDisciplineByTeacher(accountTeacher, disciplineIdentity);
        discipline.setRemoved(true);
        return discipline;
    }

    private Discipline getDisciplineByTeacher(AccountTeacher accountTeacher, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException {
        Discipline discipline = disciplineRepository
                .findDisciplineByIdAndTeacherAndIsRemovedFalse(disciplineIdentity.getId(), accountTeacher);
        if (discipline == null) {
            throw new EntityNotFoundException(disciplineIdentity, Discipline.class);
        }
        return discipline;
    }

    private Discipline getDisciplineByStudent(
            AccountStudent accountStudent,
            DisciplineIdentity disciplineIdentity
    )
            throws EntityNotFoundException, ImpossibleAccessDisciplineException {
        Discipline discipline = disciplineRepository.findById(disciplineIdentity.getId())
                .orElseThrow(() -> new EntityNotFoundException(disciplineIdentity, Discipline.class));
        if (discipline.isPublic()) {
            return discipline;
        }
        AccessDiscipline accessDiscipline = accessDisciplineRepository
                .findAccessDisciplineByDisciplineAndStudent(discipline, accountStudent);
        if (accessDiscipline == null) {
            throw new ImpossibleAccessDisciplineException(disciplineIdentity, accountStudent::getId);
        }
        return discipline;
    }
}
