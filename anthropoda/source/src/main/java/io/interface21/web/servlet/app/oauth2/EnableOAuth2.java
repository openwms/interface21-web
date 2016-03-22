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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.OAuth2ImportSelector;

/**
 * A EnableOAuth2.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(OAuth2ImportSelector.class)
public @interface EnableOAuth2 {

    /**
     * Default is {@literal true} and activates the AuthorizationServer as part of the deployment unit where this annotation is put on a
     * configuration class.
     *
     * @return if the AuthorizationServer is enabled or not.
     */
    boolean asAuthorizationServer() default false;

    /**
     * Default is {@literal true} and activates the ResourceServer as part of the deployment unit where this annotation is put on a
     * configuration class.
     *
     * @return if the ResourceServer is enabled or not.
     */
    boolean asResourceServer() default false;

    /**
     * The Spring bean name of the {@link org.springframework.security.authentication.AuthenticationProvider AuthenticationProvider} that is
     * used to authenticate the user.
     *
     * @return Spring bean name as String
     */
    String authenticationProviderBean() default "";
}
