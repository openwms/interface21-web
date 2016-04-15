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
package io.interface21.web.servlet.app;

import java.util.Collections;

import org.ameba.annotation.FilteredComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * A Starter.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.2
 */
@Configuration
@EnableAutoConfiguration
@EnableBasicAuthentication(authenticationProviderBean = "inMemProvider")
@FilteredComponentScan
class BasicAuthenticationApplication {

    public
    @Bean
    AuthenticationProvider inMemProvider() {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setPasswordEncoder(new BCryptPasswordEncoder());
        dap.setUserDetailsService(new InMemoryUserDetailsManager(Collections.singletonList(new User("user", "test", Collections.singletonList(new SimpleGrantedAuthority("API_CLIENT"))))));
        return dap;
    }

    public static void main(String[] args) {
        SpringApplication.run(BasicAuthenticationApplication.class, args);
    }
}
