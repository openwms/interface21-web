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
package io.interface21.web.auth.app;

import org.ameba.annotation.ExcludeFromScan;
import org.ameba.app.AuthenticationConfigurer;
import org.ameba.exception.SecurityException;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;
import java.util.List;

/**
 * A SecurityConfig.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.1
 * @since 0.1
 */
@ExcludeFromScan
class SecurityConfig implements AuthenticationConfigurer {

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
    }

    @Autowired
    private ApplicationContext context;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth, List<EnableBasicAuthentication> basicAuthenticationConfigurations) throws Exception {
        Collections.sort(basicAuthenticationConfigurations, AnnotationAwareOrderComparator.INSTANCE);


        basicAuthenticationConfigurations.stream().map(EnableBasicAuthentication::userDetailsService).findFirst().ifPresent(p -> {
            try {
                UserDetailsService uds = context.getBean(p);
                auth.userDetailsService(uds);
            } catch (Exception e) {
                throw new BeanInstantiationException(UserDetailsService.class, "Can't find or instantiate bean of configured type");
            }
        });
        return auth.build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(HttpSecurity http) {
        try {
            http.httpBasic()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/public/**").permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/public/auth/login").authenticationDetailsSource(new AuthenticationDetailsSourceWithDomain())
            ;
        } catch (Exception e) {
            // TODO: 09/03/16
            throw new SecurityException();
        }
    }
}
