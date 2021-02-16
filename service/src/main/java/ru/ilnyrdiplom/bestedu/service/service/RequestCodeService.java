package ru.ilnyrdiplom.bestedu.service.service;

import ru.ilnyrdiplom.bestedu.dal.model.RequestCode;

import java.time.Instant;

public interface RequestCodeService {
    RequestCode create(String email, Instant now);
}
