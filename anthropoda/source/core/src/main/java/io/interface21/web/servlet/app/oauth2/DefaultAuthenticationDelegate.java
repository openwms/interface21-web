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

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestTemplate;

/**
 * A DefaultAuthenticationDelegate.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
class DefaultAuthenticationDelegate implements AuthenticationDelegate {

    private RestTemplate restTemplate;
    private String authenticationUrl;

    public DefaultAuthenticationDelegate(RestTemplate restTemplate, String authenticationUrl) {
        this.restTemplate = restTemplate;
        this.authenticationUrl = authenticationUrl;
    }

    @Override
    public ResponseEntity<Collection<? extends GrantedAuthority>> authenticate(String username, char[] password) {
        HttpEntity<String> entity = new HttpEntity<>(createHeader(username, password));
        ParameterizedTypeReference<Collection<? extends GrantedAuthority>> typeWrapper = new ParameterizedTypeReference<Collection<? extends GrantedAuthority>>() {};
        return restTemplate.exchange(authenticationUrl, HttpMethod.GET, entity, typeWrapper);
    }

    protected HttpHeaders createHeader(String username, char[] password) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String authorization = username + ":" + Arrays.toString(password);
        httpHeaders.set("Authorization", "Basic " + Arrays.toString(Base64.getEncoder().encode(authorization.getBytes(Charset.forName("US-ASCII")))));
        return httpHeaders;
    }
}
