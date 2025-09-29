package br.com.barber.system.web.config;

import br.com.barber.system.web.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private static final List<String> PUBLIC_URLS = List.of(
            "/auth/login"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Rotas públicas
        if (PUBLIC_URLS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Token ausente → 401
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token ausente");
            return;
        }

        String token = authHeader.substring(7);

        try {
            String username = JwtUtil.validateToken(token);
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response); // token válido → segue
        }
        catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado");
        }
    }
}