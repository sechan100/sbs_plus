package org.sbsplus.security.entrypoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String errorType = "AuthenticationFailureType";
        
        if(authException instanceof BadCredentialsException){
            errorType = "password";
        }
        
        // UsernameNotFoundException
        if(authException instanceof UsernameNotFoundException){
            errorType = "username";
        }
        
        
        // forwarding with request attribute: exceptionMsg..
        response.sendRedirect(String.valueOf(request.getRequestURL()) + "?error=true&type=" + errorType);
        
    }
}
