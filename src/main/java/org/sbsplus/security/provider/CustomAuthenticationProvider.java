package org.sbsplus.security.provider;

import org.sbsplus.user.entity.User;
import org.sbsplus.security.principal.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
// 사용자 인증 처리하기 위한 커스텀 인증 프로바이더. 사용자 지정 인증 로직 제공
    @Autowired
    @Qualifier("userDetailsService") // 여러개의 userDetailsService 빈 중에서 어떤 빈을 주입할지를 지정하기 위해 사용
    // 사용자 정보를 로드하기 위한 userDetailsService 를 주입
    private UserDetailsService userDetailsService;
    
    @Autowired
    @Qualifier("inMemoryAdminDetailsService")
    // 인메모리 사용자 정보를 로드하기 위한 userDetailsService 주입
    private UserDetailsService inMemoryAdminDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // 사용자 인증을 시도할 때 호출. authentication : 인증에 필요한 정보를 담은 객체
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        
        if(username.equals("admin")){
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(passwordEncoder);
            // 인메모리 관리자(관리자 권한을 가진 사용자)의 인증을 다른 방식으로 처리
            daoAuthenticationProvider.setUserDetailsService(inMemoryAdminDetailsService);
            // DaoAuthenticationProvider 사용하여 inMemoryAdminDetailsService를 이용, 관리자 인증 수행하고 결과 반환
            return daoAuthenticationProvider.authenticate(authentication);
        }
        // 그렇지 않으면 ('else') userDetailsService 를 사용하여 사용자 정보를 로드, 해당 사용자의
        // 비밀번호와 입력된 비밀번호 비교하여 인증 수행
        
        

        UserContext userContext = (UserContext) userDetailsService.loadUserByUsername(username);
        User user = userContext.getUser();

        // password matche
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("BadCredentialsException");
        }


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        user, null, userContext.getAuthorities()
                );

        return authenticationToken;
    }



    // 해당 provider가 지원 가능한 Authentication 구현체인지의 여부
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
// 사용자의 인증을 처리. 특히 관리자와 사용자를 구분하여 다른 방식으로 인증을 수행.