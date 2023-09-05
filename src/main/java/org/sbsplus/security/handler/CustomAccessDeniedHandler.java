package org.sbsplus.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.sbsplus.util.Rq;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final Rq rq;

    @Setter
    private String defaultAccessDeniedPageUrl = "/access_denied";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        request.setAttribute("msg", accessDeniedException.getMessage());
        request.getRequestDispatcher(defaultAccessDeniedPageUrl).forward(request, response);
//        rq.forward(defaultAccessDeniedPageUrl);
    }
}
