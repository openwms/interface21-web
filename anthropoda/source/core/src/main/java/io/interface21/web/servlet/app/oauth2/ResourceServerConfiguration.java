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
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * A ResourceServerConfiguration.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.2
 */
@ExcludeFromScan
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    public
    @Bean
    ResourceServerTokenServices remoteTokenServices() {
        RemoteTokenServices rts = new RemoteTokenServices();
        rts.setClientId("type-server");
        rts.setClientSecret("1a9030fbca47a5b2c28e92f19050bb77824b5ad1");
        rts.setCheckTokenEndpointUrl("http://localhost:8083/oauth/check_token");
        return rts;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("oauth2-resource");
        resources.tokenServices(remoteTokenServices());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/public/**", "/webjars/**", "/resources/**").permitAll()
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
}
