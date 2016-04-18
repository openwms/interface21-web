Anthropodia is just _one_ web worm. Find more [here](../README.md)

# Web Worms: Anthropoda

 The Anthropoda module eases the use and configuration of security in applications. It is built on top of Spring Security and pre-configures
 Spring Security accordingly. Especially when applications deal with OAuth2, Spring Security OAuth2 takes a lot of complexity and error-prone
 low-level token handling away. But Spring Security OAuth2 does not limit the feature set the OAuth2 implementation offers, this leads to a
 framework that is complex by nature (like the base technology is). The Anthropoda component targets this problem, by restricting the options
 we have with OAuth2 to fit the most common use cases.

## A. Http BASIC Authentication

 Support for BASIC authentication is not such important, like for OAuth2. By using the provided annotation does simple pre-configure the
 security chain so that all incoming requests to `/public/**` are accessible w/o authentication and a configured view resolver can resolve
 the login form under `/public/auth/login`.

 Additionally an input field with name `j_Domain` is accepted to take the current login domain, or organization or context where an user
 tries to login. This is important to provide in a multi-tenancy environment (domain authentication).

## B. OAuth2 Authorization

 When you start implementing OAuth2 you need to know the basic vocabulary, the building blocks and responsibilities in addition to the
 OAuth2 authorization flows. This is a must-have for anyone who wants to work with OAuth2. Afterwards you'll probably search for some
 library that takes away the flow handling and is easy to use. Applications that already rely on the Spring Framework will likely use the
 [Spring Security OAuth](http://projects.spring.io/spring-security-oauth/) module to achieve this.

### What does Anthropoda do?

 From an users perspective, Anthropoda only knows about one Java annotation

````
 @EnableOAuth2(mode = OperationMode.COMBINED,
               authenticationProviderBean = "inMemProvider")
````

 This annotation is used on the protected `ResourceServer` as well as on the `AuthorizationServer`, even in an environment where both
 components run combined or separated. The security endpoint configuration is still part of each part.

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
