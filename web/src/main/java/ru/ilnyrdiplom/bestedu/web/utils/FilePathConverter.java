package ru.ilnyrdiplom.bestedu.web.utils;

import com.fasterxml.jackson.databind.util.StdConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.web.config.ConverterConfiguration;

@Component
@RequiredArgsConstructor
public class FilePathConverter extends StdConverter<String, String> {

    private final ConverterConfiguration configuration;

    @Override
    public String convert(String path) {
        return configuration.getFileBaseUrl() + path;
    }
}
