package ru.ilnyrdiplom.bestedu.web.mixins;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseWithoutContentFacade;

@JsonSerialize(as = ExerciseWithoutContentFacade.class)
public interface ExerciseWithoutContentMixin {
}
