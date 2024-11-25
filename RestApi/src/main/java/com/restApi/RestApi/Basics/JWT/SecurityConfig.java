// package com.restApi.RestApi.Basics.JWT;

// import java.util.List;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// @Configuration
// public class SecurityConfig {

//    @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//     http.cors().and() // CORS-Konfiguration aktivieren
//         .csrf().disable()
//         .authorizeHttpRequests()
//         .requestMatchers("/api/**").permitAll()
//         .anyRequest().authenticated()
//         .and()
//         .addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//     return http.build();
// }

// @Bean
// public CorsConfigurationSource corsConfigurationSource() {
//     CorsConfiguration configuration = new CorsConfiguration();
//     configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//     configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//     configuration.setAllowedHeaders(List.of("*"));
//     configuration.setAllowCredentials(true);
//     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//     source.registerCorsConfiguration("/**", configuration);
//     return source;
// }
// }

//"/h2-console/**","/api/**","/api/users/**", "/api/categories/**",
                //"/api/users/login", "/api/users/register"
