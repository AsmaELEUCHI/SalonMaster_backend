package com.salonMaster_springBoot.salonMaster.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ConfirmationTokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    public ConfirmationTokenFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Extraire le token de confirmation du paramètre de l'URL
        String token = request.getParameter("token");
        if (token != null && !token.isEmpty()) {
            try {
                String email = tokenProvider.getEmailFromToken(token);
                if (email != null) {
                    // Logique de traitement si nécessaire
                    // Par exemple, vous pouvez configurer le contexte de sécurité si la confirmation est effectuée
                    System.out.println("Extracted Token for Confirmation: " + token);
                    System.out.println("Extracted Email for Confirmation: " + email);
                }
            } catch (Exception e) {
                // Gérer les erreurs de token si nécessaire
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Confirmation Token");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
