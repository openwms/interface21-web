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

import java.util.ArrayList;
import java.util.List;

import io.interface21.web.servlet.app.oauth2.AuthenticationProviderConfiguration;
import io.interface21.web.servlet.app.oauth2.AuthorizationServerConfiguration;
import io.interface21.web.servlet.app.oauth2.EnableOAuth2;
import io.interface21.web.servlet.app.oauth2.OAuth2Configuration;
import io.interface21.web.servlet.app.oauth2.OperationMode;
import io.interface21.web.servlet.app.oauth2.ResourceServerConfiguration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

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

        Class<?> annoType = EnableOAuth2.class;
        AnnotationAttributes attributes = AnnotationConfigUtils.attributesFor(importingClassMetadata, annoType);
        if (attributes == null) {
            throw new IllegalArgumentException(String.format(
                    "@%s is not present on importing class '%s' as expected",
                    annoType.getSimpleName(), importingClassMetadata.getClassName()));
        }
        List<String> configurationClasses = new ArrayList<>();
        if (attributes.getEnum("mode") == OperationMode.COMBINED) {
            configurationClasses.add(ResourceServerConfiguration.class.getName());
            configurationClasses.add(AuthorizationServerConfiguration.class.getName());
            OAuth2Configuration.authenticationProviderBean = attributes.getString("authenticationProviderBean");
        }
        if (attributes.getEnum("mode") == OperationMode.RESOURCES) {
            configurationClasses.add(ResourceServerConfiguration.class.getName());

            String authenticationUrl = attributes.getString("authenticationUrl");
            Assert.hasText(authenticationUrl, "Standalone resource servers need to have an authentication endpoint configured, property authenticationUrl");
            AuthenticationProviderConfiguration.authenticationUrl = authenticationUrl;
            OAuth2Configuration.authenticationProviderBean = "httpAuthenticationProvider";
            configurationClasses.add(AuthenticationProviderConfiguration.class.getName());
        }
        if (attributes.getEnum("mode") == OperationMode.AUTHORIZATIONS) {
            configurationClasses.add(AuthorizationServerConfiguration.class.getName());
            OAuth2Configuration.authenticationProviderBean = attributes.getString("authenticationProviderBean");
        }
        configurationClasses.add(OAuth2Configuration.class.getName());
        return configurationClasses.toArray(new String[configurationClasses.size()]);
    }
}
