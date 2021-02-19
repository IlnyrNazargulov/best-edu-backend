package ru.ilnyrdiplom.bestedu.web.services;


import org.eclipse.jetty.http.BadMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.facade.exceptions.*;
import ru.ilnyrdiplom.bestedu.web.contracts.ErrorBody;
import ru.ilnyrdiplom.bestedu.web.contracts.ErrorCodes;
import ru.ilnyrdiplom.bestedu.web.exceptions.RefreshTokenExpiredException;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class ErrorRegister {
    private final ErrorHandlersMap<Exception, ErrorBody> errorHandlers = new ErrorHandlersMap<>();

    @PostConstruct
    protected void init() {
        register(EntityNotFoundException.class,
                (e) -> new ErrorBody(ErrorCodes.NOT_FOUND, "Entity " + e.getEntityClass().getSimpleName() + " with id " + e.getId() + " not found.", e, HttpStatus.NOT_FOUND));
        register(RefreshTokenExpiredException.class,
                (e) -> new ErrorBody(ErrorCodes.REFRESH_TOKEN_EXPIRED, "Refresh token is expired.", e, HttpStatus.UNAUTHORIZED));
        register(BadMessageException.class,
                (e) -> new ErrorBody(ErrorCodes.BAD_HTTP_REQUEST, e.getCause().getMessage(), e, HttpStatus.BAD_REQUEST));
        register(AccountLoginException.class,
                (e) -> new ErrorBody(ErrorCodes.LOGIN_EXISTS, e.getMessage(), e, HttpStatus.BAD_REQUEST));
        register(WrongRequestCodeException.class,
                (e) -> new ErrorBody(ErrorCodes.INVALID_CODE, "Wrong code.", e, HttpStatus.BAD_REQUEST));
        register(AccessDeniedException.class,
                (e) -> new ErrorBody(ErrorCodes.ACCESS_DENIED, "The specified token does not grant access to the requested resource.", e, HttpStatus.FORBIDDEN));
        register(DisciplineAlreadyExistsException.class,
                (e) -> new ErrorBody(ErrorCodes.DISCIPLINE_ALREADY_EXISTS, e.getMessage(), e, HttpStatus.BAD_REQUEST));
        register(ImpossibleAccessDisciplineException.class,
                (e) -> new ErrorBody(ErrorCodes.IMPOSSIBLE_ACCESS_DISCIPLINE, e.getMessage(), e, HttpStatus.BAD_REQUEST));
        register(WrongAccountTypeException.class,
                (e) -> new ErrorBody(ErrorCodes.WRONG_ACCOUNT_TYPE, e.getMessage(), e, HttpStatus.BAD_REQUEST));

    }

    protected <T extends Exception> void register(Class<T> exceptionClass, Function<T, ErrorBody> converter) {
        errorHandlers.put(exceptionClass, (Function<Exception, ErrorBody>) converter);
    }

    public ErrorBody lookup(Exception e) {
        return errorHandlers.getBody(e);
    }

    private static class ErrorHandlersMap<E extends Exception, R extends ErrorBody> extends HashMap<Class<? extends E>, Function<E, R>> {
        R getBody(E exception) {
            Function<E, R> f = get(exception.getClass());
            if (f == null) {
                return null;
            }
            return f.apply(exception);
        }
    }
}
