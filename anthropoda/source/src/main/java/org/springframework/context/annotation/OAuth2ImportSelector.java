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
package org.springframework.context.annotation;

import io.interface21.web.auth.app.oauth2.AuthorizationServerConfiguration;
import io.interface21.web.auth.app.oauth2.ResourceServerConfiguration;
import org.springframework.core.type.AnnotationMetadata;

/**
 * A OAuth2ImportSelector enables essential OAuth2 configuration based on the annotation metadata.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.2
 */
public class OAuth2ImportSelector implements ImportSelector {

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{AuthorizationServerConfiguration.class.getName(), ResourceServerConfiguration.class.getName()};
    }
}
