package org.sbsplus.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.sbsplus.util.Rq;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler { // 접근이 거부된 경우 처리

    private final Rq rq;

    @Setter
    private String defaultAccessDeniedPageUrl = "/access_denied";
    //기본적으로 사용할 접근 거부 페이지의 URL 설정. 접근 거부시 사용

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // AccessDeniedException : 접근 거부 예외 객체
        Authentication authentication = ((SecurityContext)request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY)).getAuthentication();
        // 현재 사용자의 인증 정보 가져오기 위해 security의 securityContext에서 authentication 객체 추출
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>)authentication.getAuthorities();
        // 현재 사용자의 권한 목록 가져옴.
        
        Set<String> authoritie = new HashSet<>(); // 사용자 권한 저장하기 위한 set 생성
        for(GrantedAuthority grantedAuthority : grantedAuthorities){
            authoritie.add(grantedAuthority.getAuthority());
            // 사용자 권한 목록을 순회하면서 각 권한을 'authorities' 집합에 추가
        }
        
        // 기본 메세지
        if(!authoritie.contains("ROLE_ADMIN"))
            request.setAttribute("msg", "관리자 권한으로 이용해주세요.");
        else
            request.setAttribute("msg", accessDeniedException.getMessage());
        //접근 거부 메시지 가져와 request 객체의 속성으로 설정.
        request.getRequestDispatcher(defaultAccessDeniedPageUrl).forward(request, response);
    }   // 접근 거부 페이지(defaultAccessDeniedPageUrl)로 포워딩. 사용자에게 적절한 메시지와 함께 접근 거부 페이지 표시.
}
