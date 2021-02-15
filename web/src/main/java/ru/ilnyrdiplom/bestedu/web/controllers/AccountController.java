package ru.ilnyrdiplom.bestedu.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilnyrdiplom.bestedu.facade.exceptions.AccountLoginException;
import ru.ilnyrdiplom.bestedu.facade.model.AccountTeacherFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;
import ru.ilnyrdiplom.bestedu.facade.services.AccountServiceFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.requests.RegisterRequest;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.AccountWithTokenResponse;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.ApiResponse;
import ru.ilnyrdiplom.bestedu.web.services.SecurityTokenService;

@RequestMapping(value = "/accounts/", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final SecurityTokenService securityTokenService;
    private final AccountServiceFacade accountService;

    @Secured(Role.ANONYMOUS)
    @PostMapping(value = "/teachers/register/")
    public ResponseEntity<ApiResponse<AccountWithTokenResponse>> registerTeacher(@Validated @RequestBody RegisterRequest registerRequest) throws AccountLoginException {
        AccountTeacherFacade account = accountService.createAccountTeacher(registerRequest);
        OAuth2AccessToken accessTokenByAccount = securityTokenService.createAccessTokenByAccount(account);
        return ApiResponse.success(new AccountWithTokenResponse(accessTokenByAccount, account));
    }
}
