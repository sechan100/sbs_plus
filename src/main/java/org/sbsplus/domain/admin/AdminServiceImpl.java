package org.sbsplus.domain.admin;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.user.dto.UserDto;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.domain.user.service.UserService;
import org.sbsplus.general.security.principal.UserContext;
import org.sbsplus.util.Rq;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    // Spring Security와 사용자 권한 관리를 다루는 서비스 클래스
    
    private final Rq rq;
    private final UserService userService;

    @Transactional
    public void suspendUser(Long userId) {
        // 관리자 역할을 가지고 있는지 확인
        if (!isAdmin()) {
            throw new AccessDeniedException("관리자 권한이 필요합니다.");
        }

        // 사용자를 정지 상태로 변경
        User user = userService.findById(userId);
        user.setSuspended(true);
    }

    public void activateUser(Long userId) {
        // 관리자 역할을 가지고 있는지 확인
        if (!isAdmin()) {
            throw new AccessDeniedException("관리자 권한이 필요합니다.");
        }

        // 사용자를 활성 상태로 변경
        UserDto userDto = userService.getUserDtoById(userId);
        userDto.setSuspended(false);
        userService.save(userDto);
    }
    public void deleteUser(Long userId) {
        // 관리자 역할을 가지고 있는지 확인
        if (!isAdmin()) {
            throw new AccessDeniedException("관리자 권한이 필요합니다.");
        }

        // 사용자를 활성 상태로 변경
        UserDto userDto = userService.getUserDtoById(userId);
        userService.delete(userDto);
    }

    @Override
    public boolean isAdmin() {
        return rq.isAdmin();
    }
    //현재 사용자가 권한을 가지고 있는지
    @Override
    public void grantAdminAuthority(User user) { //특정사용자에게 권한 부여
        
        // 새로운 GrantedAuthority List 생성
        List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            
        UserContext userContext = new UserContext(user, authorities);
        
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, userContext.getAuthorities());
        //UsernamePasswordAuthenticationToken을 사용하여 사용자 인증, 해당 권한 정보와 함께

        
        // authentication 토큰 갱신
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //'SecurityContextHolder'에 설정. 사용자의 인증 정보 갱신
    }
    
    @Override
    public void revokeAdminAuthority(User user) { //특정 사용자 관리자 권한 취소 메서드
        // 새로운 GrantedAuthority List 생성
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        //사용자에게 Role_user 역할만 부여하여 일반사용자로 변경
        UserContext userContext = new UserContext(user, authorities);
        
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, userContext.getAuthorities());
        
        
        // authentication 토큰 갱신
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
// Spring Security를 사용하여 사용자 권한 관리, 사용자 권한 부여 및 취소. 이 서비스를 호출하여
// 사용자의 역할 및 권한을 동적으로 변경. Spring Security의 인증 및 권한 관리에 유용

















