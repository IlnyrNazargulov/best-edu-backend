package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.users.AccountTeacher;
import ru.ilnyrdiplom.bestedu.dal.repositories.DisciplineRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.DisciplineAlreadyExistsException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;
import ru.ilnyrdiplom.bestedu.facade.services.DisciplineServiceFacade;
import ru.ilnyrdiplom.bestedu.service.service.AccountService;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DisciplineServiceImpl implements DisciplineServiceFacade {
    private final AccountService accountService;
    private final DisciplineRepository disciplineRepository;

    @Override
    public Discipline createDiscipline(AccountIdentity accountIdentity, String name)
            throws EntityNotFoundException, DisciplineAlreadyExistsException {
        Instant now = Instant.now();
        AccountTeacher accountTeacher = accountService.getAccountTeacher(accountIdentity);
        Discipline existDiscipline = disciplineRepository
                .findDisciplineByNameAndTeacher(name, accountTeacher);
        if (existDiscipline != null) {
            throw new DisciplineAlreadyExistsException(name, accountIdentity);
        }
        Discipline discipline = new Discipline(accountTeacher, now, name);
        return disciplineRepository.save(discipline);
    }

    @Override
    public Discipline getDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity)
            throws EntityNotFoundException {
        AccountTeacher accountTeacher = accountService.getAccountTeacher(accountIdentity);
        Discipline discipline = disciplineRepository
                .findDisciplineByIdAndTeacher(disciplineIdentity.getId(), accountTeacher);
        if (discipline == null) {
            throw new EntityNotFoundException(disciplineIdentity, Discipline.class);
        }
        return discipline;
    }

    @Override
    @Transactional
    public Discipline updateDiscipline(AccountIdentity accountIdentity, DisciplineIdentity disciplineIdentity, String newName)
            throws EntityNotFoundException {
        Discipline discipline = getDiscipline(accountIdentity, disciplineIdentity);
        discipline.setName(newName);
        return discipline;
    }

}
