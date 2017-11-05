package bookslib

import org.mbds.webservice.rest.sec.Role
import org.mbds.webservice.rest.sec.User
import org.mbds.webservice.rest.sec.UserRole

class BootStrap {

    def init = { servletContext ->

        def adminRole = Role.findOrSaveWhere('authority': 'ROLE_ADMIN')
        def admin = User.findOrSaveWhere(username: 'admin', password: 'admin', enabled: true)
        def userRole = Role.findOrSaveWhere('authority': 'ROLE_USER')
        def user = User.findOrSaveWhere(username: 'user', password: 'user', enabled: true)

        if (!admin.authorities.contains(adminRole)) {
            UserRole.create(admin, adminRole, true)
        }
        if (!user.authorities.contains(userRole)) {
            UserRole.create(user, userRole, true)
        }
        User.withSession { it.flush() }
    }
    def destroy = {

    }
}
