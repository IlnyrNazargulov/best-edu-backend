package ru.ilnyrdiplom.bestedu.facade.exceptions;

import ru.ilnyrdiplom.bestedu.facade.model.identities.DisciplineIdentity;

public class ExerciseAlreadyExistsException extends BaseException {
    public ExerciseAlreadyExistsException(String name, DisciplineIdentity disciplineIdentity) {
        super("Exercise with name " + name + " at discipline with id " + disciplineIdentity.getId() + " already exists.");
    }
}
