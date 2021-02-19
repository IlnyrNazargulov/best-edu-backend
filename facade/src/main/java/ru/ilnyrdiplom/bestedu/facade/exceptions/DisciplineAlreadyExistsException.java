package ru.ilnyrdiplom.bestedu.facade.exceptions;

import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;

public class DisciplineAlreadyExistsException extends BaseException {
    public DisciplineAlreadyExistsException(String name, AccountIdentity accountIdentity) {
        super("Discipline with name " + name + " at teacher with id " + accountIdentity.getId() + " already exists.");
    }
}
