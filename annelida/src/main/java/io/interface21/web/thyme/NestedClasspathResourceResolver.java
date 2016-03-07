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
package io.interface21.web.thyme;

import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.util.Validate;

import java.io.InputStream;

/**
 * A NestedClasspathResourceResolver is capable to load thymeleaf template resources from within jar files.
 *
 * Use {@code classpath*:} notation or similar like supported by Spring's ResourceLoader
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 * @see org.springframework.core.io.ResourceLoader
 */
public class NestedClasspathResourceResolver implements IResourceResolver {

    private ResourceLoader rl;

    /**
     * Constructor with Spring's ResourceLoader to use.
     *
     * @param rl ResourceLoader
     */
    public NestedClasspathResourceResolver(ResourceLoader rl) {
        this.rl = rl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "NestedClasspathResourceResolver";
    }

    /**
     * {@inheritDoc}
     * <p>
     * Expected a resource location accepted by Spring's ResourceLoader.
     *
     * @see org.springframework.core.io.ResourceLoader
     */
    @Override
    public InputStream getResourceAsStream(TemplateProcessingParameters templateProcessingParameters, String resourceName) {
        Validate.notNull(resourceName, "Resource name cannot be null");
        try {
            return rl.getResource(resourceName).getInputStream();
        } catch (Exception e) {
            // resource not found results in returning null, see interface documentation
        }
        return null;
    }
}
