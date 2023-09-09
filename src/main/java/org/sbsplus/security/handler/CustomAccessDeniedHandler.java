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
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final Rq rq;

    @Setter
    private String defaultAccessDeniedPageUrl = "/access_denied";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication authentication = ((SecurityContext)request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY)).getAuthentication();
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>)authentication.getAuthorities();
        
        Set<String> authoritie = new HashSet<>();
        for(GrantedAuthority grantedAuthority : grantedAuthorities){
            authoritie.add(grantedAuthority.getAuthority());
        }
        
        // 기본 메세지
        if(!authoritie.contains("ROLE_ADMIN"))
            request.setAttribute("msg", "관리자 권한으로 이용해주세요.");
        else
            request.setAttribute("msg", accessDeniedException.getMessage());
        
        request.getRequestDispatcher(defaultAccessDeniedPageUrl).forward(request, response);
    }
}
