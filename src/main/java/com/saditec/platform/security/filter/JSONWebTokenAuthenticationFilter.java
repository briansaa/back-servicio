package com.saditec.platform.security.filter;

import com.saditec.platform.security.jwt.JWTService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JSONWebTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nonnull HttpServletResponse servletResponse,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(jwtService.getPrefix())) {
            filterChain.doFilter(request, servletResponse);
            return;
        }

        try {
            authHeader = authHeader.substring(7);
            boolean isValidToken = jwtService.isValidToken(authHeader);

            if (!isValidToken) filterChain.doFilter(request, servletResponse);

            String subject = jwtService.getSubject(authHeader);
            List<?> authorities = jwtService.getAuthorities(authHeader);
            Collection<? extends GrantedAuthority> authoritiesCollection = authorities.stream().map(a -> new SimpleGrantedAuthority(a.toString())).toList();
            Authentication authentication = new UsernamePasswordAuthenticationToken(subject, null, authoritiesCollection);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            logger.error("Error validating token", e);
        }

        filterChain.doFilter(request, servletResponse);
    }
}
