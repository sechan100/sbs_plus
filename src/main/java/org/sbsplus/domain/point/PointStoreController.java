package org.sbsplus.domain.point;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sbsplus.domain.email.EmailMsg;
import org.sbsplus.util.Rq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PointStoreController {
    
    private final PointStoreService pointStoreService;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final Rq rq;
    
    
    @GetMapping("/point/store")
    public String pointStore(Model model) {
        
        List<PointStoreItem> storeItems = pointStoreService.findAll();
        
        model.addAttribute("storeItems", storeItems);
        
        return "/point/store";
    }
    
    @GetMapping("/point/purchase")
    @ResponseBody
    public Integer purchase(@RequestParam Integer id) throws MessagingException {
        
        boolean purchaseSuccess = pointStoreService.purchase(id);
        
        if (purchaseSuccess) {
            sendPurchaseEmail(javaMailSender, templateEngine, rq, pointStoreService.findById(id));
            return pointStoreService.getQuantity(id);
        } else {
            return -1;
        }
    }
    

    private void sendPurchaseEmail(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine, Rq rq, PointStoreItem item) throws MessagingException {
        
        Context context = new Context();
        context.setVariable("item", item);
        String context_ = templateEngine.process("/util/storePurchase", context);
        
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        EmailMsg emailMessage = EmailMsg.builder().to(rq.getUser().getEmail()).subject("[SBS PLUS+] 포인트 구매 내역 알림").build();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); // address
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // subject
            mimeMessageHelper.setText(context_, true); // html page
            mimeMessageHelper.setFrom("sbsplus100@gmail.com");
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            
            
        } catch (MessagingException e) {
            throw new MessagingException();
            
        } catch (MailException e) {
            log.info("Fail To Send Email For Point Store Purchase!");
            return;
        }
    }
    
    
}