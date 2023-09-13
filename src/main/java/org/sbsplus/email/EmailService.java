package org.sbsplus.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sbsplus.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    
    @Value("${server.port}")
    private Integer port;
    
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final UserService userService;
    
    public String sendMail(EmailMsg emailMessage, String type) throws MessagingException {
        
        String authKey = createKey();
        
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); // address
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // subject
            mimeMessageHelper.setText(setContext(authKey, emailMessage.getTo(), type), true); // html page
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            
            return authKey;
            
        } catch (MessagingException e) {
            throw new MessagingException();
            
        } catch (MailException e) {
            log.info("Fail To Send Email For Authentication!");
            return null;
        }
    }
    
    private String createKey() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);
            switch(index) {
                case 0 -> key.append((char) ((int) random.nextInt(26) + 97));
                case 1 -> key.append((char) ((int) random.nextInt(26) + 65));
                default -> key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }
    
    public String setContext(String code, String email ,String type) {
        
        Context context = new Context();
        
        context.setVariable("code", code);
        context.setVariable("email", email);
//        if(port == null){
//            throw A
//        }
        context.setVariable("port", port);
        
        return templateEngine.process(type, context);
    }
    
    
    
}