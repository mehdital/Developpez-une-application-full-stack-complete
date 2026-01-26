# Justification des choix techniques — MDD (MVP)

Ce document liste uniquement les choix réellement utilisés dans ce projet (MVP).

## Architecture
- **Mono-repo** : séparation claire `front/` (Angular) et `back/` (Spring Boot) avec communication via API HTTP.
- **Back en couches** : controllers fins + services + repositories (Spring Data JPA).

## Back-end (Java / Spring)
- **Spring Boot 2.7.x** : déjà présent dans le repo, stable et compatible Java 11.
- **Spring Data JPA (Hibernate)** : ORM simple pour le modèle minimal (users/topics/subscriptions/posts/comments).
- **Flyway** : migrations SQL versionnées (création du schéma + données de thèmes de test).
- **Spring Security + JWT (Bearer)** : sécurisation stateless des endpoints (seuls `/api/auth/*` sont publics).
- **BCrypt** : hash des mots de passe (PasswordEncoder Spring Security).
- **Validation** : Bean Validation (`@Valid`, contraintes) et format d’erreur JSON cohérent (`message` + `fieldErrors`).

## Base de données (MySQL)
- **MySQL** : conforme aux contraintes ORION.
- **Schéma minimal** : tables et contraintes (FK + uniques + index date) conformes au modèle demandé.

## Front-end (Angular)
- **Angular 14** : version/config du repo conservée.
- **Forms** : Reactive Forms pour la validation (login/register/profil/article/commentaire).
- **Sécurité** :
  - token stocké côté client (localStorage) pour la persistance,
  - interceptor HTTP pour ajouter `Authorization: Bearer <token>`,
  - guards de routes pour protéger l’accès aux écrans connectés.
- **UI** : conversion maquette -> templates Angular + styles globaux (tokens CSS) pour respecter le rendu desktop/mobile.
