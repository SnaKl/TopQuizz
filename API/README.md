# TopQuizz API

TopQuizz est une application Android, qui prend la forme d'un jeu de quiz dans lequel on peut choisir des thèmes de questions spécifiques.

Les données sont stockées dans une base de données MongoDB et sont accessibles par le biais d'une API REST en Node.js, dont l'utilisation est détaillée ci-dessous.

## Prérequis

Installer Node.js
* [Installation sur Windows](https://nodejs.org/dist/v16.13.1/node-v16.13.1-x86.msi)
* [Installation sur Linux](https://nodejs.org/dist/v16.13.1/node-v16.13.1.tar.gz)
* [Installation sur Mac](https://nodejs.org/dist/v16.13.1/node-v16.13.1.pkg)

## Installation de l'API Node.js

Cloner le projet TopQuizz 
``` 
git clone https://github.com/SnaKl/TopQuizz.git
```

Dans un terminal, aller dans le repertoire du projet 
```
cd path/to/TopQuizz
```
Se placer dans le repertoire de l'API
```
cd API
```
Installer les dépendances du projet (cette opération peut prendre quelques minutes)
```
npm install
```
## Utilisation de l'API

### Configuration 

Il est nécessaire d'ajouter un fichier de configuration des variables d'environnement. Pour cela, toujours dans le repertoire __API__  exécuter :
* Sur Linux/Mac 
``` 
printf "DATABASE_URL=mongodb+srv://admin:colin0412@topquizzdb.sfzet.mongodb.net/TopQuizz\n
SERVER_ADDRESS=http://localhost\n
SERVER_PORT=3000\n
TOKEN_KEY=AZERTY1234\n" >> .env
```
* Sur Windows 
``` 
(echo DATABASE_URL=mongodb+srv://admin:colin0412@topquizzdb.sfzet.mongodb.net/TopQuizz
More? echo SERVER_ADDRESS=http://localhost 
More? echo SERVER_PORT=3000
More? echo TOKEN_KEY=AZERTY1234) > .env
```

### Lancement 
Pour démarrer l'API en mode __dev__ , il faut exécuter : 
```
npm run dev
```

Pour démarrer l'API, il faut exécuter :
```
npm run start
```

Pour lancer les tests unitaires, il faut exécuter :
```
npm run test
```

## Description de l'API REST

Les exemples ci-dessous sont pour accéder aux données de la collection Theme.

### Récupérer la liste de thèmes:

#### Requête 
```GET /api/theme```

```
curl --location --request GET 'http://localhost:3000/api/theme'
```
#### Réponse 

```
{"themes":[
  {"_id":"61b2319d5d0525cc9b21c2e6",
  "title":"Colin",
  "imageUrl":"\\themeImage\\1639068061173831950999.jpeg",
  "description":"Quizz sur colin LP",
  "nbQuestion":7},
  {"_id":"61b49d64378b52e9acfeaf22",
  "title":"Emily",
  "imageUrl":"\\themeImage\\1639226724246791472994.jpeg",
  "description":"Quizz sur emily",
  "nbQuestion":1},
  {"_id":"61b5e9683eacb1ee16cd515e",
  "title":"test",
  "imageUrl":null,
  "description":"test",
  "nbQuestion":1}]}
```

### Créer un nouveau thème :

#### Requête 
```POST /api/theme```

```
curl --location --request POST 'http://localhost:3000/api/theme' \
--header 'Content-Type: application/json' \
--data-raw '{
  "title":"Readme Example",
  "imageUrl":"\\themeImage\\1639068061173831950999.jpeg",
  "description":"Create a new theme for Readme"
  }'
```


