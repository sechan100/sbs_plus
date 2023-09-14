package org.sbsplus.domain.admin;


import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.general.security.principal.UserContext;
import org.sbsplus.util.Rq;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    
    private final Rq rq;
    
    @Override
    public boolean isAdmin() {
        return rq.isAdmin();
    }
    
    @Override
    public void grantAdminAuthority(User user) {
        
        // 새로운 GrantedAuthority List 생성
        List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            
        UserContext userContext = new UserContext(user, authorities);
        
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, userContext.getAuthorities());
        
        
        // authentication 토큰 갱신
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        
    }
    
    @Override
    public void revokeAdminAuthority(User user) {
        // 새로운 GrantedAuthority List 생성
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        UserContext userContext = new UserContext(user, authorities);
        
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, userContext.getAuthorities());
        
        
        // authentication 토큰 갱신
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}


















