package com.salonMaster_springBoot.salonMaster.config;

import com.salonMaster_springBoot.salonMaster.service.UserDetailServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
// Filtre pour gérer l'authentification basée sur JWT
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserDetailServiceImp userDetailServiceImp;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Extraire le token JWT de l'en-tête Authorization
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // Vérifiez si l'en-tête Authorization est présent et commence par "Bearer"
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Enlevez "Bearer " du début
            System.out.println("Extracted Token from Header: " + token);
            email = tokenProvider.getEmailFromToken(token);
            System.out.println("Extracted Email: " + email);
        }
        UserDetails userDetails = null;
        // si l'email n'est pas null et aucune authentification n'est en cours dans le contexte actuel
        if (email != null && SecurityContextHolder.getContext().getAuthentication()==null){
             userDetails = userDetailServiceImp.loadUserByUsername(email);
            System.out.println("Loaded UserDetails: " + userDetails);
        }
        //Si le token est valide et que les détails de l'utilisateur sont chargés
        if(token != null && tokenProvider.validateToken(token) && userDetails != null){
            // Création d'un objet d'authentification pour l'utilisateur courant.
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            // Ajout de détails supplémentaires concernant la requête (comme l'adresse IP) à l'objet d'authentification.
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // Mise à jour du contexte de sécurité avec l'objet d'authentification.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("User authenticated: " + userDetails.getUsername());
        }else {
            System.out.println("Token is invalid or userDetails is null");
        }

        // Poursuite de la chaîne de filtres (ou de la requête) après le filtrage.
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println("Exception in JwtAuthenticationFilter: " + e.getMessage());
            throw e; // Rethrow if necessary
        }



    }

}
