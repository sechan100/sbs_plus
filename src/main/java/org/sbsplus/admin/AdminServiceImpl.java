package org.sbsplus.admin;


import lombok.RequiredArgsConstructor;
import org.sbsplus.util.Rq;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {
    
    @Override
    public boolean isAdmin() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Set<? extends GrantedAuthority> grantedAuthorities = (Set<? extends GrantedAuthority>) authentication.getAuthorities();
        for(GrantedAuthority grantedAuthority: grantedAuthorities){
            
            // admin 권한 확인
            if(grantedAuthority.getAuthority().equals("ADMIN")){
                return true;
            }
        }
        
        return false;
    }
}
