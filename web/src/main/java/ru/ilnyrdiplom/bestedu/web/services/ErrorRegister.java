package ru.ilnyrdiplom.bestedu.web.services;


import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.web.contracts.ErrorBody;


import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class ErrorRegister {
    private final ErrorHandlersMap<Exception, ErrorBody> errorHandlers = new ErrorHandlersMap<>();

    @PostConstruct
    protected void init() {

    }

    protected <T extends Exception> void register(Class<T> exceptionClass, Function<T, ErrorBody> converter) {
        errorHandlers.put(exceptionClass, (Function<Exception, ErrorBody>) converter);
    }

    public ErrorBody lookup(Exception e) {
        return errorHandlers.getBody(e);
    }

    private static class ErrorHandlersMap<E extends Exception, R extends ErrorBody> extends HashMap<Class<? extends E>, Function<E, R>> {
        R getBody(E exception) {
            Function<E, R> f = get(exception.getClass());
            if (f == null) {
                return null;
            }
            return f.apply(exception);
        }
    }
}
