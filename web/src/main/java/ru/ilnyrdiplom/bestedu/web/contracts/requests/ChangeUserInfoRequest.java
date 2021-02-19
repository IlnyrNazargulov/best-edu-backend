package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import lombok.Getter;
import lombok.Setter;
import ru.ilnyrdiplom.bestedu.facade.model.requests.ChangeUserInfoRequestFacade;

import javax.validation.constraints.NotBlank;


@Setter
@Getter
public class ChangeUserInfoRequest implements ChangeUserInfoRequestFacade {
    @NotBlank
    private String secondName;
    @NotBlank
    private String firstName;
    private String patronymic;
}
