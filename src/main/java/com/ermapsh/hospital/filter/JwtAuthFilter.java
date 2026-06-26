package com.ermapsh.hospital.filter;

import com.ermapsh.hospital.entity.User;
import com.ermapsh.hospital.service.JwtService;
import com.ermapsh.hospital.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Filter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")){
                filterChain.doFilter(request, response);
                return;
        }

        String token = requestTokenHeader.split("Bearer")[1];

        Long userId = jwtService.getUserIdFromJwtToken(token);

        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null ){
            User user = userService.getUserById(userId);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, null);
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);

//        with response
    }
}
