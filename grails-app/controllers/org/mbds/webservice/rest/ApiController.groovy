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

    def book() {
        switch (request.getMethod()) {
            case 'GET':

                if (!params.id) {

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
                    render(status: 400, text: "Librairie cible introuvable!")
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

                Book bookToUpdate = new Book(params)
                def putBook = Library.executeUpdate("update Book b set b.name = " + bookToUpdate.name +
                        " , b.author = " + bookToUpdate.author +
                        " , b.yearCreated = " + bookToUpdate.yearCreated +
                        " , b.isbn = " + bookToUpdate.isbn +
                        " , b.library.id = " + bookToUpdate.library.is +
                        " where b.id = " + params.id)
                if (putBook){
                    render(status: 200, text: "Livre mis à jour avec succès")
                }else{
                    render(status: 400, text: "erreur")
                }
                break
            case 'DELETE':
                if (!Book.findById(params.id)) {
                    render(status: 404, text: "Le livre est introuvable")
                    return
                }
                def delBook = Book.executeUpdate("delete Book where id = " + params.id)
                if (delBook) {
                    render(status: 200, text: "Le livre est supprimée avec succès")
                } else {
                    render(status: 400, text: "erreur")
                }
                break
            default:
                response.status = 405
        }

    }

    def library() {
        switch (request.getMethod()) {
            case 'GET':
                if (!params.id) {

                    def libraries = [libraries: Library.findAll()]
                    render libraries as JSON

                } else {

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
                    response.status = 201

                } else {
                    render(status: 400, text: params)
                }
                break
            case 'PUT':

                Library libraryToUpdate = new Library(params)
                def putLib = Library.executeUpdate("update Library l set l.name = " + libraryToUpdate.name +
                        " , l.address = " + libraryToUpdate.address +
                        " , l.yearCreated = " + libraryToUpdate.yearCreated +
                        " where l.id = " + params.id)
                if (putLib){
                    render(status: 200, text: "Librairie mise à jour avec succès")
                }else{
                    render(status: 400, text: "erreur")
                }
                break
            case 'DELETE':
                if (!Library.findById(params.id)) {
                    render(status: 404, text: "La librairie est introuvable")
                    return
                }
                def delLib = Library.executeUpdate("delete Library where id = " + params.id)
                if (delLib) {
                    render(status: 200, text: "La librairie est supprimée avec succès")
                } else {
                    render(status: 400, text: "erreur")
                }
                break

            default:
                response.status = 405
        }
    }

}