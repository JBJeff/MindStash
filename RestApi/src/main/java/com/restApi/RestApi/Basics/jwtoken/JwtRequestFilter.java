// package com.restApi.RestApi.Basics.jwtoken;

// import java.io.IOException;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class JwtRequestFilter extends OncePerRequestFilter {

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {

//         String path = request.getRequestURI();
//         if (path.startsWith("/api/users/register") || path.startsWith("/authenticate")) {
//             // Keine Authentifizierung für diese Endpunkte erforderlich
//             filterChain.doFilter(request, response);
//             return;
//         }

//         // Normaler JWT-Filterprozess
//         String authorizationHeader = request.getHeader("Authorization");
//         String token = null;
//         String username = null;

//         if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//             token = authorizationHeader.substring(7);
//             username = jwtTokenUtil.extractUsername(token);
//         }

//         if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//             UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//             if (jwtTokenUtil.validateToken(token, userDetails)) {
//                 UsernamePasswordAuthenticationToken authToken =
//                         new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 SecurityContextHolder.getContext().setAuthentication(authToken);
//             }
//         }
//         filterChain.doFilter(request, response);
//     }
// }
