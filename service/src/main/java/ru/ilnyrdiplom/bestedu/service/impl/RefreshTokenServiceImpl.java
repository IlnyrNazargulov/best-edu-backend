package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.dal.model.RefreshToken;
import ru.ilnyrdiplom.bestedu.dal.model.users.Account;
import ru.ilnyrdiplom.bestedu.dal.repositories.RefreshTokenRepository;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.RefreshTokenLimitExceededException;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;
import ru.ilnyrdiplom.bestedu.facade.services.RefreshTokenServiceFacade;
import ru.ilnyrdiplom.bestedu.service.RefreshTokenProperties;
import ru.ilnyrdiplom.bestedu.service.service.AccountService;
import ru.ilnyrdiplom.bestedu.service.service.RandomService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RefreshTokenServiceImpl implements RefreshTokenServiceFacade {

    private final AccountService accountService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenProperties refreshTokenProperties;
    private final RandomService randomService;

    @Override
    public UUID claimToken(@NonNull AccountIdentity accountIdentity, Instant now, Instant expiredAt) throws EntityNotFoundException, RefreshTokenLimitExceededException {
        Account account = accountService.getAccount(accountIdentity);
        Instant lastHour = now.minus(1L, ChronoUnit.HOURS);
        int count = refreshTokenRepository.findCountForTime(account, lastHour);
        if (count > refreshTokenProperties.getMaxTokensPerHour()) {
            throw new RefreshTokenLimitExceededException(accountIdentity, refreshTokenProperties.getMaxTokensPerHour(), count);
        }
        RefreshToken refreshToken = new RefreshToken(
                randomService.generateUUID(),
                account,
                now,
                expiredAt
        );
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    @Override
    public Account releaseToken(UUID token, Instant now) {
        RefreshToken refreshToken = refreshTokenRepository.findById(token).orElse(null);
        if (refreshToken == null) {
            return null;
        }

        int affected = refreshTokenRepository.remove(refreshToken);
        if (affected != 1) {
            return null;
        }

        if (refreshToken.getExpiredAt().isBefore(now)) {
            return null;
        }

        return refreshToken.getAccount();
    }

    @Override
    public void releaseOne(AccountIdentity accountIdentity, UUID token) throws EntityNotFoundException {
        Account account = accountService.getAccount(accountIdentity);
        RefreshToken refreshToken = refreshTokenRepository.findByAccountAndToken(account, token);
        if (refreshToken == null) {
            throw new EntityNotFoundException(token, RefreshToken.class);
        }
        refreshTokenRepository.remove(refreshToken);
    }

    @Override
    public void releaseAll(AccountIdentity targetIdentity, AccountIdentity currentIdentity) throws EntityNotFoundException {
        Account target = accountService.getAccount(targetIdentity);
        refreshTokenRepository.removeAllByAccount(target);
    }
}
