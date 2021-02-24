package ru.ilnyrdiplom.bestedu.dal.dto;

import lombok.Getter;
import ru.ilnyrdiplom.bestedu.facade.model.ExerciseWithoutContentFacade;

import java.time.Instant;

@Getter
public class ExerciseWithoutContent implements ExerciseWithoutContentFacade {
    private int id;
    private String name;
    private Instant createdAt;
    private int orderNumber;

    public ExerciseWithoutContent(int id, String name, Instant createdAt, int orderNumber) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.orderNumber = orderNumber;
    }
}
