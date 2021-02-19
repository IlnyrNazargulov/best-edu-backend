package ru.ilnyrdiplom.bestedu.web.mixins;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.ilnyrdiplom.bestedu.facade.model.DisciplineFacade;
import ru.ilnyrdiplom.bestedu.facade.model.FileFacade;

@JsonSerialize(as = FileFacade.class)
public interface FileMixin {
}
