package ru.ilnyrdiplom.bestedu.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import ru.ilnyrdiplom.bestedu.facade.model.*;
import ru.ilnyrdiplom.bestedu.web.mixins.*;

@Slf4j
@SpringBootApplication
@PropertySource({"classpath:security.properties", "classpath:web.properties"})
@ConfigurationPropertiesScan
public class BestEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(BestEduApplication.class, args);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.mixIn(AccountFacade.class, AccountMixin.class);
            jacksonObjectMapperBuilder.mixIn(CommentFacade.class, CommentMixin.class);
            jacksonObjectMapperBuilder.mixIn(DisciplineFacade.class, DisciplineMixin.class);
            jacksonObjectMapperBuilder.mixIn(ExerciseFacade.class, ExerciseMixin.class);
            jacksonObjectMapperBuilder.mixIn(ExerciseFileFacade.class, ExerciseFileMixin.class);
            jacksonObjectMapperBuilder.mixIn(FileFacade.class, FileMixin.class);
            jacksonObjectMapperBuilder.mixIn(NotificationFacade.class, NotificationMixin.class);
            jacksonObjectMapperBuilder.mixIn(ExerciseWithoutContentFacade.class, ExerciseWithoutContentMixin.class);
            jacksonObjectMapperBuilder.mixIn(AccessDisciplineFacade.class, AccessDisciplineMixin.class);

            jacksonObjectMapperBuilder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            jacksonObjectMapperBuilder.featuresToEnable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
            jacksonObjectMapperBuilder.defaultViewInclusion(true);
        };
    }
}
