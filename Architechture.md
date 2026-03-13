# Architecture - InnotechFusion Voter App

## Description
Application web d'émargement des votants pour l'association InnotechFusion.
Elle permet de lister les membres et d'enregistrer leur vote de manière irréversible.


## Stack technique

| Couche      | Technologie         | Rôle                              |
|-------------|---------------------|-----------------------------------|
| Front-end   | Angular             | Interface utilisateur (SPA)       |
| Back-end    | Spring Boot (Java)  | API REST                          |
| Base de données | MySQL           | Stockage des données              |
| Conteneurisation | Docker / Docker Compose | Déploiement des services |


## Architecture

[ Navigateur ]
     |
     | HTTP (JSON)
     v
[ Angular - Front-end ] --> port 4200
     |
     | HTTP (JSON)
     v
[ Spring Boot - API REST ] --> port 8080
     |
     | JPA / Hibernate
     v
[ MySQL - Base de données ] --> port 3306


## Schéma BDD - V1

Table : `membre`

| Colonne           | Type         | Contrainte         |
|-------------------|--------------|--------------------|
| id                | BIGINT       | PK, AUTO_INCREMENT |
| nom               | VARCHAR(100) | NOT NULL           |
| prenom            | VARCHAR(100) | NOT NULL           |
| date_de_naissance | DATE         | NOT NULL           |
| a_vote            | BOOLEAN      | DEFAULT false      |


## Schéma BDD - V2 (prévisionnel, non implémenté)

Objectif V2 : gérer plusieurs scrutins et enregistrer les votes de manière anonyme.

Table : `membre`
| id | nom | prenom | date_de_naissance |

Table : `scrutin`
| id | nom | date_du_scrutin |

Table : `vote`
| id | scrutin_id (FK) | membre_id (FK) |

> Le vote est anonyme : on sait QUI a voté (membre_id), mais PAS pour QUI.
> La table `vote` peut avoir une contrainte UNIQUE sur (scrutin_id, membre_id)
> pour éviter qu'un membre vote deux fois pour le même scrutin.
