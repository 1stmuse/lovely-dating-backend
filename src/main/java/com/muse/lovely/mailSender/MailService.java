package com.muse.lovely.mailSender;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@EnableAsync
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Async
    public void sendMail(
            String subject,
            String recipientEmail,
            String activationCode
    ) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("activationCode", activationCode);

        Context context = new Context();
        context.setVariables(properties);



        helper.setFrom("contact@muse.com");
        helper.setSubject(subject);
        helper.setTo(recipientEmail.trim());
        String template = templateEngine.process("activationMail", context);

        helper.setText(template, true);

        mailSender.send(message);

    }
}
