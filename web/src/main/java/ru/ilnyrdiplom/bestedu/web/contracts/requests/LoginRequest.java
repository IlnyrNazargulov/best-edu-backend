package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class LoginRequest {
    public static final int MIN_PASSWORD_LENGTH = 6;

    @NotNull
    @Email
    private String login;
    @NotNull
    @Size(min = MIN_PASSWORD_LENGTH)
    private String plainPassword;
}
