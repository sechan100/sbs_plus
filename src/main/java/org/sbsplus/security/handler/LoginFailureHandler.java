package org.sbsplus.security.handler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.sbsplus.security.EmailNotAuthenticatedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        
        String errorType = "AuthenticationFailureType";
        
        if(exception instanceof BadCredentialsException){
            errorType = "password";
        }
        
        // UsernameNotFoundException
        if(exception instanceof UsernameNotFoundException){
            errorType = "username";
        }
        
        if(exception instanceof EmailNotAuthenticatedException){
            errorType = "emailAuth";
        }
        
        
        // forwarding with request attribute: exceptionMsg..
        response.sendRedirect( "/login?error=true&type=" + errorType);
        
    }
}
