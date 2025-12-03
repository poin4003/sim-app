package com.sim.app.sim_app.config.jwt;

import java.io.IOException;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sim.app.sim_app.config.jwt.dto.JwtPayload;
import com.sim.app.sim_app.features.auth.entity.KeyStoreEntity;
import com.sim.app.sim_app.features.auth.repository.KeyStoreRepository;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;
    private final KeyStoreRepository keyStoreRepository;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = getJwtFromRequest(request);

            if (token != null) {
                UUID userId = jwtTokenProvider.getUserIdFromTokenUnverified(token);
                if (userId != null) {
                    KeyStoreEntity keyStore = keyStoreRepository.selectOne(
                        new LambdaQueryWrapper<KeyStoreEntity>()
                            .eq(KeyStoreEntity::getUserId, userId)
                    );

                    if (keyStore != null)  {
                        Claims claims = jwtTokenProvider.getAllClaimsFromToken(token, keyStore.getPublicKey());

                        JwtPayload payload = objectMapper.convertValue(claims, JwtPayload.class);

                        UserDetails userDetails = userDetailsService.loadUserByUsername(payload.getUserEmail());

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                        );

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }
    
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
