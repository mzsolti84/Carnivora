package hu.progmatic.felhasznalo;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.SQLOutput;

@Service
@Log
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void emailKuldes(String to, String subject, String body) {
        /*SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("carnivora.project@gmail.com");
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("Mail Sent Successfully");*/

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(body, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("carnivora.project@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.warning("Email küldési hiba!");
            throw new IllegalStateException("Email küldési hiba!");
        }
    }

    public static String emailBodyBuilder(String felhasznalo, String link) {
        String body = "";
        body += String.format("<p>Kedves %s!</p>", felhasznalo);
        body += "<p>Az alábbi linken tudod megerősíteni a regisztrációt.<p>";
        body += String.format("<a href=\"%s\">Megerősítő link</a>", link);
        return body;
    }
}
