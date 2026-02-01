# MDD (Monde de Dév) — MVP

Mono-repo Full-Stack (Front Angular + Back Spring Boot + MySQL) avec authentification JWT, abonnements, articles et commentaires.

## Démarrage rapide (Docker)
Depuis la racine du repo :

`docker compose up --build`

Puis ouvrir :
- Front : `http://localhost:4200`
- API : `http://localhost:8080/api`

Arrêter :
- `Ctrl+C` puis `docker compose down`
- supprimer les données MySQL : `docker compose down -v`

## Développement sans Docker (optionnel)
Prérequis : Java 11+, Maven, Node.js, MySQL 8.

### 1) Démarrer MySQL (Docker uniquement)
`docker compose up -d db`

### 2) Back
Variables d'environnement (exemple PowerShell) :

`$env:DB_USERNAME='root'; $env:DB_PASSWORD='TON_MDP'`

Puis :

`cd back; mvn spring-boot:run`

### 3) Front
`cd front`

`npm ci`

`npm start`

Front : `http://localhost:4200` (proxy `/api` -> `http://localhost:8080` via `front/proxy.conf.json`)
