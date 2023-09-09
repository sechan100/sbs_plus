package org.sbsplus.security.handler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j // Lombok 어노테이선. 로그를 기록하기 위한 Logger 인스턴스를 자동으로 생성
public class LoginFailureHandler implements AuthenticationFailureHandler { //로그인 실패시 처리
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // AuthenticationException : 로그인 인증 실패 예외 객체
        String errorType = "AuthenticationFailureType"; //실패한 인증의 유형
        
        if(exception instanceof BadCredentialsException){
            errorType = "password";
        }
        
        // UsernameNotFoundException
        if(exception instanceof UsernameNotFoundException){
            errorType = "username";
        }
        
        
        // forwarding with request attribute: exceptionMsg..
        // 실패한 인증의 유형(errorType)dmf URL 파라미터로 포함해 /login페이지로 redirect.
        // 페이지에서 실패 인증의 유형에 따른 오류 페이지 표시
        response.sendRedirect( "/login?error=true&type=" + errorType);
        
    }
}
