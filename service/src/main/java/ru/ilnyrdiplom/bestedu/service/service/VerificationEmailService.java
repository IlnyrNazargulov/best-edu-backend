package ru.ilnyrdiplom.bestedu.service.service;

public interface VerificationEmailService {
    void sendVerifyMessage(String email, String code);
}
