package org.sbsplus.security.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider customAuthenticationProvider;
    private final AccessDeniedHandler defaultAccessDeniedHandler;
    private final AuthenticationFailureHandler loginFailureHandler;
    private final AuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider);
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        // 메인 페이지, 회원가입, 로그인, QnA, 권한 없음 페이지 permit all
                        .requestMatchers("/", "/register*", "/login*", "/access_denied", "/question/**").permitAll()
                        //위 경로에 대한 접근은 모두 허용
                        .requestMatchers("/admin/**").hasRole("ADMIN") //admin역할을 가진 사용자만 허용
                        .anyRequest().authenticated() // 그 외의 모든 요청은 인증된 사용자만 접근
                )

                .formLogin(form -> form
                        .loginPage("/login") //사용자 정의 로그인페이지 설정
                        .loginProcessingUrl("/login") //로그인처리url지정
                        .defaultSuccessUrl("/", false) //로그인 성공시 메인, 실패시 loginFailuerHandler로 실패 처리
                        .failureHandler(loginFailureHandler)
                        .permitAll()
                )

                .exceptionHandling(exception -> exception //예외처리 구성
                        .accessDeniedHandler(defaultAccessDeniedHandler) //접근 거부 핸들러
                        .authenticationEntryPoint(customAuthenticationEntryPoint) //사용자 정의 인증 진입점 설정
                );



        return http.build();
    }



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring().requestMatchers(String.valueOf(PathRequest.toStaticResources().atCommonLocations()));
    }
}
