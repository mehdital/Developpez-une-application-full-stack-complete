# MDD (Monde de Dév) — MVP

Mono-repo Full-Stack (Front Angular + Back Spring Boot + MySQL) avec authentification JWT, abonnements, articles et commentaires.

## Le plus simple (1 commande, via Docker)
Depuis la racine du repo :

`docker compose up --build`

Puis ouvre :
- Front : `http://localhost:4200`
- API (direct) : `http://localhost:8080`

Arrêter :
- `Ctrl+C` puis `docker compose down`
- supprimer les données MySQL : `docker compose down -v`

## Dev sans Docker (optionnel)
### 1) Démarrer MySQL

`docker compose up -d db`

### 2) Back
`$env:DB_USERNAME='root'; $env:DB_PASSWORD='TON_MDP'`

`cd back; mvn spring-boot:run`



### 3) Front
`cd front`

`npm install`

`npm start`

Front : `http://localhost:4200`
