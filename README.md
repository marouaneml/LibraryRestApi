# LibraryRestApi
implémentation du web service rest sous le framework Grails avec le spring security dans le cadre du TP web service encadré par Mr.Galli

## La documentation de l'API
/!\ Toute la partie metier est dans le controleur ApiController
### Les librairies
| Le verbe HTTP | Le path du web service | Données en entrée | La réponse | Erreurs possibles |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| GET | /api/[ library \| libraries ] | empty | la liste de toutes les librairies | 200: Pas de librairies |
| GET | /api/[ library \| libraries ]/{number} | empty | la libraire qui a l'id en question |  404: Librairie introuvable |
| POST | /api/[ library \| libraries ] | JSON String | 201: Librairie bien creée | 400: un champ ne correspond pas ou mal formaté <br> 401: Vous n'etes pas admin, impossible d'ajouter une librairie |
| PUT | /api/[ library \| libraries ] | JSON String | 202: Librairie mise à jour avec succès | 400: la requête est mal formatée <br> 401: Vous n'etes pas admin, impossible de modifier une librairie |
| DELETE | /api/[ Library \| libraries ] | empty | 202: La librairie est supprimée avec succès | 405: La librairie contient des livres! <br> 404: La librairie est introuvable <br> 401: Vous n'etes pas admin, impossible de supprimer une librairie |

### Les livres
| Le verbe HTTP | Le path du web service | Données en entrée | La réponse | Erreurs possibles |
| ------------- | ------------- | ------------- | ------------- | ------------- | 
| GET | /api/[ book \| books ] | empty | la liste de tous les livres | 200: Pas de livres |
| GET | /api/[ book \| books ]/{number} | empty | le livre qui correspond à cet id |  404: Livre introuvable |
| POST | /api/[ book \| books ] | JSON String | 201: Livre inseré avec succès | 404: Librairie cible introuvable! <br> 400: la date est mal formatée ou des parametres ne sont pas au bon format <br> 405: Vous n'etes pas adhérant, impossible d'jouter un livre |
| PUT | /api/[ book \| books ] | JSON String | 202: Livre mis à jour avec succès | 400: La librairie de substitution n'existe pas! <br> 405: Vous n'etes pas adhérant, impossible de modifer un livre |
| DELETE | /api/[ book \| books ] | empty | 202: Le livre est supprimé avec succès | 404: Le livre est introuvable <br> 400: la requête est mal formatée <br> 401: Vous n'etes pas admin, impossible de supprimer un livre |

### Fonctionnalités prises en charge
  * Dans tous les web services, une reponse 405 est possible si la methode n'est pas autorisée, une reponse 500 en cas d'erreur interne du serveur
  * Les chemins Library et Librairies redirigent vers les même services
  * Les services des livres aussi sont accessibles par /api/book ou bien /api/books
  * Impossible de supprimer une librairie si un livre fait partie de cette dernière

### Données de test

```javascript
// Nouvelle Librairie ou pour modifier une librairie
// méthode POST/PUT
{
	"name": "MoroccoLib",
	"address": "Morocco, Agadir NR 45",
	"yearCreated": "1993"
}
// Nouveau livre ou modifier un livre
// méthode POST/PUT
{
	"name":"Le Monstre",
	"releaseDate":"19-03-1994",
	"isbn":"11745869996",
	"author":"Marouane",
	"library":{
		"id":1
	}
}
```

### La securité 
La sécurisation avec Spring Security pour le web service REST nécessite une authentification pour le REST API basée sur le token.
Parmit les web service que j'ai implementé, des WS de consultation tel que GET sur les livres/librairies n'a pas besoin d'authentification, alors que d'autres telques POST/PUT/DELETE sur les livres necessitent le ROLE_USER, et POST/PUT/DELETE sur les librairies necessitent le ROLE_ADMIN.
J'ai suivi les étapes suivantes pour realiser cette sécurisation:

  * Etape 1: Ajouter les dependances necessaires :
```javascript 
compile 'org.grails.plugins:spring-security-core:3.2.0.M1'
compile "org.grails.plugins:spring-security-rest:2.0.0.M2"
```
  * Etape 2: Run grails s2-quickstart com.asoftwareguy.example.auth User Role pour creer les domaines de classe pour l'authentification.
  * Etape 3: Creer la classe AuthenticationToken
```javascript   
class AuthenticationToken {
    String username
    String token
}
```
  * Etape 4: Ajouter les proprietés necessaires dans le fichier application.groovy
```javascript
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'corg.mbds.webservice.rest.AuthenticationToken'
grails.plugin.springsecurity.rest.token.storage.gorm.tokenValuePropertyName = 'token'
grails.plugin.springsecurity.rest.token.storage.gorm.usernamePropertyName = 'username'
```

### Les données de test
```javascript
// Se connecter en tant qu'admin
// méthode POST
{
	"username": "admin",
	"password": "admin"
}
// Se connecter en tant d'adhérant 
méthode POST
{
	"username": "user",
	"password": "user"
}
```
