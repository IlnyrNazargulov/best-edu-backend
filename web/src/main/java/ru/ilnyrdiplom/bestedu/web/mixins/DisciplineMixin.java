package ru.ilnyrdiplom.bestedu.web.mixins;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.ilnyrdiplom.bestedu.facade.model.DisciplineFacade;

@JsonSerialize(as = DisciplineFacade.class)
public interface DisciplineMixin {
}
