package ru.ilnyrdiplom.bestedu.web.mixins;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.ilnyrdiplom.bestedu.facade.model.AccountFacade;

@JsonSerialize(as = AccountFacade.class)
public interface AccountMixin {
}
