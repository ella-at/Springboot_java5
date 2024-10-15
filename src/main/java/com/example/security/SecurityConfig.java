package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/entrar", "/api/usuarios/cadastro").permitAll()
                        .requestMatchers("/brinquedos/lista", "/brinquedos/detail/**").hasRole("USER")
                        .requestMatchers("/brinquedos/create", "/brinquedos/editar/**", "/brinquedos/delete/**", "/brinquedos/venda/**", "/brinquedos/relatorios/**", "/index/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/entrar")
                        .defaultSuccessUrl("/index", true)
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
}
