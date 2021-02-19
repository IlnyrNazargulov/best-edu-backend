package ru.ilnyrdiplom.bestedu.web.mixins;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.ilnyrdiplom.bestedu.facade.model.DisciplineFacade;
import ru.ilnyrdiplom.bestedu.facade.model.NotificationFacade;

@JsonSerialize(as = NotificationFacade.class)
public interface NotificationMixin {
}
