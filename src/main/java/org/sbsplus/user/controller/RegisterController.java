package org.sbsplus.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sbsplus.email.EmailMsg;
import org.sbsplus.email.EmailService;
import org.sbsplus.type.Category;
import org.sbsplus.user.dto.UserDto;
import org.sbsplus.user.entity.User;
import org.sbsplus.user.service.UserService;
import org.sbsplus.util.EmailAuthKey;
import org.sbsplus.util.Rq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;
    private final EmailService emailService;
    private final EmailAuthKey emailAuthKey;
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


        // username invalid
        if(!userService.isValidUsername(userDto.getUsername())){
            return "redirect:/register?error=true&type=username";

        // email invalid
        } else if(!userService.isValidEmail(userDto.getEmail())) {
            return "redirect:/register?error=true&type=email";

        // match password with confirmPassword
        } else if(userService.confirmPassword(userDto.getPassword(), userDto.getConfirmPassword())) {
            return "redirect:/register?error=true&type=password";
        }
        
        EmailMsg emailMessage = EmailMsg.builder()
                .to(userDto.getEmail())
                .subject("[SBS PLUS+] 이메일 인증 웹 발신")
                .build();
        
        try {
            userDto.setAuthKey(emailService.sendMail(emailMessage, "/email"));
        } catch (Exception e) {
            e.getMessage();
        }
        
        emailAuthKey.addAuthKey(userDto.getEmail(), userDto.getAuthKey());
        userService.save(userDto);

        return "redirect:/login";
    }
    
    @GetMapping("/emailConfirm")
    @Transactional
    public String doEmailConfirm(String email, String code, Model model) {
        
        String authKey = emailAuthKey.getAuthKey(email);
        
        // 이메일 인증 완료시
        if(code.equals(authKey)) {
            
            // 임시 authKey 데이터 삭제
            emailAuthKey.removeAuthKey(email);
            
            // 영속 상태인 엔티티 가져오기
            User userWithoutEmailAuthentication = userService.findByEmail(email);
            
            // 이메일 인증 = true
            userWithoutEmailAuthentication.setEmailAuth(true);
        }
        
        return "redirect:/login";
    }
}