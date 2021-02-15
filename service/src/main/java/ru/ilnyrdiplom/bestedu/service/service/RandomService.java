package ru.ilnyrdiplom.bestedu.service.service;

import java.util.UUID;

public interface RandomService {
    UUID generateUUID();

    String generatePassword(int length);

    String generateCode(int length);
}
