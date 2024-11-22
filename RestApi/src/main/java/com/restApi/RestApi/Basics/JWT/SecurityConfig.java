package com.restApi.RestApi.Basics.JWT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // CSRF-Schutz deaktiviert, um Probleme mit der H2-Konsole zu vermeiden
            .headers().frameOptions().disable() // Frame-Optionen deaktiviert, damit die H2-Konsole angezeigt wird
            .and()
            .authorizeHttpRequests()
                .requestMatchers("/h2-console/**","/api/**","/api/users/**", "/api/categories/**",
                "/api/users/login", "/api/users/register").permitAll() // Zugriff erlauben
                .anyRequest().authenticated() // Authentifizierung für andere Anfragen
            .and()
            .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
}
