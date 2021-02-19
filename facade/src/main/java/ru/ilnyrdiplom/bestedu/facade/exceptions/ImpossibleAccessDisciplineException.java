package ru.ilnyrdiplom.bestedu.facade.exceptions;

import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;

public class ImpossibleAccessDisciplineException extends BaseException {
    public ImpossibleAccessDisciplineException(DisciplineIdentity disciplineIdentity, AccountIdentity accountIdentity) {
        super("Access to discipline " + disciplineIdentity.getId() + " is denied to account " + accountIdentity.getId() + ".");
    }
}
