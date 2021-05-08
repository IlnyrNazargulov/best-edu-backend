package ru.ilnyrdiplom.bestedu.web.mixins;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.ilnyrdiplom.bestedu.facade.model.AccessDisciplineFacade;

@JsonSerialize(as = AccessDisciplineFacade.class)
public interface AccessDisciplineMixin {
}
