package ru.ilnyrdiplom.bestedu.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ilnyrdiplom.bestedu.facade.exceptions.AccountLoginException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.model.AccountTeacherFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;
import ru.ilnyrdiplom.bestedu.facade.services.AccountServiceFacade;
import ru.ilnyrdiplom.bestedu.facade.services.RefreshTokenServiceFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.requests.RefreshTokenRequest;
import ru.ilnyrdiplom.bestedu.web.contracts.requests.RegisterRequest;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.AccountWithTokenResponse;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.ApiResponse;
import ru.ilnyrdiplom.bestedu.web.exceptions.RefreshTokenExpiredException;
import ru.ilnyrdiplom.bestedu.web.model.TokenPrincipal;
import ru.ilnyrdiplom.bestedu.web.services.SecurityTokenService;

@RequestMapping(value = "/accounts/", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final SecurityTokenService securityTokenService;
    private final AccountServiceFacade accountService;
    private final RefreshTokenServiceFacade refreshTokenService;

    @Secured(Role.EMAIL_VERIFIED)
    @PostMapping(value = "/teachers/register/")
    public ResponseEntity<ApiResponse<AccountWithTokenResponse>> registerTeacher(@Validated @RequestBody RegisterRequest registerRequest)
            throws AccountLoginException {
        AccountTeacherFacade account = accountService.createAccountTeacher(registerRequest);
        OAuth2AccessToken accessTokenByAccount = securityTokenService.createAccessTokenByAccount(account);
        return ApiResponse.success(new AccountWithTokenResponse(accessTokenByAccount, account));
    }

    @PostMapping("/current/refresh/")
    public ResponseEntity<ApiResponse<OAuth2AccessToken>> refreshToken(@Validated @RequestBody RefreshTokenRequest refreshTokenRequest)
            throws RefreshTokenExpiredException {
        OAuth2AccessToken accessToken = securityTokenService.refreshAccessToken(refreshTokenRequest.getToken());
        return ApiResponse.success(accessToken);
    }

    @PostMapping("/current/logout/")
    public ResponseEntity<?> logout(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @RequestBody RefreshTokenRequest refreshTokenRequest,
            @RequestParam boolean allDevices) throws EntityNotFoundException {
        if (!allDevices) {
            refreshTokenService.releaseOne(tokenPrincipal.getAccountIdentity(), refreshTokenRequest.getToken());
            return ApiResponse.success();
        }
        refreshTokenService.releaseAll(tokenPrincipal.getAccountIdentity(), tokenPrincipal.getAccountIdentity());
        return ApiResponse.success();
    }
}
