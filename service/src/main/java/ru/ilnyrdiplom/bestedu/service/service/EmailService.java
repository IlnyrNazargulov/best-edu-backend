package ru.ilnyrdiplom.bestedu.service.service;

public interface EmailService {
    void sendVerifyMessage(String email, String validationCode);
}
