package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EmailCodeRequest {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String code;
}
