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

import java.security.Principal;

import io.interface21.web.servlet.app.oauth2.EnableOAuth2;
import io.interface21.web.servlet.app.oauth2.OperationMode;
import org.ameba.annotation.FilteredComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A ResourceServerApplication.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableAutoConfiguration
@RestController
@EnableResourceServer
@FilteredComponentScan(basePackages = "io.interface21")
@EnableOAuth2(mode = OperationMode.RESOURCES, authenticationUrl = "http://localhost:8083/auth/authenticate", authenticationProviderBean = "httpAuthenticationProvider")
public class ResourceServerApplication {

    @RequestMapping("/")
    public Principal user(Principal user) {
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }
}
