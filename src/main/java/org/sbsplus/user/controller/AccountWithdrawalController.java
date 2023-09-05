package org.sbsplus.user.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.sbsplus.user.entity.Account;
import org.sbsplus.user.repository.AccountRepository;
import org.sbsplus.util.Rq;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AccountWithdrawalController {
    
    private final Rq rq;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    
    
    @GetMapping("/withdrawal")
    public String withdrawal(){
        
        return "/user/withdrawal";
    }
    
    @PostMapping("/withdrawal")
    public String withdrawal(String password){
        
        Account withdrawalTargetAccount = rq.getAccount();
        String encodedLoginedUserPassword = rq.getAccount().getPassword();
        
        
        // password incorrect
        if(!passwordEncoder.matches(password, encodedLoginedUserPassword)){
            throw new BadCredentialsException("BadCredentialsException");
        }
        
        // force logout
        HttpSession session = rq.getSession();
        
        // 1) AuthenticationToken: UsernamePasswordAuthenticationToken initialization to null
        SecurityContextHolder.getContext().setAuthentication(null);
        
        // 2) session expire
        session.invalidate();
        
        // delete on DB
        accountRepository.delete(withdrawalTargetAccount);
        
        
        return "redirect:/";
    }
}
