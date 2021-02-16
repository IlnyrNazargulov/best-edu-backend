package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.dal.model.RequestCode;
import ru.ilnyrdiplom.bestedu.dal.repositories.RequestCodeRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongRequestCodeException;
import ru.ilnyrdiplom.bestedu.facade.services.RequestCodeServiceFacade;
import ru.ilnyrdiplom.bestedu.service.service.RandomService;
import ru.ilnyrdiplom.bestedu.service.service.RequestCodeService;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class RequestCodeServiceImpl implements RequestCodeService, RequestCodeServiceFacade {
    private final RequestCodeRepository requestCodeRepository;
    private final RandomService randomService;

    @Override
    public RequestCode create(String email, Instant now) {
        String generateCode = randomService.generateCode(6);
        RequestCode requestCode = new RequestCode(email, generateCode, now);
        return requestCodeRepository.save(requestCode);
    }

    @Override
    public void verify(String email, String code) throws WrongRequestCodeException, EntityNotFoundException {
        RequestCode requestCode = requestCodeRepository.findTopRequestCodeByEmailOrderByCreatedAt(email);
        if (requestCode == null) {
            throw new EntityNotFoundException(email, RequestCode.class);
        }
        if (requestCode.getCode().equals(code)) {
            return;
        }
        else {
            throw new WrongRequestCodeException(email, code);
        }
    }
}
