package org.sbsplus.security.service;

import lombok.RequiredArgsConstructor;
import org.sbsplus.user.entity.User;
import org.sbsplus.security.principal.UserContext;
import org.sbsplus.user.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("userDetailsService") // userDetailsService 라는 이름으로 빈을 등록
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
// 사용자 정보를 데이터베이스에서 가져오고, 해당 정보를 security의 userDetails 타입으로 변환하여 제공
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 사용자 정보 로드.
        User user = userRepository.findByUsername(username);

        // username not found check
        if(user == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        List<GrantedAuthority> roles = new ArrayList<>(); // 사용자의 권한정보를 저장하기 위한 리스트 생성
        roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        // 사용자 역할정보를 'grantedAuthority' 형태로 변환 후 리스트 추가.
        // ROLE_는 사용자 역할을 접두사로 표현.
        UserContext userContext = new UserContext(user, roles);
        // 사용자 정보('User' 객체) 와 권한('GrantedAuthority' 리스트)를 사용. 'UserContext'객체 생성
        // 사용자 정보와 권한 정보 함께 담든 클래스
        return userContext;
    }
}
// 사용자 이름을 사용. 데이터베이스에서 사용자 정보 조회하고, 조회한 정보를 security의 'UserDetails'타입으로 변환하여 반환.
// Spring Security에서 사용자 인증과 권한 관리에 필요한 정보를 제공
















