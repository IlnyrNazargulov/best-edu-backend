package ru.ilnyrdiplom.bestedu.service.service;

public interface MailSenderService {
    void send(String emailTo, String subject, String message);
}
