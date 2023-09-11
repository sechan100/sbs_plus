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
                        
                        // 메인, 접근거부, 유효하지 않은 요청, error(이거 빼면 이상해짐..)
                        .requestMatchers("/", "/access_denied", "/unexpected_request", "/error*").permitAll()

                        // 회원가입, 로그인
                        .requestMatchers("/register*", "/login*").permitAll()

                        // 커뮤니티
                        .requestMatchers("/article/write").authenticated()
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





























