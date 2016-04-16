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

import java.util.Properties;

import org.ameba.annotation.ExcludeFromScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

/**
 * A AuthenticationProviderConfiguration.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@ExcludeFromScan
@Configuration
public class AuthenticationProviderConfiguration implements BeanFactoryAware {

    public static String authenticationUrl;
    @Autowired
    private RestTemplate restTemplate;
    private BeanFactory beanFactory;

    public @Bean HandlerMapping authControllerHandlerMapping() {
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        Properties mappings = new Properties();
        mappings.put("/auth/authenticate", "oAuth2AuthenticationController");
        mapping.setMappings(mappings);
        return mapping;
    }

    public @Bean AuthenticationProvider httpAuthenticationProvider() {
        HttpAuthenticationProvider ap = new HttpAuthenticationProvider(new DefaultAuthenticationDelegate(restTemplate, authenticationUrl));
        try {
            ap.setPasswordEncoder(beanFactory.getBean(PasswordEncoder.class));
        } catch (BeansException e) {
            // no need to set en encoder .... can be optimized here
        }
        return ap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
