package ru.ilnyrdiplom.bestedu.facade.exceptions;

public class AccountLoginException extends BaseException {
    public AccountLoginException(String login) {
        super("Account with login " + login + " already exists.");
    }
}
