package org.sbsplus.home.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.sbsplus.util.DataCreator;
import org.sbsplus.util.Rq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class GeneralController {

    private final Rq rq;
    private final DataCreator dataCreator;
    
    // ************ TEST DATA CREATE ***************
    private boolean isTestDataCreated = false;
    // *********************************************
    
    
    @GetMapping("/")
    public String home(){
        
        // ************ TEST DATA CREATE ***************
        if(!isTestDataCreated) {
//            dataCreator.createTestData();
            isTestDataCreated = true;
        }// *********************************************
        
        
        return "/home";
    }


    @GetMapping("/access_denied")
    public String accessDenied(Model model, HttpServletRequest request){

        String msg = (String) request.getAttribute("msg");

        model.addAttribute("msg", msg);

        return "/util/access_denied";
    }
    
    @GetMapping("/unexpected_request")
    public String unexpectedRequest(Model model, HttpServletRequest request){
        
        String msg = (String) request.getAttribute("msg");
        
        model.addAttribute("msg", msg);
        
        return "/util/unexpected_request";
    }


}
