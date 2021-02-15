package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import lombok.Getter;
import lombok.Setter;
import ru.ilnyrdiplom.bestedu.facade.model.requests.RegisterRequestFacade;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class RegisterRequest extends LoginRequest implements RegisterRequestFacade {
    @NotBlank
    private String secondName;
    @NotBlank
    private String firstName;
    private String patronymic;
}
