package ru.ilnyrdiplom.bestedu.service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ru.ilnyrdiplom.bestedu.facade.model.RequestCodeStatusFacade;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class RequestCodeStatus implements RequestCodeStatusFacade {
    private final int nextAttemptAfter;
    private final boolean isCodeSent;
}
