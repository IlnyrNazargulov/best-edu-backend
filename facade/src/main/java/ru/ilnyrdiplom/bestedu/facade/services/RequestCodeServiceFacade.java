package ru.ilnyrdiplom.bestedu.facade.services;

import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongRequestCodeException;

public interface RequestCodeServiceFacade {
    void verify(String email, String code) throws WrongRequestCodeException, EntityNotFoundException;
}
