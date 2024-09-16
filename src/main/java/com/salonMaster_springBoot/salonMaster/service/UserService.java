package com.salonMaster_springBoot.salonMaster.service;

import com.salonMaster_springBoot.salonMaster.dto.*;
import com.salonMaster_springBoot.salonMaster.entity.Salon;
import com.salonMaster_springBoot.salonMaster.entity.User;
import com.salonMaster_springBoot.salonMaster.exception.EmailAlreadyExistsException;
import com.salonMaster_springBoot.salonMaster.exception.GeneralBusinessException;
import com.salonMaster_springBoot.salonMaster.exception.UserNotFoundException;
import com.salonMaster_springBoot.salonMaster.repository.SalonRepository;
import com.salonMaster_springBoot.salonMaster.repository.UserRepository;
import com.salonMaster_springBoot.salonMaster.config.TokenProvider;
import jakarta.mail.MessagingException;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Injections des dépendances
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SalonRepository salonRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenprovider;

    // Méthode pour l'inscription d'un nouvel utilisateur
    public void registerUser(UserRegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
        // Crée un nouvel utilisateur avec les informations fournies
        User user = new User();
        user.setRepresentativeFirstName(registerDto.getRepresentativeFirstName());
        user.setRepresentativeLastName(registerDto.getRepresentativeLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEnabled(false);
        userRepository.save(user);
        // Crée le salon avec les informations fournies ce sont les données saisies au
        // niveau du formulaire inscription
        Salon salon = new Salon();
        salon.setName(registerDto.getSalonName());
        salon.setAddress(registerDto.getSalonAddress());
        salon.setRegion(null);
        salon.setDepartement(null);
        salon.setUser(user);
        salonRepository.save(salon);

        // Crée un token pour la confirmation d'inscription
        String token = tokenprovider.generateToken(user.getEmail());
        try {
            emailService.sendRegistrationConfirmation(user.getEmail(), token);
        } catch (MessagingException e) { // l'exception spécifique est capturée
            throw new GeneralBusinessException("Erreur lors de l'envoi de l'email de confirmation.");

        }

    }

    // Méthode pour confirmer l'inscription de l'utilisateur via le token
    public void confirmUser(String token) {
        try {
            String email = tokenprovider.getEmailFromToken(token);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        System.out.println("User not found for email: " + email);
                        return new UserNotFoundException();
                    });

            System.out.println("User found: " + user);
            user.setEnabled(true);
            userRepository.save(user);
            System.out.println("User account enabled and saved.");
        } catch (Exception e) {
            // Log and handle the exception as needed
            e.printStackTrace();
            throw new RuntimeException("Token de confirmation invalide ou expiré.");
        }
    }

    // Méthode pour connecter un utilisateur
    public UserLoginDto loginUser(UserLoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new GeneralBusinessException("Mot de passe incorrect.");
        }
        if (!user.isEnabled()) {
            throw new GeneralBusinessException("Compte non activé.");
        }
        String token = tokenprovider.generateToken(user.getEmail());
        System.out.println("Received token in loginUser: " + token);
        UserLoginDto connectedUser = new UserLoginDto();
        connectedUser.setEmail(user.getEmail());
        connectedUser.setToken(token);
        System.out.println("ConnectedUser Email: " + connectedUser.getEmail());
        System.out.println("ConnectedUser Token: " + connectedUser.getToken());
        return connectedUser;
    }

    // Méthode pour mettre à jour le profil de l'utilisateur
    public UserProfileDto updateProfile(String email, UserProfileDto userProfileDto) {

        Salon salon = salonRepository.findByUserEmail(email)
                .orElseThrow(() -> new GeneralBusinessException("Salon non trouvé."));
        salon.setName(userProfileDto.getSalonName());
        salon.setAddress(userProfileDto.getSalonAddress());
        salon.setOpeningDate(userProfileDto.getOpeningDate());
        salon.setEmployeeCount(userProfileDto.getNumberOfEmployees());
        salon.setRegion(userProfileDto.getRegion());
        salon.setDepartement(userProfileDto.getDepartement());
        salonRepository.save(salon);

        UserProfileDto userProfileDto1 = new UserProfileDto();

        userProfileDto1.setSalonName(salon.getName());

        userProfileDto1.setSalonAddress(salon.getAddress());
        userProfileDto1.setOpeningDate(salon.getOpeningDate());
        userProfileDto1.setNumberOfEmployees(salon.getEmployeeCount());
        userProfileDto1.setRegion(salon.getRegion());
        userProfileDto1.setDepartement(salon.getDepartement());
        return userProfileDto1;
    }

    // Méthode pour réinitialiser le mot de passe
    public void resetPassword(PasswordResetDto resetDto) {
        User user = userRepository.findByEmail(resetDto.getEmail())
                .orElseThrow(UserNotFoundException::new);
        String token = tokenprovider.generateToken(user.getEmail());
        try {
            emailService.sendPasswordResetLink(user.getEmail(), token);
        } catch (MessagingException e) {
            throw new GeneralBusinessException("Erreur lors de l'envoi de l'email de réinitialisation.");
        }
    }

    // Méthode pour mettre à jor le mot de passe de l'utilisateur
    public void updatePassword(String token, PasswordUpdateDto newPassword) {

        String email = tokenprovider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        userRepository.save(user);
    }

}
