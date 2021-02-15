package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.RefreshTokenLimitExceededException;
import ru.ilnyrdiplom.bestedu.facade.model.AccountFacade;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.services.RefreshTokenServiceFacade;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RefreshTokenServiceImpl implements RefreshTokenServiceFacade {

    @Override
    public UUID claimToken(AccountIdentity accountIdentity, Instant now, Instant expiredAt) throws EntityNotFoundException, RefreshTokenLimitExceededException {
        return null;
    }

    @Override
    public AccountFacade releaseToken(UUID token, Instant now) {
        return null;
    }

    @Override
    public void releaseOne(AccountIdentity accountIdentity, UUID token) throws EntityNotFoundException {

    }

    @Override
    public void releaseAll(AccountIdentity targetIdentity, AccountIdentity currentIdentity) throws EntityNotFoundException {

    }
}
