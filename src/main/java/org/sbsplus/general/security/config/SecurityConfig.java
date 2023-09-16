package org.sbsplus.general.security.config;


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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
                        
                        // 메인, 접근거부, 유효하지 않은 요청, error
                        .requestMatchers("/", "/accessDenied*", "/unexpected_request*", "/error*", "/upload*", "/uploads/**").permitAll()

                        // 회원가입, 로그인
                        .requestMatchers("/register*", "/login*").anonymous()
                        
                        // 이메일
                        .requestMatchers("/email/**", "/admin/grantAuthority").permitAll()
                        
                        // 어드민 원한 부여
                        .requestMatchers("/admin/grantAuthority").authenticated()
                        
                        // 커뮤니티
                        .requestMatchers("/article/write", "/comment/write*", "/article/like*", "/comment/like*").hasRole("USER")
                        .requestMatchers("/article/**", "/article*").permitAll()

                        // QnA
                        .requestMatchers("/question/**").permitAll()

                        // admin 제한
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", false)
                        .failureHandler(loginFailureHandler)
                        .permitAll()
                )
                
                .csrf(AbstractHttpConfigurer::disable)

                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(defaultAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                );



        return http.build();
    }



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring().requestMatchers("/css/**", "/js/**");
    }
}





























