package org.sbsplus.util;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.sbsplus.user.entity.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@Component
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@RequestScope
public class Rq {

    private final HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private User user;

    
    public Rq() {
        ServletRequestAttributes sessionAttributes = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()));
        HttpServletRequest request = sessionAttributes.getRequest();
        HttpServletResponse response = sessionAttributes.getResponse();
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
        if(authentication instanceof UsernamePasswordAuthenticationToken) {
            User user = (User) authentication.getPrincipal();
            this.user = user;
        } else if(authentication instanceof AnonymousAuthenticationToken) {
            User anonymous = new User();
                anonymous.setId((long) -1);
                anonymous.setUsername("");
                
            this.user = anonymous;
        }
    }
    
    // forwarding
    public void forward(String forwardUrl) {
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
        try {
            dispatcher.forward(request, response);
        } catch(ServletException | IOException e){
            e.getMessage();
        }
    }
    
    // unexpected_request
    public String unexpectedRequestForWardUri(String msg){
        
        request.setAttribute("msg", msg);
        return "forward:/unexpected_request";
    }
    
    public Cookie getCookie(String name){
        
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        
        return null;
    }
    
    public boolean isAdmin() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        for(GrantedAuthority grantedAuthority: grantedAuthorities){
            
            // admin 권한 확인
            if(grantedAuthority.getAuthority().equals("ROLE_ADMIN")){
                return true;
            }
        }
        
        return false;
    }
}





















