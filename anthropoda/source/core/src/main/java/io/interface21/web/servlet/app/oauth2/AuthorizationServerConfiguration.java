/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.interface21.web.servlet.app.oauth2;

import org.ameba.annotation.ExcludeFromScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * A AuthorizationServerConfiguration.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.2
 */
@ExcludeFromScan
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    /** We need to have an AuthenticationManager to delegate to the standard Spring Security authentication chain. */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("signing-key:kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg")
    private String signingKey;
/*
    public @Bean
    OAuth2AuthenticationController oAuth2AuthenticationController() {
      return new OAuth2AuthenticationController();
    }
    public @Bean
    HandlerMapping authControllerHandlerMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        Properties mappings = new Properties();
        mapping.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        mappings.put("/auth/authenticate", oAuth2AuthenticationController());
        mapping.setMappings(mappings);
        return mapping;
    }
*/
    /**
     * Bean that is used as token converter between OAuth2 and JWT tokens. Cause of symmetric token encryption the signing key must be
     * shared between resource and authorization server.
     *
     * @return The token converter
     */
    public
    @Bean
    JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(signingKey);
        return accessTokenConverter;
    }

    /**
     * We want to use a JWT toke store here.
     *
     * @return The token store
     */
    public
    @Bean
    TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * Configures the client statically.
     *
     * @param clients The configurer
     * @throws Exception if any errors
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("type-server")
                .secret("1a9030fbca47a5b2c28e92f19050bb77824b5ad1")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .authorities("ROLE_API_CLIENT")
                .scopes("read", "write", "trust")
                .resourceIds("oauth2-resource")
                .accessTokenValiditySeconds(600)
        ;
    }

    /**
     * {@inheritDoc}
     *
     * Configure the authorization server to use JWT toke store and the authentication manager.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_API_CLIENT')")
                .checkTokenAccess("permitAll()"/*"hasAuthority('ROLE_API_CLIENT')"*/)
                .allowFormAuthenticationForClients();
    }
}
