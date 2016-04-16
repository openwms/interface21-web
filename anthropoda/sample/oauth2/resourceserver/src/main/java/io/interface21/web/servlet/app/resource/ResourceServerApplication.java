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
package io.interface21.web.servlet.app.resource;

import java.util.Collections;

import io.interface21.web.servlet.app.oauth2.EnableOAuth2;
import org.ameba.annotation.FilteredComponentScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * A ResourceServerApplication.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableAutoConfiguration
@FilteredComponentScan(basePackages = "io.interface21")
@EnableOAuth2(asResourceServer = true, authenticationProviderBean = "inMemProvider")
@EnableOAuth2Client
//@EnableWebSecurity
public class ResourceServerApplication {

    @Value("signing-key:kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg")
    private String signingKey;

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

    public
    @Bean
    AuthenticationProvider inMemProvider() {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        //dap.setPasswordEncoder(new BCryptPasswordEncoder());
        dap.setUserDetailsService(new InMemoryUserDetailsManager(Collections.singletonList(new User("user", "test", Collections.singletonList(new SimpleGrantedAuthority("ROLE_API_CLIENT"))))));
        return dap;
    }

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }
}
