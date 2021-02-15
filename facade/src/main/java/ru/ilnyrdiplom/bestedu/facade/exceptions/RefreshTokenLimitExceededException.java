package ru.ilnyrdiplom.bestedu.facade.exceptions;

import lombok.NonNull;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;

public class RefreshTokenLimitExceededException extends BaseException {
    public RefreshTokenLimitExceededException(@NonNull AccountIdentity accountIdentity, int max, int actual) {
        super("Refresh token limit exceeded: limit " + max + " pre hour, but actual " + actual + " for account " + accountIdentity.getId());
    }
}
