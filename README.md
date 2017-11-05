# LibraryRestApi
implémentation du web service rest sous le framework Grails avec le spring security

## La documentation de l'API
### Les librairies
| Le verbe HTTP | Le path du web service | La réponse | Erreurs possibles |
| ------------- | ------------- | ------------- | ------------- |
| GET | /api/[ library \| libraries ] | la liste de toutes les librairies | 200: Pas de librairies |
| GET | /api/[ library \| libraries ]/{number} | la libraire qui a l'id en question |  404: Librairie introuvable |
| POST | /api/[ library \| libraries ] | 201: Librairie bien creée | 400: un champ ne correspond pas ou mal formaté |
| PUT | /api/[ library \| libraries ] | 202: Librairie mise à jour avec succès | 400: la requête est mal formatée |
| DELETE | /api/[ Library \| libraries ] | 202: La librairie est supprimée avec succès | 405: La librairie contient des livres! <br> 404: La librairie est introuvable |

### Les livres
| Le verbe HTTP | Le path du web service | La réponse | Erreurs possibles |
| ------------- | ------------- | ------------- | ------------- |
| GET | /api/[ book \| books ] | la liste de tous les livres | 200: Pas de livres |
| GET | /api/[ book \| books ]/{number} | le livre qui correspond à cet id |  404: Livre introuvable |
| POST | /api/[ book \| books ] | 201: Livre inseré avec succès | 404: Librairie cible introuvable! <br> 400: la date est mal formatée ou des parametres ne sont pas au bon format |
| PUT | /api/[ book \| books ] | 202: Livre mis à jour avec succès | 400: La librairie de substitution n'existe pas! |
| DELETE | /api/[ book \| books ] | 202: Le livre est supprimé avec succès | 404: Le livre est introuvable <br> 400: la requête est mal formatée |

### Remarque
  * Dans tous les web services, une reponse 405 est possible si la methode n'est pas autorisée, une reponse 500 en cas d'erreur interne du serveur
  * Les chemins Library et Librairies redirigent vers les même services
  * Les services des livres aussi sont accessibles par /api/book ou bien /api/books
  * Impossible de supprimer une librairie si un livre fait partie de cette dernière
