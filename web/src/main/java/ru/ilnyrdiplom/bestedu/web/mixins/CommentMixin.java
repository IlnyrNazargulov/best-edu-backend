package ru.ilnyrdiplom.bestedu.web.mixins;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.ilnyrdiplom.bestedu.facade.model.CommentFacade;

@JsonSerialize(as = CommentFacade.class)
public interface CommentMixin {
}
