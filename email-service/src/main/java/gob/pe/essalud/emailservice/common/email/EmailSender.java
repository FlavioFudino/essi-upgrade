package gob.pe.essalud.emailservice.common.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Component
public class EmailSender {

    private final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    @Value("${spring.mail.display-name}")
    private String emailFrom;

    private final JavaMailSender emailSender;

    public EmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    /*public void sendSimple(String to, String from, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(from);
        emailSender.send(message);

        logger.info("Correo enviado a: {}, con asunto: {}", to, subject);
    }*/

    public boolean send(String to, String subject, String text) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(emailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            emailSender.send(message);

            logger.info("Correo enviado a: {}, con asunto: {}", to, subject);
            return true;

        } catch (MessagingException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}