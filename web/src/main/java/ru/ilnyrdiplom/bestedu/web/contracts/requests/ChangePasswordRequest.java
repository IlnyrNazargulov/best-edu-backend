package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Setter
@Getter
public class ChangePasswordRequest {

    public static final int MIN_PASSWORD_LENGTH = 6;

    @Size(min = MIN_PASSWORD_LENGTH)
    @NotNull
    private String password;
}
