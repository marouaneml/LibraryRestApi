package org.mbds.webservice.rest

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.mbds.webservice.rest.model.Book
import org.mbds.webservice.rest.model.Library

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

@Secured("permitAll")
class ApiController {
    def springSecurityService

    def success() {
        render(status: 200, text: "Connecté! <br>"
                + "Bienvenue l'utilisateur " + springSecurityService.currentUser.id + " <br>" +
                "<a href='/api/index'>aller à l'accueil</a>")
    }

    def index() {
        render("Vous etes dans le web service api &#xab librairie de MAROUANE &#xbb <br>" +
                "Vous pouvez tester les web services: <br>" +
                "<ol>" +
                "<li><a href='/api/library'>Les librairies</a></li>" +
                "<li><a href='/api/book'>Les Livres</a></li>" +
                "</ol>")
    }
    def books(){
        book()
    }
    def book() {

        switch (request.getMethod()) {
            case 'GET':

                if (!params.id) {
                    if (Book.findAll().size() == 0){
                        render(status: 200, text: "Pas de Livres")
                    }
                    def books = [books: Book.findAll()]
                    render books as JSON

                } else {
                    if (!Book.findById(params.id)) {
                        render(status: 404, text: "Livre introuvable")
                        return
                    }
                    if (Book.findById(params.id)) {
                        response.status = 200
                        render Book.findById(params.id) as JSON
                    } else {
                        response.status = 400
                    }
                }
                break
            case 'POST':
                if (!Library.get(params.library.id)) {
                    render(status: 404, text: "Librairie cible introuvable!")
                    return
                }
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                def bookInstance
                try {
                    Date date = formatter.parse(params.releaseDate);
                    bookInstance = new Book(
                            name: params.get("name"),
                            releaseDate: date,
                            isbn: params.get("isbn"),
                            author: params.get("author"),
                            library: params.get("library.id")
                    )
                } catch (ParseException e) {
                    render(status: 400, text: "la date est mal formatée")
                    return
                }

                if (bookInstance.save(flush: true)) {
                    render(status: 201, text: "Livre inseré avec succès")
                } else {
                    render(status: 400, text: "Des parametres ne sont pas au bon format")
                }
                break
            case 'PUT':
                request.withFormat {
                    json {
                        if (!Library.findById(request.JSON.library.id)) {
                            render(status: 400, text: "La librairie de substitution n'existe pas!")
                            return
                        } else {
                            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            Date date = formatter.parse(request.JSON.releaseDate);
                            def putBook = Library.executeUpdate("update Book b set b.name = '" + request.JSON.name + "'" +
                                    " , b.author = '" + request.JSON.author + "'" +
                                    " , b.releaseDate = '" + date.year + "-" + date.day + "-" + date.month + "'" +
                                    " , b.isbn = '" + request.JSON.isbn + "'" +
                                    " , b.library.id = " + request.JSON.library.id +
                                    " where b.id = " + params.id)
                            if (putBook) {
                                render(status: 202, text: "Livre mis à jour avec succès")
                            } else {
                                render(status: 400, text: "erreur")
                            }
                        }
                    }
                }
                break
            case 'DELETE':
                if (!Book.findById(params.id)) {
                    render(status: 404, text: "Le livre est introuvable")
                    return
                }
                def delBook = Book.executeUpdate("delete Book where id = " + params.id)
                if (delBook) {
                    render(status: 202, text: "Le livre est supprimée avec succès")
                } else {
                    render(status: 400, text: "la requête est mal formatée")
                }
                break
            default:
                response.status = 405
        }

    }
    def libraries(){
        library()
    }
    def library() {

        switch (request.getMethod()) {
            case 'GET':
                if (!params.id) {
                    if (Library.findAll().size() == 0){
                        render(status: 200, text: "Pas de librairies")
                    }
                    def libraries = [libraries: Library.findAll()]
                    render libraries as JSON

                } else {
                    println getActionName()
                    if (!Library.findById(params.id)) {
                        render(status: 404, text: "Librairie " + params.id + " introuvable!")
                        return
                    }
                    if (Library.findById(params.id)) {
                        response.status = 200
                        render Library.findById(params.id) as JSON
                    } else {
                        response.status = 400
                    }
                }
                break
            case 'POST':
                def libraryInstance = new Library(params)
                if (libraryInstance.save(flush: true)) {
                    render( status: 201, text: "Librairie bien creée")
                } else {
                    render(status: 400, text: "Un champ est mal formaté")
                }
                break
            case 'PUT':
                request.withFormat {
                    json {
                        def putLib = Library.executeUpdate("update Library l set l.name = '" + request.JSON.name + "'" +
                                " , l.address = '" + request.JSON.address + "'" +
                                " , l.yearCreated = " + request.JSON.yearCreated +
                                " where l.id = " + params.id)
                        if (putLib) {
                            render(status: 202, text: "Librairie mise à jour avec succès")
                        } else {
                            render(status: 400, text: "la requête est mal formatée")
                        }
                    }
                }

                break
            case 'DELETE':
                if (Book.findAllByLibrary(Library.findById(params.id)).size() > 0){
                    render(status: 405, text: "La librairie contient des livres!")
                    return
                }
                if (!Library.findById(params.id)) {
                    render(status: 404, text: "La librairie est introuvable")
                    return
                }
                def delLib = Library.executeUpdate("delete Library where id = " + params.id)
                if (delLib) {
                    render(status: 202, text: "La librairie est supprimée avec succès")
                } else {
                    render(status: 400, text: "la requête est mal formatée")
                }
                break

            default:
                response.status = 405
        }
    }

}