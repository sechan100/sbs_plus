package org.sbsplus.admin;


import lombok.RequiredArgsConstructor;
import org.sbsplus.util.Rq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminController {
    
    
    private final AdminService adminService;
    private final Rq rq;
    
    @GetMapping("/admin/grantAuthority")
    public String grantAuthorityForm(){
        
        return "/admin/grantAuthorityForm";
    }
    
    @PostMapping("/admin/grantAuthority")
    public String grantAdminAuthority(@RequestParam String adminCode){
        
        // adminCode 유효성 확인
        if(!adminCode.equals("admin")){
            return "redirect:/admin/grantAuthority?error=true&type=adminCode";
        }
        
        // 해당 계정에 ADMIN 권한 추가
        adminService.grantAdminAuthority(rq.getUser());
        
        
        return "redirect:/";
    }
    
    @GetMapping("/admin/revokeAuthority")
    public String revokeAdminAuthority(){
        
        // 해당 계정의 관리자 권한 철회, 박탈
        adminService.revokeAdminAuthority(rq.getUser());
        
        return "redirect:/";
    }
    
}
