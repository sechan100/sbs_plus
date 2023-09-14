package org.sbsplus.domain.admin;


import org.sbsplus.domain.user.entity.User;

public interface AdminService {
    boolean isAdmin();
    void grantAdminAuthority(User user);
    
    void revokeAdminAuthority(User user);
}
