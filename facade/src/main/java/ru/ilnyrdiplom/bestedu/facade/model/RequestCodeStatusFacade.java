package ru.ilnyrdiplom.bestedu.facade.model;

public interface RequestCodeStatusFacade {

    int getNextAttemptAfter();

    boolean isCodeSent();
}
