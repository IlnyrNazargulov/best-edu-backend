package ru.ilnyrdiplom.bestedu.service.service;

import ru.ilnyrdiplom.bestedu.dal.model.Discipline;
import ru.ilnyrdiplom.bestedu.dal.model.Exercise;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.model.identities.ExerciseIdentity;

public interface ExerciseService {
    Exercise getExerciseByDiscipline(Discipline discipline, ExerciseIdentity exerciseIdentity) throws EntityNotFoundException;
}
