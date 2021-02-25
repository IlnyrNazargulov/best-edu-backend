package ru.ilnyrdiplom.bestedu.web.mixins;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.ilnyrdiplom.bestedu.facade.model.FileFacade;
import ru.ilnyrdiplom.bestedu.web.utils.FilePathConverter;

@JsonSerialize(as = FileFacade.class)
public interface FileMixin {
    @JsonGetter("url")
    @JsonSerialize(converter = FilePathConverter.class)
    String getFileName();
}
