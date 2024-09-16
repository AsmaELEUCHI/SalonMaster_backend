# SalonMaster Backend

## Description

   Le backend de l'application SalonMaster est développé avec Spring Boot. Il fournit des fonctionnalités pour la gestion des utilisateurs et des chiffres d'affaires des salons de beauté. Les principales fonctionnalités incluent :
   
   - Inscription des utilisateurs avec validation par email.
   - Connexion des utilisateurs avec génération de tokens JWT.
   - Gestion des données de profil utilisateur.
   - Saisie et affichage des chiffres d'affaires avec gestion de l'historique.
   - Calcul des moyennes de chiffres d'affaires à différents niveaux (global, régional, départemental).
   - Réinitialisation du mot de passe

## Fonctionnalités

   ### Inscription et Validation
   
   1. **Inscription :**
      - Les données d'inscription sont stockées dans la base de données.
      - Un email de confirmation est envoyé à l'utilisateur.
   
   2. **Confirmation :**
      - L'utilisateur confirme son compte via un token envoyé par email.
      - Le statut de l'utilisateur passe à actif dans la base de données.
   
   ### Connexion
   - Lors de la connexion, les données de l'utilisateur sont vérifiées.
   - Un token JWT est généré et envoyé si les informations sont correctes.
   - Le token doit être inclus dans les requêtes pour accéder à l'espace personnel.
   
   ### Espace Personnel
   1. **Profile :**
   - Mise à jour des données de profil utilisateur.
   - Les données sont stockées et affichées en temps réel.
   2. **Gestion des chiffres d'affaires :**
   - Saisie des chiffres d'affaires via un formulaire.
   - Les données sont stockées dans la base de données et affichées dans un tableau d'historique.
   - Possibilité de suppression des entrées dans l'interface et dans la base de données.
   3. **Calcul des moyennes :**
   - Calcul de la moyenne des chiffres d'affaires de l'utilisateur.
   - Calcul des moyennes à niveau global, régional et départemental pour tous les clients.
   ### Réinitialisation de Mot de Passe
   - Reçevoir la demande de réinitialisation de Mot de Passe et envoi d'un email avec le lien de réinitialisation.
   - Mettre à jour du mot de passe dans la base de donnée, lors de la soumission du nouveau mot de passe avec le token de réinitialisation.
  
## Technologies Utilisées
   - Java v22.0.2
   - Spring Boot v3.3.2
   - Maven v3.9.8
   - MariaDB v10.4.32

## Structure du projet
   - entity : Contient les classes représentant les entités de la base de données.
   - repository : Interfaces pour accéder aux données.
   - dto : Classes de transfert de données entre le frontend et le backend.
   - exception : Classes d'exception personnalisées pour la gestion des erreurs.
   - exceptionHandler : Gestionnaire d'exceptions globales.
   - config : Configuration du projet, y compris la sécurité et les filtres JWT.
   - services : Services métiers pour la logique d'application.
   - controller : Contrôleurs REST pour gérer les requêtes HTTP.
  
## Installation et configuration
   Avant de commencer, s'assurer d'avoir les outils suivants : Java, la  base de donnée (MariaDB ou MySQL..), IDE, git
   
   1. **Cloner le Dépôt** :
      Exécution de la commande suivante pour cloner le dépôt depuis GitHub :
   
      ```powershell
      git clone https://github.com/AsmaELEUCHI/SalonMaster_backend.git
   
   2. **Configurer application.properties** :
   - copier le fichier application.properties.example situé dans src/main/resources/
   - Renommer le fichier application.properties
   - Modifier les valeurs en fonction de la configuration locale
   3. **Créer la base de donnée sur le serveur**
   4. **Lancer l'application**
      Cliquer sur run
## Frontend
   Pour le développement et les détails de la partie frontend : https://github.com/AsmaELEUCHI/SalonMaster_frontend
