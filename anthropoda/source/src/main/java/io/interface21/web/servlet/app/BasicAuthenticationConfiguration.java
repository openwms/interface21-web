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

import org.ameba.annotation.ExcludeFromScan;
import org.ameba.app.AuthenticationConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.StringUtils;

/**
 * A BasicAuthenticationConfiguration.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@ExcludeFromScan
public class BasicAuthenticationConfiguration extends WebSecurityConfigurerAdapter implements AuthenticationConfigurer, BeanFactoryAware {

    public static String authenticationProviderBean;
    private BeanFactory beanFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        if (StringUtils.hasText(authenticationProviderBean)) {
            AuthenticationProvider ap = beanFactory.getBean(authenticationProviderBean, AuthenticationProvider.class);
            auth.authenticationProvider(ap);
        }
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