package com.ibrahimkvlci.ecommerce.auth.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void sendVerificationEmail(String email, String code) {
        try {
            Context context = new Context();
            context.setVariable("email", email);
            context.setVariable("code", code);

            String htmlContent = templateEngine.process("mail-template", context);

            MimeMessage mimeMessage = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Hoşgeldiniz - Üyelik Onayı");
            helper.setFrom(from);

            helper.setText(htmlContent, true);

            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }

    }

}
