package org.sbsplus.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class EtcConfig { // 메모리 내 관리자 계정 생성, 해당 계정의 비밀번호를 안전하게 암호화.
    
    @Bean
    public UserDetailsService inMemoryAdminDetailsService(){ //사용자 정보 관리 서비스 생성 빈. 메모리에 사용자 정보 저장 및 관리
        User admin = (User) User.builder()
                .username("admin") // 사용자 이름 admin으로 설정
                .password(passwordEncoder().encode("admin")) // 비밀번호 admin 설정 후 암호화
                .roles("ADMIN") // ADMIN역할 부여
                .build();
        
        return new InMemoryUserDetailsManager(admin);
        //사용자 정보를 메모리에 저장 및 관리하는 UserDetailsService 반환
    }
    
    
    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
