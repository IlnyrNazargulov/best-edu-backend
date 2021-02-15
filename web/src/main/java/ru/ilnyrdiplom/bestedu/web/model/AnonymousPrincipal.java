package ru.ilnyrdiplom.bestedu.web.model;

import org.springframework.security.access.AccessDeniedException;
import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;

public class AnonymousPrincipal implements TokenPrincipal {
    @Override
    public AccountIdentity getAccountIdentity() {
        throw new AccessDeniedException("Anonymous user has not identity.");
    }
}
