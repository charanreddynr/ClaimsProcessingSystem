package com.charan.claimsprocessingsystem.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private JavaMailSender mailSender;  // Why? DI for email.

    public void sendEmail(String to, String subject, String text) {  // Why? Send email.
        SimpleMailMessage message = new SimpleMailMessage();  // Why? Simple text.
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);  // Why? Sends; if not, no email.
    }

    public void sendSms(String to, String text) {  // Why? Stub – print for test.
        System.out.println("SMS to " + to + ": " + text);  // Why? Log; real use Twilio.
    }
}