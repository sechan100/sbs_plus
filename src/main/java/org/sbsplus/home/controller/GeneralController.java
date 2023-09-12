package org.sbsplus.home.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.sbsplus.util.DataCreator;
import org.sbsplus.util.Rq;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        
        // ************ TEST DATA CREATE ***************
        if(!isTestDataCreated && ddlAutoValue.equals("create")) {
            dataCreator.createTestData();
            isTestDataCreated = true;
        }// *********************************************
        
        
        return "/home";
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
        
        return "/util/accessDenied";
    }
    
    @GetMapping("/unexpected_request")
    public String unexpectedRequest(Model model, HttpServletRequest request){
        
        String msg = (String) request.getAttribute("msg");
        
        model.addAttribute("msg", msg);
        
        return "/util/unexpected_request";
    }


}
