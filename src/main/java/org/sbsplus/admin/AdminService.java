package org.sbsplus.admin;


import org.sbsplus.user.entity.User;

public interface AdminService {
    boolean isAdmin();
    void grantAdminAuthority(User user);
    
    void revokeAdminAuthority(User user);
}
