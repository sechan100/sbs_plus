package org.sbsplus.domain.user.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.domain.user.repository.UserRepository;
import org.sbsplus.util.Rq;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserWithdrawalController {
    
    private final Rq rq;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    
    
    @GetMapping("/withdrawal")
    public String withdrawal(){
        
        return "/user/withdrawal";
    }
    
    @PostMapping("/withdrawal")
    public String withdrawal(@RequestParam Map<String, String> passwordForm){
        
        User withdrawalTargetUser = rq.getUser();
        String encodedLoginedUserPassword = rq.getUser().getPassword();
        
        // convert null to empty String: ""
        String inputPassword = (passwordForm.get("password") == null ? "" : passwordForm.get("password"));
        
        // password incorrect
        if(!passwordEncoder.matches(inputPassword, encodedLoginedUserPassword)){
            throw new BadCredentialsException("BadCredentialsException");
        }
        
        // force logout
        HttpSession session = rq.getSession();
        
        // 1) AuthenticationToken: UsernamePasswordAuthenticationToken initialization to null
        SecurityContextHolder.getContext().setAuthentication(null);
        
        // 2) session expire
        session.invalidate();
        
        // delete on DB
        userRepository.delete(withdrawalTargetUser);
        
        
        return "redirect:/";
    }
}
