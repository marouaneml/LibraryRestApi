// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'org.mbds.webservice.rest.sec.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'org.mbds.webservice.rest.sec.UserRole'
grails.plugin.springsecurity.authority.className = 'org.mbds.webservice.rest.sec.Role'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/', access: ['permitAll']],
        [pattern: '/error', access: ['permitAll']],
        [pattern: '/index', access: ['permitAll']],
        [pattern: '/index.gsp', access: ['permitAll']],
        [pattern: '/shutdown', access: ['permitAll']],
        [pattern: '/assets/**', access: ['permitAll']],
        [pattern: '/**/js/**', access: ['permitAll']],
        [pattern: '/**/css/**', access: ['permitAll']],
        [pattern: '/**/images/**', access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**', filters: 'none'],
        [pattern: '/**/js/**', filters: 'none'],
        [pattern: '/**/css/**', filters: 'none'],
        [pattern: '/**/images/**', filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**', filters: 'JOINED_FILTERS'],

        //Stateless chain
        [
                pattern: '/api/**',
                filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
        ],

        //Traditional chain
        [
                pattern: '/**',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ]
]

grails.plugin.springsecurity.logout.postOnly = false

grails.plugin.springsecurity.successHandler.alwaysUseDefault = true
grails.plugin.springsecurity.successHandler.defaultTargetUrl = "/api/success"

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'org.mbds.webservice.rest.sec.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'org.mbds.webservice.rest.sec.UserRole'
grails.plugin.springsecurity.authority.className = 'org.mbds.webservice.rest.sec.Role'
grails.plugin.springsecurity.securityConfigType = 'InterceptUrlMap'
grails.plugin.springsecurity.interceptUrlMap = [
        '/':                    ['permitAll'],
        '/assets/**':           ['permitAll'],
        '/partials/**':         ['permitAll'],
        '/**':                  ['isFullyAuthenticated()']
]

grails.plugin.springsecurity.rememberMe.persistent = false
grails.plugin.springsecurity.rest.login.useJsonCredentials = true
grails.plugin.springsecurity.rest.login.failureStatusCode = 401
grails.plugin.springsecurity.rest.token.storage.useGorm = true
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'corg.mbds.webservice.rest.AuthenticationToken'
grails.plugin.springsecurity.rest.token.storage.gorm.tokenValuePropertyName = 'token'
grails.plugin.springsecurity.rest.token.storage.gorm.usernamePropertyName = 'username'