# Justification des choix techniques — MDD (MVP)

Ce document liste uniquement les choix réellement utilisés dans ce projet (MVP).

## Architecture
- **Mono-repo** : séparation claire `front/` (Angular) et `back/` (Spring Boot) avec communication via API HTTP.
- **Back en couches** : controllers fins + services + repositories (Spring Data JPA).

## Back-end (Java / Spring)
- **Spring Boot 2.7.3 (Java 11)** : version stable, compatible avec le projet.
- **Spring Data JPA (Hibernate)** : ORM simple pour le modèle minimal (users/topics/subscriptions/posts/comments).
- **Flyway** : migrations SQL versionnées (schéma + données initiales des thèmes).
- **Spring Security + JWT (Bearer)** : sécurisation stateless des endpoints (seuls `/api/auth/*` sont publics).
- **BCrypt** : hash des mots de passe.
- **Validation** : Bean Validation (`@Valid`) + erreurs JSON cohérentes (`message` + `fieldErrors`).

## Base de données (MySQL)
- **MySQL 8** : base de dev via Docker Compose.
- **Schéma minimal** : tables et contraintes (FK + uniques) conformes au modèle demandé.

## Front-end (Angular)
- **Angular 14** : version du projet conservée.
- **Reactive Forms** : validation (login/register/profil/article/commentaire).
- **Sécurité** :
  - JWT stocké côté client (localStorage),
  - interceptor HTTP pour ajouter `Authorization: Bearer <token>`,
  - guards de routes pour protéger l’accès aux écrans connectés.
- **UI** : styles SCSS simples basés sur la maquette (pas de dépendance UI superflue).
