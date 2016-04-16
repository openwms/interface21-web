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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.StringUtils;

/**
 * A OAuth2Configuration.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@ExcludeFromScan
@Configuration
@EnableWebSecurity
//@DependsOn("httpAuthenticationProvider")
public class OAuth2Configuration extends WebSecurityConfigurerAdapter implements BeanFactoryAware {

    public static String authenticationProviderBean;
    private BeanFactory beanFactory;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        if (StringUtils.hasText(authenticationProviderBean)) {
            AuthenticationProvider ap = beanFactory.getBean(authenticationProviderBean, AuthenticationProvider.class);
            auth.authenticationProvider(ap);
        }
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/public/**", "/webjars/**", "/resources/**", "/auth/authenticate").permitAll()
                .antMatchers("/**").access("#oauth2.hasScope('ROLE_API_CLIENT')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .defaultSuccessUrl("/index")
                .failureUrl("/login?error=1")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .rememberMe()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error/403")
        ;}

    /**
     * {@inheritDoc}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/public/**", "/webjars/**", "/resources/**").permitAll()
            .anyRequest().authenticated()
        .and()
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .defaultSuccessUrl("/index")
            .failureUrl("/login?error=1")
            .permitAll()
        .and()
            .logout()
            .permitAll()
        .and()
            .rememberMe()
        .and()
            .exceptionHandling()
            .accessDeniedPage("/error/403");
    }
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
