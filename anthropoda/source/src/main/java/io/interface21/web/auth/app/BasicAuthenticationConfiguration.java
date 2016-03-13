/*
 * Stamplets.
 *
 * This software module including the design and software principals used is and remains
 * the property of Heiko Scherrer (the initial authors of the project) with the understanding
 * that it is not to be reproduced nor copied in whole or in part, nor licensed or otherwise
 * provided or communicated to any third party without their prior written consent.
 * It must not be used in any way detrimental to the interests of the author.
 * Acceptance of this module will be construed as an agreement to the above. 
 *
 * All rights of Heiko Scherrer remain reserved.
 * Stamplets is an registered trademarks of Heiko Scherrer. Other products and company
 * names mentioned herein may be trademarks or trade names of the respective owner.
 * Specifications are subject to change without notice.
 */
package io.interface21.web.auth.app;

import org.ameba.annotation.ExcludeFromScan;
import org.ameba.app.AuthenticationConfigurer;
import org.ameba.exception.SecurityException;
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