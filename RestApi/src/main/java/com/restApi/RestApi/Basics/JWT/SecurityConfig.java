package com.restApi.RestApi.Basics.JWT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // CSRF-Schutz deaktiviert, um Probleme mit der H2-Konsole zu vermeiden
            .headers().frameOptions().disable() // Frame-Optionen deaktiviert, damit die H2-Konsole angezeigt wird
            .and()
            .authorizeHttpRequests()
                .requestMatchers("/h2-console/**").permitAll() // Zugriff auf die H2-Konsole erlauben
                .requestMatchers("/api/**").permitAll() //
                .requestMatchers("/api/users/**").permitAll()
               .requestMatchers("/api/categories/**").permitAll()
                .anyRequest().authenticated() // Authentifizierung für andere Anfragen
            .and()
            .formLogin().permitAll(); // Standard-Login-Seite erlauben

        return http.build();
    }
}
