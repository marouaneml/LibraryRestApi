package bookslib

import org.mbds.webservice.rest.sec.Role
import org.mbds.webservice.rest.sec.User
import org.mbds.webservice.rest.sec.UserRole

class BootStrap {

    def init = { servletContext ->

        def adminRole = Role.findOrSaveWhere('authority': 'ROLE_ADMIN')
        def admin = User.findOrSaveWhere(username: 'admin', password: 'admin', enabled: true)

        if (!admin.authorities.contains(adminRole)) {
            UserRole.create(admin, adminRole, true)
        }

        User.withSession { it.flush() }
    }
    def destroy = {

    }
}
