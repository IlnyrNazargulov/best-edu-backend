package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.ilnyrdiplom.bestedu.facade.model.requests.RegisterRequestFacade;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class TeacherRegisterRequest implements RegisterRequestFacade {
    public static final int MIN_PASSWORD_LENGTH = 6;

    @NotBlank
    private String secondName;
    @NotBlank
    private String firstName;
    private String patronymic;
    @NotNull
    @Size(min = MIN_PASSWORD_LENGTH)
    private String plainPassword;
    @JsonIgnore
    private String login;
}
