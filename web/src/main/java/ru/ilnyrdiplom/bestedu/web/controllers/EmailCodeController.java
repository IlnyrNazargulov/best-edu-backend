package ru.ilnyrdiplom.bestedu.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilnyrdiplom.bestedu.facade.exceptions.AccountLoginException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongRequestCodeException;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;
import ru.ilnyrdiplom.bestedu.facade.services.AccountServiceFacade;
import ru.ilnyrdiplom.bestedu.facade.services.RequestCodeServiceFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.requests.EmailCodeRequest;
import ru.ilnyrdiplom.bestedu.web.contracts.requests.VerifyEmailRequest;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.ApiResponse;
import ru.ilnyrdiplom.bestedu.web.services.SecurityTokenService;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EmailCodeController {

    private final RequestCodeServiceFacade requestCodeService;
    private final SecurityTokenService securityTokenService;
    private final AccountServiceFacade accountService;

    @PostMapping(value = "/accounts/request-code/")
    public ResponseEntity<?> registerRequestCode(
            @Validated @RequestBody VerifyEmailRequest verifyEmailRequest
    ) throws AccountLoginException {
        accountService.registerRequestCode(verifyEmailRequest.getEmail());
        return ApiResponse.success();
    }

    @PostMapping(value = "/accounts/verify-code/")
    public ResponseEntity<ApiResponse<OAuth2AccessToken>> verifyCode(
            @Validated @RequestBody EmailCodeRequest emailCodeRequest
    ) throws WrongRequestCodeException, EntityNotFoundException {
        requestCodeService.verify(emailCodeRequest.getEmail(), emailCodeRequest.getCode());
        OAuth2AccessToken accessTokenByPhone = securityTokenService.createAccessTokenByEmail(emailCodeRequest.getEmail(), Role.ROLE_EMAIL_VERIFIED);
        return ApiResponse.success(accessTokenByPhone);
    }
}
