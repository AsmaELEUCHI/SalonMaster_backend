package com.salonMaster_springBoot.salonMaster.controller;

import com.salonMaster_springBoot.salonMaster.config.JwtAuthenticationFilter;
import com.salonMaster_springBoot.salonMaster.dto.*;
import com.salonMaster_springBoot.salonMaster.entity.Salon;
import com.salonMaster_springBoot.salonMaster.exception.GeneralBusinessException;
import com.salonMaster_springBoot.salonMaster.repository.SalonRepository;
import com.salonMaster_springBoot.salonMaster.service.UserService;
import com.salonMaster_springBoot.salonMaster.service.XssSanitizerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private XssSanitizerService xssSanitizerService;
    @Autowired
    private SalonRepository salonRepository;
    @Value("${frontend.url}")
    private String frontendUrl;

    // Endpoint pour l'inscription d'un nouvel utilisateur
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUserController(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        // Nettoyage des entrées utilisateur
        userRegisterDto.setSalonName(xssSanitizerService.sanitize(userRegisterDto.getSalonName()));
        userRegisterDto
                .setRepresentativeFirstName(xssSanitizerService.sanitize(userRegisterDto.getRepresentativeFirstName()));
        userRegisterDto
                .setRepresentativeLastName(xssSanitizerService.sanitize(userRegisterDto.getRepresentativeLastName()));
        userRegisterDto.setSalonAddress(xssSanitizerService.sanitize(userRegisterDto.getSalonAddress()));
        userRegisterDto.setEmail(userRegisterDto.getEmail());
        userRegisterDto.setPassword(xssSanitizerService.sanitize(userRegisterDto.getPassword()));
        userRegisterDto.setConfirmPassword(xssSanitizerService.sanitize(userRegisterDto.getConfirmPassword()));
        userService.registerUser(userRegisterDto);
        AuthResponse response = new AuthResponse(null,
                "Merci pour votre inscription, veuillez vérifier votre boîte mail pour confirmer votre inscription.");

        return ResponseEntity.ok(response);
    }

    // Endpoint pour confirmer l'inscription d'un utilisateur via un token.
    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUserController(@RequestParam("token") String token) {
        try {
            userService.confirmUser(token);
            String loginUrl = frontendUrl + "/login";
            String message = "Votre compte a été confirmé avec succès. Vous pouvez maintenant vous connecter à l'adresse suivante : <a href=\""
                    + loginUrl + "\">Se connecter</a>.";
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            // Log and handle the exception as needed
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la confirmation.");
        }
    }

    // Endpoint pour connecter un utilisateur.
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUserController(@RequestBody UserLoginDto userLoginDto) {
        // Nettoyage des entrées utilisateur
        userLoginDto.setEmail(userLoginDto.getEmail());
        userLoginDto.setPassword(xssSanitizerService.sanitize(userLoginDto.getPassword()));
        UserLoginDto loginResponse = userService.loginUser(userLoginDto);
        System.out.println("Response of /login, Email: " + loginResponse.getEmail());
        System.out.println("Response of /login,Token: " + loginResponse.getToken());
        AuthResponse response = new AuthResponse();

        response.setMessage("Vous êtes connecté(e)");
        response.setToken(loginResponse.getToken());

        return ResponseEntity.ok()
                .body(response);

    }

    // Endpoint qui permet la vérification du token et afficher la page
    // personal-space
    @PostMapping("/personalSpace")
    public ResponseEntity<AuthResponse> getPersonalSpace() {
        // Récupérer l'objet Authentication depuis le contexte de sécurité
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthResponse response = new AuthResponse();
        // Afficher l'objet Authentication pour le débogage
        System.out.println("Authentication: " + authentication);

        // Vérifier si l'utilisateur est authentifié
        if (authentication != null && authentication.isAuthenticated()) {
            // Afficher le nom de l'utilisateur pour le débogage
            System.out.println("Response of /personalSpace, User authenticated: " + authentication.getName());
            response.setMessage("Accès à l'espace personnel autorisé.");
            response.setToken(authentication.getName());
            return ResponseEntity.ok(response);
        } else {
            // Afficher un message d'erreur pour le débogage
            System.out.println("Response of /personalSpace, Authentication failed or user not authenticated.");
            // Retourner une réponse d'erreur avec statut 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // Endpoint pour mettre à jour le profil d'un utilisateur.
    @PostMapping("/profile")
    public ResponseEntity<UpdateResponse> updateProfileController(@RequestBody UserProfileDto userProfileDto,
            Authentication authentication) {

        userProfileDto.setSalonName(xssSanitizerService.sanitize(userProfileDto.getSalonName()));
        userProfileDto.setSalonAddress(xssSanitizerService.sanitize(userProfileDto.getSalonAddress()));
        userProfileDto.setOpeningDate(userProfileDto.getOpeningDate());
        userProfileDto.setNumberOfEmployees(userProfileDto.getNumberOfEmployees());
        userProfileDto.setRegion(xssSanitizerService.sanitize(userProfileDto.getRegion()));
        userProfileDto.setDepartement(xssSanitizerService.sanitize(userProfileDto.getDepartement()));

        String email = authentication.getName();
        UserProfileDto updatedSalon = userService.updateProfile(email, userProfileDto);
        UpdateResponse response = new UpdateResponse();
        response.setMessage("Profil mis à jour avec succès.");
        response.setSalon(updatedSalon);
        return ResponseEntity.ok(response);

    }

    // endpoint pour affichage des informations de profile
    @GetMapping("/profile")
    public ResponseEntity<UpdateResponse> getProfileController(Authentication authentication) {
        String email = authentication.getName();
        Salon salon = salonRepository.findByUserEmail(email)
                .orElseThrow(() -> new GeneralBusinessException("Salon non trouvé."));
        UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setSalonName(salon.getName());
        userProfileDto.setSalonAddress(salon.getAddress());
        userProfileDto.setOpeningDate(salon.getOpeningDate());
        userProfileDto.setNumberOfEmployees(salon.getEmployeeCount());
        userProfileDto.setRegion(salon.getRegion());
        userProfileDto.setDepartement(salon.getDepartement());
        UpdateResponse response = new UpdateResponse();
        response.setMessage("Profil récupéré avec succès.");
        response.setSalon(userProfileDto);
        return ResponseEntity.ok(response);
    }

    // Endpoint pour demande de réinitialiser le mot de passe d'un utilisateur.
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPasswordController(@RequestBody PasswordResetDto passwordResetDto) {
        userService.resetPassword(passwordResetDto);
        return ResponseEntity.ok("Veuillez vérifier votre boîte mail pour réinitialiser votre mot de passe.");
    }

    @PostMapping("/update-password")
    public ResponseEntity<MessageResponse> updatePasswordController(@RequestParam("token") String token,
            @RequestBody PasswordUpdateDto passwordUpdateDto) {
        System.out.println("Received token: " + token);
        System.out.println("Received new password: " + passwordUpdateDto.getNewPassword());
        String sanitizedPassword = xssSanitizerService.sanitize(passwordUpdateDto.getNewPassword());
        passwordUpdateDto.setNewPassword(sanitizedPassword);
        userService.updatePassword(token, passwordUpdateDto);
        return ResponseEntity.ok(new MessageResponse("Mot de passe mis à jour avec succès."));
    }

}
