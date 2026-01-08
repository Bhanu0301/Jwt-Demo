package com.example.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers("/auth/**").permitAll() //public endpoints
                                        .anyRequest().authenticated()//rest all endpoints authenticated
                )
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    //uses BCrypt hashing
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //AuthenticationProvider to link UserDetailsService of SpringSecurity with PasswordEncoder
    //Uncomment if Spring version is 3, for 3+ AuthenticationProvider is automatically contained
    //    @Bean
    //    public AuthenticationProvider authenticationProvider(){
    //        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    //        provider.setUserDetailsService(userDetailsService);
    //        provider.setPasswordEncoder(passwordEncoder());
    //        return provider;
    //
    //    }

    //Manager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
