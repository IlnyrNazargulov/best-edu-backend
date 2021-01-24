package ru.ilnyrdiplom.bestedu.web.contracts.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ilnyrdiplom.bestedu.web.contracts.ErrorBody;

import java.util.List;


@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final boolean isSuccess;
    private final List<ErrorBody> errors;
}
