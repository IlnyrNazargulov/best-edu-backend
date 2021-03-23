package ru.ilnyrdiplom.bestedu.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ilnyrdiplom.bestedu.facade.exceptions.AccountLoginException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongCredentialsException;
import ru.ilnyrdiplom.bestedu.facade.model.AccountFacade;
import ru.ilnyrdiplom.bestedu.facade.model.AccountStudentFacade;
import ru.ilnyrdiplom.bestedu.facade.model.AccountTeacherFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;
import ru.ilnyrdiplom.bestedu.facade.services.AccountServiceFacade;
import ru.ilnyrdiplom.bestedu.facade.services.RefreshTokenServiceFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.requests.*;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.AccountWithTokenResponse;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.ApiResponse;
import ru.ilnyrdiplom.bestedu.web.exceptions.RefreshTokenExpiredException;
import ru.ilnyrdiplom.bestedu.web.model.TokenPrincipal;
import ru.ilnyrdiplom.bestedu.web.services.SecurityTokenService;

import java.util.List;

@RequestMapping(value = "/accounts/", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final SecurityTokenService securityTokenService;
    private final AccountServiceFacade accountService;
    private final RefreshTokenServiceFacade refreshTokenService;

    @Secured(Role.ANONYMOUS)
    @PostMapping(value = "/login/")
    public ResponseEntity<ApiResponse<AccountWithTokenResponse>> login(
            @Validated @RequestBody LoginRequest loginRequest
    )
            throws WrongCredentialsException {
        AccountFacade account = accountService.getByCredentials(loginRequest.getLogin(), loginRequest.getPlainPassword());
        OAuth2AccessToken accessTokenByAccount = securityTokenService.createAccessTokenByAccount(account);
        return ApiResponse.success(new AccountWithTokenResponse(accessTokenByAccount, account));
    }

    @Secured({Role.TEACHER, Role.STUDENT})
    @GetMapping
    public ResponseEntity<ApiResponse<List<? extends AccountFacade>>> getAccountTeachers(
            @RequestParam(required = false) String fullName,
            Pageable pageable
    ) {
        List<? extends AccountFacade> accounts = accountService.getAccountTeachers(fullName, pageable);
        return ApiResponse.success(accounts);
    }

    @Secured({Role.TEACHER, Role.STUDENT})
    @GetMapping("/{teacherId}/")
    public ResponseEntity<?> getAccountTeacher(@PathVariable int teacherId) throws EntityNotFoundException {
        AccountFacade account = accountService.getAccount(() -> teacherId);
        return ApiResponse.success(account);
    }

    @Secured({Role.TEACHER, Role.STUDENT})
    @GetMapping("/current/")
    public ResponseEntity<?> getCurrent(@AuthenticationPrincipal TokenPrincipal tokenPrincipal) throws EntityNotFoundException {
        AccountFacade account = accountService.getAccount(tokenPrincipal.getAccountIdentity());
        return ApiResponse.success(account);
    }

    @Secured({Role.TEACHER, Role.STUDENT})
    @PutMapping(value = "/current/")
    public ResponseEntity<ApiResponse<AccountFacade>> updateAccount(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @Validated @RequestBody UpdateAccountRequest updateAccountRequest
    )
            throws EntityNotFoundException {
        AccountFacade account = accountService.updateAccount(tokenPrincipal.getAccountIdentity(), updateAccountRequest);
        return ApiResponse.success(account);
    }

    @Secured(Role.EMAIL_VERIFIED)
    @PostMapping(value = "/teachers/register/")
    public ResponseEntity<ApiResponse<AccountWithTokenResponse>> registerTeacher(
            @AuthenticationPrincipal String email,
            @Validated @RequestBody TeacherRegisterRequest teacherRegisterRequest
    )
            throws AccountLoginException {
        AccountTeacherFacade account = accountService.createAccountTeacher(teacherRegisterRequest, email);
        OAuth2AccessToken accessTokenByAccount = securityTokenService.createAccessTokenByAccount(account);
        return ApiResponse.success(new AccountWithTokenResponse(accessTokenByAccount, account));
    }

    @PostMapping(value = "/students/register/")
    public ResponseEntity<ApiResponse<AccountWithTokenResponse>> registerStudent(
            @Validated @RequestBody RegisterRequest registerRequest
    )
            throws AccountLoginException {
        AccountStudentFacade account = accountService.createAccountStudent(registerRequest);
        OAuth2AccessToken accessTokenByAccount = securityTokenService.createAccessTokenByAccount(account);
        return ApiResponse.success(new AccountWithTokenResponse(accessTokenByAccount, account));
    }

    @PostMapping("/current/refresh/")
    public ResponseEntity<ApiResponse<OAuth2AccessToken>> refreshToken(
            @Validated @RequestBody RefreshTokenRequest refreshTokenRequest
    )
            throws RefreshTokenExpiredException {
        OAuth2AccessToken accessToken = securityTokenService.refreshAccessToken(refreshTokenRequest.getToken());
        return ApiResponse.success(accessToken);
    }

    @Secured({Role.TEACHER, Role.STUDENT})
    @PostMapping("/current/logout/")
    public ResponseEntity<?> logout(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @RequestBody RefreshTokenRequest refreshTokenRequest,
            @RequestParam boolean allDevices
    ) throws EntityNotFoundException {
        if (!allDevices) {
            refreshTokenService.releaseOne(tokenPrincipal.getAccountIdentity(), refreshTokenRequest.getToken());
            return ApiResponse.success();
        }
        refreshTokenService.releaseAll(tokenPrincipal.getAccountIdentity(), tokenPrincipal.getAccountIdentity());
        return ApiResponse.success();
    }

    @Secured(Role.EMAIL_VERIFIED)
    @PostMapping(value = "/reset-password/")
    public ResponseEntity resetPassword(
            @AuthenticationPrincipal String email,
            @Validated @RequestBody ChangePasswordRequest changePasswordRequest
    )
            throws EntityNotFoundException {
        accountService.resetPassword(email, changePasswordRequest.getPassword());
        return ApiResponse.success();
    }

    @Secured({Role.TEACHER, Role.STUDENT})
    @PostMapping(value = "/current/change-password/")
    public ResponseEntity<ApiResponse<AccountFacade>> changePassword(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @Validated @RequestBody ChangePasswordRequest changePasswordRequest
    )
            throws EntityNotFoundException {
        AccountFacade account = accountService
                .changePassword(tokenPrincipal.getAccountIdentity(), changePasswordRequest.getPassword());
        return ApiResponse.success(account);
    }
}
