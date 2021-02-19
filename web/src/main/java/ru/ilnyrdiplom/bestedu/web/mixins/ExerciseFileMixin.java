package ru.ilnyrdiplom.bestedu.web.mixins;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseFileFacade;

@JsonSerialize(as = ExerciseFileFacade.class)
public interface ExerciseFileMixin {
}
