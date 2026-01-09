package com.example.web.config;
import com.example.web.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//    private final JwtAuthFilter jwtAuthFilter;
//    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
//        this.jwtAuthFilter = jwtAuthFilter;
//    } //causeing cyclic dependency

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers("/auth/**").permitAll() //public endpoints
                                        .anyRequest().authenticated()//rest all endpoints authenticated
                )
                .csrf(csrf -> csrf.disable())

                //With JWT we can enable STATELESS authentication -> no state preserved in session storage for subsequent requests
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Before reaching servlet or processing of request pass through this gatekeeper/filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

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
