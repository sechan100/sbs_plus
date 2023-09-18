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

    @GetMapping("/admin/suspend")
    @ResponseBody
    public String suspendUser(@RequestParam Long userId) {
        // 관리자 역할을 가지고 있는지 확인
        if (!adminService.isAdmin()) {
            throw new AccessDeniedException("관리자 권한이 필요합니다.");
        }

        // 사용자 정지 로직 구현
        UserDto userToSuspendDto = userService.getUserDtoById(userId);
        userToSuspendDto.setSuspended(true); // 사용자를 정지 상태로 변경

        userService.save(userToSuspendDto); // 변경된 정보 저장

        return """
                <a href="" th:hx-get="@{/admin/active(userId = ${user.id})}" hx-boost="true">계정 활성화</a>
                """;
    }
    @GetMapping("/admin/active")
    @ResponseBody
    public String activateUser(@RequestParam Long userId) {
        // 관리자 역할을 가지고 있는지 확인
        if (!adminService.isAdmin()) {
            throw new AccessDeniedException("관리자 권한이 필요합니다.");
        }

        // 사용자 활성화 로직 구현
        UserDto userToActivateDto = userService.getUserDtoById(userId);

        // 사용자를 활성 상태로 변경
        userToActivateDto.setSuspended(false);

        userService.save(userToActivateDto); // 변경된 정보 저장

        return """
                <a href="" th:hx-get="@{/admin/suspend(userId = ${user.id})}" hx-boost="true">계정 정지</a>
                """;
    }
}
