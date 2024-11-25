// package com.restApi.RestApi.Basics.JWT;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;

// @Component
// public class JWTAuthenticationFilter extends OncePerRequestFilter {

//     @Autowired
//     private JWTUtility jwtUtility;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {

//         String authHeader = request.getHeader("Authorization");

//         if (authHeader != null && authHeader.startsWith("Bearer ")) {
//             String token = authHeader.substring(7); // Entferne "Bearer "
//             try {
//                 String userId = jwtUtility.validateTokenAndGetUserId(token);
//                 request.setAttribute("userId", userId); // Benutzer-ID für weitere Verarbeitung speichern
//             } catch (RuntimeException e) {
//                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                 return;
//             }
//         }

//         filterChain.doFilter(request, response); // Nächste Filterkette
//     }
// }
