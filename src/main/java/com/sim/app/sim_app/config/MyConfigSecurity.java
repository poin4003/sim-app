package com.sim.app.sim_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.sim.app.sim_app.features.user.v1.service.impl.UserDetailServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class MyConfigSecurity {

    private final UserDetailServiceImpl userDetailService;

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // .authorizeHttpRequests(authorizeRequest -> authorizeRequest
            //     .requestMatchers("/admin/**").hasRole("ADMIN")
            //     .anyRequest().authenticated()
            // )
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()
            ) 
            // .formLogin(form -> form
            //     .defaultSuccessUrl("/user/info", true)
            // ) 
            // .userDetailsService(userDetailService);
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
            .userDetailsService(userDetailService)
            .passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }
}
