package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.service.config.EmailProperties;
import ru.ilnyrdiplom.bestedu.service.service.EmailService;
import ru.ilnyrdiplom.bestedu.service.service.MailSenderService;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final MailSenderService mailSenderService;
    private final EmailProperties emailProperties;

    @Override
    public void sendVerifyMessage(String email, String validationCode) {
        String message = emailProperties.getTemplateValidationEmail()
                .replaceAll("#code#", validationCode)
                .replaceAll("#email#", email);
        mailSenderService.send(email, emailProperties.getSubjectValidationEmail(), message);
    }
}

