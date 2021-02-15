package ru.ilnyrdiplom.bestedu.web.services;


import org.eclipse.jetty.http.BadMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.facade.exceptions.AccountLoginException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
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
