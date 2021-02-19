package ru.ilnyrdiplom.bestedu.web.contracts.requests;

import lombok.Getter;
import lombok.Setter;
import ru.ilnyrdiplom.bestedu.facade.model.requests.UpdateAccountRequestFacade;

import javax.validation.constraints.NotBlank;


@Setter
@Getter
public class UpdateAccountRequest implements UpdateAccountRequestFacade {
    @NotBlank
    private String secondName;
    @NotBlank
    private String firstName;
    private String patronymic;
}
