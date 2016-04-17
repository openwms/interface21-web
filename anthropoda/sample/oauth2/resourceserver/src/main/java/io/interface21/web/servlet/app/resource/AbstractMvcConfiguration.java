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

import java.util.List;
import java.util.Properties;

import org.ameba.system.NestedReloadableResourceBundleMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * An AbstractMvcConfiguration class can be extended to inherit a pre-configured {@code MessageSource} and ressource handlers
 *
 * @deprecated Instead extend the same class from Ameba lib 1.4.2
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.4.1
 */
@Deprecated
public abstract class AbstractMvcConfiguration extends WebMvcConfigurerAdapter {

    /**
     * Initializes a {@link MessageSource MessageSource} bean with the given set of basenames.
     *
     * @return The messageSource
     */
    @Bean
    public MessageSource messageSource() {
        NestedReloadableResourceBundleMessageSource messageSource = new NestedReloadableResourceBundleMessageSource();
        messageSource.setBasenames(getBasenames());
        messageSource.setCommonMessages(getCommonMessages());
        return messageSource;
    }

    /**
     * {@inheritDoc}
     *
     * Add support for static resources and webjars.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    /**
     * {@inheritDoc}
     *
     * Error page is tried to be resolved at public/error, the exception attribute is set to {@literal exception}.
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        SimpleMappingExceptionResolver smer = new SimpleMappingExceptionResolver();
        smer.setDefaultErrorView("public/error");
        smer.setExceptionAttribute("exception");
        exceptionResolvers.add(smer);
    }

    /**
     * Override to provide an array of basenames that are resolved to properties files. The default abstract implementation just returns one
     * basename {@literal classpath:/META-INF/resources/WEB-INF/i18n/auth}.
     *
     * @return Default basename
     */
    protected String[] getBasenames() {
        return new String[]{"classpath:/META-INF/resources/WEB-INF/i18n/auth"};
    }

    /**
     * Override to provide common messages that are not within properties files but should be added programmatically.
     *
     * @return Properties of messages to add
     */
    protected Properties getCommonMessages() {return new Properties();}
}
