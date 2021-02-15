package ru.ilnyrdiplom.bestedu.web.contracts.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import ru.ilnyrdiplom.bestedu.facade.model.AccountFacade;

@Getter
@RequiredArgsConstructor
public class AccountWithTokenResponse {
    private final OAuth2AccessToken token;
    private final AccountFacade account;
}
