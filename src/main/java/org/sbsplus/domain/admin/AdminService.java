package org.sbsplus.domain.admin;


import org.sbsplus.domain.user.dto.UserDto;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public interface AdminService {

    void deleteUser(String username);
    void suspendUser(Long userId);
    void activateUser(Long userId);
    boolean isAdmin();
    void grantAdminAuthority(User user);
    
    void revokeAdminAuthority(User user);
}
