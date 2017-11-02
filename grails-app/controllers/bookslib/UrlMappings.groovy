package bookslib

class UrlMappings {

    static mappings = {
        "/books"(controller: 'api', action: 'book')

        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
