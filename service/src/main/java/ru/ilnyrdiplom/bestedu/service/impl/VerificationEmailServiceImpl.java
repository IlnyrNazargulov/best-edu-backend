package ru.ilnyrdiplom.bestedu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilnyrdiplom.bestedu.service.EmailProperties;
import ru.ilnyrdiplom.bestedu.service.service.MailSenderService;
import ru.ilnyrdiplom.bestedu.service.service.VerificationEmailService;

@Component
@RequiredArgsConstructor
public class VerificationEmailServiceImpl implements VerificationEmailService {

    private final MailSenderService mailSenderService;
    private final EmailProperties emailProperties;

    @Override
    public void sendVerifyMessage(String email, String code) {
        String message = emailProperties.getTemplate()
                .replaceAll("#code#", code)
                .replaceAll("#email#", email);
        mailSenderService.send(email, emailProperties.getSubject(), message);
    }
}

