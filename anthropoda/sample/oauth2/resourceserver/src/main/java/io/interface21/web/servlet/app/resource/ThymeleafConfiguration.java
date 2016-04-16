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

import javax.annotation.PostConstruct;

import io.interface21.web.thyme.NestedClasspathResourceResolver;
import org.ameba.http.AbstractMvcConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 * A ThymeleafConfiguration is exclusively responsible to extend and override the default Thymeleaf configuration.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@Configuration
class ThymeleafConfiguration extends AbstractMvcConfiguration {

    @Autowired
    private ResourceLoader rl;

    @PostConstruct
    public void checkTemplateLocationExists() {
        new TemplateLocation("classpath:/META-INF/resources/WEB-INF/templates/");
    }

    @Bean
    public TemplateResolver defaultTemplateResolver() {
        TemplateResolver resolver = new TemplateResolver();
        resolver.setResourceResolver(thymeleafResourceResolver(rl));
        resolver.setPrefix("classpath:/META-INF/resources/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        return resolver;
    }

    @Bean
    public NestedClasspathResourceResolver thymeleafResourceResolver(ResourceLoader rl) {
        return new NestedClasspathResourceResolver(rl);
    }
}
