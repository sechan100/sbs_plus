package org.sbsplus.domain.user.controller;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sbsplus.domain.user.dto.UserDto;
import org.sbsplus.domain.user.service.UserService;
import org.sbsplus.domain.email.EmailMsg;
import org.sbsplus.domain.email.EmailService;
import org.sbsplus.general.type.Category;
import org.sbsplus.util.EmailAuthentication;
import org.sbsplus.util.Rq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final EmailService emailService;
    private final EmailAuthentication emailAuthentication;
    private final Rq rq;


    // get request register form page
    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("user", new UserDto());
        model.addAttribute("categories", Category.getCategories());
        
        return "/user/register";
    }


    // register process
    @PostMapping("/register")
    public String register_prcs(UserDto userDto) {
        
        
        if(!emailAuthentication.isAuthenticatedEmail(userDto.getEmail())) {
            return "redirect:/register?error=true&type=email";
            
        // username invalid
        } else if(!userService.isValidUsername(userDto.getUsername())){
            return "redirect:/register?error=true&type=username";

        // match password with confirmPassword
        } else if(userService.confirmPassword(userDto.getPassword(), userDto.getConfirmPassword())) {
            return "redirect:/register?error=true&type=password";
        }
        
        // 인증된 이메일 임시 저장 데이터 삭제
        emailAuthentication.removeEamilData(userDto.getEmail());
        
        userService.save(userDto);

        return "redirect:/login";
    }
    
    @GetMapping("/email/confirm")
    @ResponseBody
    public String doEmailConfirm(@RequestParam String email, @RequestParam String authKey, Model model) {
        
        String baseAuthKey = emailAuthentication.getAuthKey(email);
        
        // authKey 일치
        if(baseAuthKey.equals(authKey)) {
            
            // 기존 email 데이터 삭제
            emailAuthentication.removeEamilData(email);
            
            // 인증된 이메일에 추가
            emailAuthentication.addAuthenticatedEmail(email);
            
        }
        
        return String.format("""
                <div class="color-info">인증이 완료되었습니다.</div>
                <input type="hidden" name="email" value="%s"/>
                """, email);
    }
    
    @GetMapping("/email/sendAuthKey")
    @ResponseBody
    public String emailAuth(@RequestParam String email){
        
        // email invalid
        if(!userService.isValidEmail(email)) {
            return """
                    <div style='color: red'>이미 사용중인 이메일입니다.</div>
                    <button type="button" id='sendBtn'>이메일 인증번호 전송</button>
                    <script>
                        $('#sendBtn').on('click', () => {
                            htmx.trigger(document.querySelector('[hx-trigger="emailSend"]'), 'emailSend');
                        });
                    </script>
                    """;
        }
        
        // 이메일 발송
        EmailMsg emailMessage = EmailMsg.builder().to(email).subject("[SBS PLUS+] 이메일 인증번호 웹 발신").build();
        try {
            
            // 기존 인증키 데이터 지움
            if(emailAuthentication.getAuthKey(email) != null){
                emailAuthentication.removeEamilData(email);
            }
            
            emailAuthentication.addAuthKey(email, emailService.sendMail(emailMessage, "/util/email"));
            
        } catch(MessagingException e) {
            throw new RuntimeException(e);
        }
        
        
        return String.format("""
                <input hx-get="/email/confirm?email=%s" hx-trigger="emailConfirm" hx-target="#target" type="text" class="form-control" name="authKey" placeholder="인증번호를 입력해주세요." required>
                <button type="button" id="confirmBtn">인증완료</button>
                <script>
                    $('#confirmBtn').on('click', () => {
                        htmx.trigger(document.querySelector('[hx-trigger="emailConfirm"]'), 'emailConfirm');
                    });
                </script>
                """, email);
    }
}
























