package ru.ilnyrdiplom.bestedu.service.service;

import ru.ilnyrdiplom.bestedu.dal.model.RequestCode;
import ru.ilnyrdiplom.bestedu.facade.model.RequestCodeStatusFacade;

import java.time.Instant;

public interface RequestCodeService {
    RequestCode create(String email, Instant now);

    RequestCode save(RequestCode requestCode);

    RequestCodeStatusFacade tryRequestNewCodeOrGetExisting(RequestCode requestCode);
}
