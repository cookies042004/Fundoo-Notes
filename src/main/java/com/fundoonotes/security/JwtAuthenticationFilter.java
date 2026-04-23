package com.fundoonotes.security;

import com.fundoonotes.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    //   UC6:Intercepts exactly ONE time per request to ensure auth
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");

        //   UC6:Verify header structure starts with Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        try {
            //   UC6:Extract claims. Throws exception if tampered or expired.
            Claims claims = jwtUtil.extractClaims(token);
            String email = claims.getSubject();
            
            //   UC6:If token is valid and context is empty, authorize them in Spring Context
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                        
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                //   UC6:Tell Spring Security "They are legit, let them pass!"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            //   UC6:Token is invalid or expired
            logger.error("Cannot set user authentication: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
