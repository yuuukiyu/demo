package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ DBからユーザーを認証する設定（ここが重要！）
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/","/style.css", "/style-*.css", "/css/**", "/login", "/register", "/register/save").permitAll()
                .requestMatchers("/todo").authenticated()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers
            	    .frameOptions().sameOrigin()
            	    .contentSecurityPolicy(csp -> csp
            	        .policyDirectives("default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; font-src 'self'")
            	    )
            	)
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/todo", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}
