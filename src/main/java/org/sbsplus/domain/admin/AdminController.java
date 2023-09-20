package org.sbsplus.domain.admin;


import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.qna.entity.Question;
import org.sbsplus.domain.qna.form.AnswerForm;
import org.sbsplus.domain.user.dto.UserDto;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.domain.user.service.UserService;
import org.sbsplus.util.Rq;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminController {
    
    private final AdminService adminService;
    private final UserService userService;
    private final Rq rq;

    @GetMapping("/admin")
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
        Page<User> paging = this.userService.getList(page);
        model.addAttribute("paging", paging);
        return "/admin/users";
    }

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
    @GetMapping("/suspend/{userId}")
    public String suspendUser(@PathVariable Long userId) {
        adminService.suspendUser(userId);
        return "redirect:/admin"; // 사용자 목록 페이지로 리다이렉트
    }

    @GetMapping("/activate/{userId}")
    public String activateUser(@PathVariable Long userId) {
        adminService.activateUser(userId);
        return "redirect:/admin"; // 사용자 목록 페이지로 리다이렉트
    }
    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable String username) {
        adminService.deleteUser(username);
        return "redirect:/admin";
    }
}
