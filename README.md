# Bouteek : Gestion d'inventaire au sein d'entrepôts

Ce projet est une application fullstack permettant la gestion d'inventaires au sein d'entrepôts.

# Backend

La partie backend a été développée avec Java Spring Boot. Celle-ci est une API nécessitant pour certaines ressources une authentification avec JSON Web Token. Une route de connexion et de création de compte a été développée et permet la gestion des utilisateurs et la génération de tokens.
La base de données pour ce projet est PostgreSQL mais ce projet utilisant Spring Data JPA, il est possible de choisir un autre système de gestion de bases de données sans souci.

# Frontend

La partie frontend a été développée avec le framework React et le langage Typescript. La partie design utilise Bootstrap. Cette partie de l'application gère l'affichage des produits, la connexion d'un utilisateur et la création d'une commande contenant plusieurs produits.

# Déploiement

Pour déployer cette application, un fichier docker-compose.yaml existe et permet la création des images pour le frontend et le backend ainsi que le déploiement de l'application.
