package com.mayckgomes.dateplan_api.configurations;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    SecurityFilter securityFilter;
    Dotenv dotenv = Dotenv.load();

    public SecurityConfigurations(SecurityFilter securityFilter){
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){

        return http
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/auth/autoLogin").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new Argon2PasswordEncoder(
                Integer.parseInt(dotenv.get("SALT_LENGTH")),
                Integer.parseInt(dotenv.get("HASH_LENGTH")),
                Integer.parseInt(dotenv.get("PARALLELISM")),
                Integer.parseInt(dotenv.get("MEMORY")),
                Integer.parseInt(dotenv.get("ITERATIONS")));
    }

}

