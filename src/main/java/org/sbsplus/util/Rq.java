package org.sbsplus.util;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.sbsplus.user.entity.Account;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;

@Component
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@RequestScope
public class Rq {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final HttpSession session;
    private final Account account;


    /**
     * Bean Constructor
     * @param request
     * @param response
     * @param session
     */
    public Rq(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        this.request = request;
        this.response = response;
        this.session = session;

        // get UserDetails: Account
        SecurityContext context = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication = context.getAuthentication();
        Account account = (Account)authentication.getPrincipal();

        this.account = account;
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

}





















