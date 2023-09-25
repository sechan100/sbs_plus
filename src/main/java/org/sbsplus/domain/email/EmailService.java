package org.sbsplus.domain.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sbsplus.domain.user.service.UserService;
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
    
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final UserService userService;
    
    public String sendEmailAuthenticationMail(EmailMsg emailMessage, String type) throws MessagingException {
        
        String authKey = createAuthKey();
        
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); // address
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // subject
            mimeMessageHelper.setText(setContext(authKey, type), true); // html page
            mimeMessageHelper.setFrom("sbsplus100@gmail.com");
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            
            return authKey;
            
        } catch (MessagingException e) {
            throw new MessagingException();
            
        } catch (MailException e) {
            log.info("Fail To Send Email For Authentication!");
            return null;
        }
    }
    
    
    private String createAuthKey() {
        Random random = new Random();
        int range = 999999 - 111111 + 1;
        int generatedNumber = random.nextInt(range) + 111111;
        return Integer.toString(generatedNumber);
    }
    
    public String setContext(String authKey, String type) {
        
        Context context = new Context();
        
        context.setVariable("authKey", authKey);
        
        return templateEngine.process(type, context);
    }
    
    
    
}