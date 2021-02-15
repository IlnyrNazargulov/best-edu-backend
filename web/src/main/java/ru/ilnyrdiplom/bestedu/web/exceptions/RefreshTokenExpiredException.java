package ru.ilnyrdiplom.bestedu.web.exceptions;

import ru.ilnyrdiplom.bestedu.facade.exceptions.BaseException;

public class RefreshTokenExpiredException extends BaseException {
    public RefreshTokenExpiredException() {
        super("The specified refresh token is expired.");
    }
}
