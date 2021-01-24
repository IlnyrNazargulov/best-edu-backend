package ru.ilnyrdiplom.bestedu.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import ru.ilnyrdiplom.bestedu.dal.DalModule;

@ComponentScan
@Configuration
@PropertySource("classpath:service.properties")
@Import({DalModule.class})
public class ServiceModule {
}