package com.skidata.codingtest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for setting up basic HTTP authentication.
 * 
 * This configuration ensures that all requests are authenticated using HTTP Basic Authentication.
 * It also disables CSRF protection for simplicity, but this may not be suitable for production environments.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
//This class was implemented to add basic HTTP-Auth to the calls
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	// Authorize all requests to be authenticated
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .anyRequest().authenticated()
            )
            // Enable HTTP Basic Authentication
            .httpBasic(httpBasic -> {} )  
            .csrf(csrf -> csrf.disable());  

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
             UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}