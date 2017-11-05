package org.mbds.webservice.rest

class AuthenticationToken {

    String username
    String token
    static constraints = {
        username nullable: false
        token nullable: false
    }
}
