package ru.ilnyrdiplom.bestedu.web.model;

import ru.ilnyrdiplom.bestedu.facade.model.identities.AccountIdentity;

public interface TokenPrincipal {
    AccountIdentity getAccountIdentity();
}
