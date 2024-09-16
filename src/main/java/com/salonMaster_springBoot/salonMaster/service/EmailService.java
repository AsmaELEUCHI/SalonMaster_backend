package com.salonMaster_springBoot.salonMaster.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.url}")
    private String appUrl;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${app.confirmation.endpoint}")
    private String confirmationEndpoint;

    @Value("${app.reset.endpoint}")
    private String resetEndpoint;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegistrationConfirmation(String email, String token) throws MessagingException {
        String confirmationUrl = UriComponentsBuilder.fromHttpUrl(appUrl)
                .path(confirmationEndpoint)
                .queryParam("token", token)
                .toUriString();

        // Texte brut
        String plainTextMessage = String.format(
                "Bonjour,\n\n" +
                        "Merci de vous être inscrit. Veuillez confirmer votre compte en cliquant sur le lien suivant :\n\n"
                        +
                        "%s\n\n" +
                        "Si vous n'avez pas demandé cette inscription, ignorez ce message.\n\n" +
                        "Cordialement,\n" +
                        "L'équipe de notre site",
                confirmationUrl);

        // HTML
        String htmlMessage = String.format(
                """
                        <!DOCTYPE html>
                        <html>
                        <head>
                            <style>
                                body { font-family: Arial, sans-serif; }
                                .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                                .button { background-color: #007BFF; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; }
                            </style>
                        </head>
                        <body>
                            <div class="container">
                                <h2>Bonjour,</h2>
                                <p>Merci de vous être inscrit. Veuillez confirmer votre compte en cliquant sur le bouton suivant :</p>
                                <a href="%s" class="button">Confirmer mon compte</a>
                                <p>Si vous n'avez pas demandé cette inscription, ignorez ce message.</p>
                                <p>Cordialement,<br>L'équipe de notre site</p>
                            </div>
                        </body>
                        </html>""",
                confirmationUrl);

        // Configurez les paramètres de l'e-mail
        MimeMessage messageMail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(messageMail, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Confirmation de votre inscription");
        helper.setText(plainTextMessage, htmlMessage); // Ajoutez les deux versions

        mailSender.send(messageMail);
    }

    public void sendPasswordResetLink(String email, String token) throws MessagingException {
        String resetUrl = UriComponentsBuilder.fromHttpUrl(frontendUrl)
                .path(resetEndpoint)
                .queryParam("token", token)
                .toUriString();

        // Texte brut
        String plainTextMessage = String.format(
                "Bonjour,\n\n" +
                        "Vous avez demandé une réinitialisation de mot de passe. Veuillez suivre le lien ci-dessous pour réinitialiser votre mot de passe :\n\n"
                        +
                        "%s\n\n" +
                        "Si vous n'avez pas demandé cette réinitialisation, ignorez ce message.\n\n" +
                        "Cordialement,\n" +
                        "L'équipe de notre site",
                resetUrl);

        // HTML
        String htmlMessage = String.format(
                """
                        <!DOCTYPE html>
                        <html>
                        <head>
                            <style>
                                body { font-family: Arial, sans-serif; }
                                .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                                .button { background-color: #007BFF; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; }
                            </style>
                        </head>
                        <body>
                            <div class="container">
                                <h2>Bonjour,</h2>
                                <p>Vous avez demandé une réinitialisation de mot de passe. Veuillez suivre le lien ci-dessous pour réinitialiser votre mot de passe :</p>
                                <a href="%s" class="button">Réinitialiser mon mot de passe</a>
                                <p>Si vous n'avez pas demandé cette réinitialisation, ignorez ce message.</p>
                                <p>Cordialement,<br>L'équipe de notre site</p>
                            </div>
                        </body>
                        </html>""",
                resetUrl);

        // Configurez les paramètres de l'e-mail
        MimeMessage messageMail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(messageMail, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Réinitialisation de votre mot de passe pour votre compte SalonMaster");
        helper.setText(plainTextMessage, htmlMessage); // Ajoutez les deux versions

        mailSender.send(messageMail);
    }
}
