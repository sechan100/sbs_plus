package org.sbsplus.security.entrypoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        
        String errorType;
        String redirectUri = request.getRequestURI();
        String queryString;
        
        
        // UsernameNotFoundException
        if(authException instanceof UsernameNotFoundException){
            
            errorType = "username";
            queryString = "?error=true&type=" + errorType;
            
            // redirect
            response.sendRedirect(redirectUri + queryString);
            
        // BadCredentialsException
        } else if(authException instanceof BadCredentialsException){
            
            errorType = "password";
            queryString = "?error=true&type=" + errorType;
            
            // redirect
            response.sendRedirect(redirectUri + queryString);
            
        // anonymous 사용자가 authenticated url에 접근을 시도할 경우 일반적으로 발생하는 예외
        } else if(authException instanceof InsufficientAuthenticationException) {
            
            redirectUri = "/access_denied";
            queryString = "";
            request.setAttribute("msg", "로그인 후 이용해주세요");
            
            // forward
            request.getRequestDispatcher(redirectUri + queryString).forward(request, response);
        }
        
    }
}
