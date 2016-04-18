# Web Worms: Anthropoda

 The Anthropoda module eases the use and configuration of security in applications. It is built on top of Spring Security and pre-configures
 Spring Security accordingly. Especially when applications deal with OAuth2, Spring Security OAuth2 takes a lot of complexity and error-prone
 low-level token handling away. But Spring Security OAuth2 does not limit the feature set the OAuth2 implementation offers, this leads to a
 framework that is complex by nature (like the base technology is). The Anthropoda component targets this problem, by restricting the options
 we have with OAuth2 to fit the most common use cases.

## A. Basic Authentication

## B. OAuth2 Authorization

 When you start implementing OAuth2 you need to know the basic vocabulary, the building blocks and responsibilities in addition to the
 OAuth2 authorization flows. This is a must-have for anyone who wants needs to work with OAuth2. Afterwards you'll probably search for some
 library that takes away the flow handling and is as easy to use. Applications that already rely on the Spring Framework will likely use the
 Spring Security OAuth2 module to achieve this.

 What does Anthropoda do?

 From the users perspective view, Anthropoda only knows about one Java annotation

````
 @EnableOAuth2(mode = OperationMode.COMBINED,
               authenticationProviderBean = "inMemProvider")
````

 This annotation can be used on the protected `ResourceServer` as well as on the `AuthorizationServer`. Even in an environment where both
 components run combined or separated. The security endpoint configuration is still part of both.

 A `ResourceServer` may be configured this way ...

````
 @EnableOAuth2(mode = OperationMode.RESOURCES,
               authenticationUrl = "http://localhost:8083/auth/authenticate",
               authenticationProviderBean = "httpAuthenticationProvider")
````

 Whereas the AuthorizationServer counterpart is configure like this ...

````
 @EnableOAuth2(mode = OperationMode.AUTHORIZATIONS,
               authenticationProviderBean = "inMemProvider")
````
