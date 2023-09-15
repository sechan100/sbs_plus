package org.sbsplus.domain.admin;


import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.user.dto.UserDto;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.domain.user.service.UserService;
import org.sbsplus.util.Rq;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminController {
    
    
    private final AdminService adminService;
    private final UserService userService;
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
    @PostMapping("/admin/suspendUser")
    public String suspendUser(@RequestParam Long userId) {
        // 관리자 역할을 가지고 있는지 확인
        if (!adminService.isAdmin()) {
            throw new AccessDeniedException("관리자 권한이 필요합니다.");
        }

        // 사용자 정지 로직 구현
        User userToSuspend = userService.findById(userId);
        userToSuspend.setSuspended(true); // 사용자를 정지 상태로 변경

//        userService.save(userToSuspend); // 변경된 정보 저장

        return "redirect:/admin/users"; // 사용자 목록 페이지로 리다이렉트
    }
}
