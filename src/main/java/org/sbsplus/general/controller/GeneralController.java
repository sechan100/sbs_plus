package org.sbsplus.general.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.sbsplus.util.DataCreator;
import org.sbsplus.util.Rq;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class GeneralController {

    private final Rq rq;
    private final DataCreator dataCreator;
    
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAutoValue;
    
    // ************ TEST DATA CREATE ***************
    private boolean isTestDataCreated = false;
    // *********************************************
    
    
    @GetMapping("/")
    public String home(){

        // 현재 사용자의 역할 확인
        if (rq.isAdmin()) {
            return "redirect:/admin"; // 관리자인 경우 관리자 페이지로 리다이렉트
        }

        // ************ TEST DATA CREATE ***************
        if(!isTestDataCreated && ddlAutoValue.equals("create")) {
            dataCreator.createTestData();
            isTestDataCreated = true;
        }// *********************************************
        
        
        return "/home/main";
    }


    @GetMapping("/accessDenied")
    public String accessDenied(Model model, HttpServletRequest request, @RequestParam(required = false) String requiredAuthority){
        
        // "/admin/**"에 접근을 시도한 경우
        if(requiredAuthority != null && requiredAuthority.equals("admin")) {
            model.addAttribute("msg", "관리자 권한이 없습니다.");
        } else {
            String msg = (String) request.getAttribute("msg");
            model.addAttribute("msg", msg);
        }
        
        return "/exception/accessDenied";
    }
    
    @GetMapping("/unexpectedRequest")
    public String unexpectedRequest(Model model, HttpServletRequest request){
        
        String msg = (String) request.getAttribute("msg");
        
        model.addAttribute("msg", msg);
        
        return "/exception/400";
    }


}
