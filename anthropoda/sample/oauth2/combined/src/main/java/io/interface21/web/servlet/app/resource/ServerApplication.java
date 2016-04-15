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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A ServerApplication.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableAutoConfiguration
@FilteredComponentScan
@EnableOAuth2(asResourceServer = true, asAuthorizationServer = true, authenticationProviderBean = "inMemProvider")
public class ServerApplication {

    public
    @Bean
    AuthenticationProvider inMemProvider() {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        //dap.setPasswordEncoder(new BCryptPasswordEncoder());
        dap.setUserDetailsService(new InMemoryUserDetailsManager(Collections.singletonList(new User("user", "test", Collections.singletonList(new SimpleGrantedAuthority("ROLE_API_CLIENT"))))));
        return dap;
    }

    @RequestMapping("/")
    public String home() {
        return "Hello World";
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
