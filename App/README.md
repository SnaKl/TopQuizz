# Application TopQuizz
TopQuizz est une application Android, qui prend la forme d'un jeu de quiz dans lequel on peut choisir des thèmes de questions spécifiques.  
L'application a été développée sur Android Studio. 

## Prérequis 
Installer Android Studio  
[Télécharger Android Studio](https://developer.android.com/studio?gclid=Cj0KCQiA2NaNBhDvARIsAEw55hji2JIMYEN28SawUqPBwGx-tP5wZ8v4NQKSmaCaKe5WX74AsOrOjWsaAhryEALw_wcB&gclsrc=aw.ds)

## Installation

Cloner le projet TopQuizz.
```
git clone https://github.com/SnaKl/TopQuizz.git
```
Ouvrir le dossier ```TopQuizz/App``` du projet avec Android Studio.  



## Configuration

Le fichier ```src/main/java/com/neves/topquiz/GlobalVariable.java``` contient les variables d'environnement pour connecter l'application et l'API.  
Ouvrez le fichier et modifiez la valeur de ``` API_HOST ```: 

* Pour tester l'application avec l'émulateur ou un smartphone, il faut changer la valeur de ``` API_HOST ``` pour qu'elle corresponde à votre __adresse IP__. 

* Pour tester __uniquement__ avec l'émulateur, il faut changer la valeur de ```API_HOST``` pour qu'elle corresponde à votre __adresse IP__ ou simplement __localhost__.

## Utilisation 

Veillez à bien démarrer l'API comme décrit dans la [documentation de l'API](https://github.com/SnaKl/TopQuizz/blob/App/API/README.md)  

Le projet est prêt à être utilisé, vous pouvez le run en appuyant sur la flèche verte dans la barre de menu. 

## Version d'Android
Notre projet utilise __Android 11__.

