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
package io.interface21.web.servlet.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * A WebAuthenticationDetailsWithDomain expects an additional field called {@value #DOMAIN_NAME_PARAMETER} as part of
 * the login form.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.2
 */
class WebAuthenticationDetailsWithDomain extends WebAuthenticationDetails {

    private final String domainName;
    /** Field name to recognize as domain name where to login. */
    public static final String DOMAIN_NAME_PARAMETER = "j_Domain";

    /**
     * Records the remote address and will also set the session Id if a session already exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public WebAuthenticationDetailsWithDomain(HttpServletRequest request) {
        super(request);
        domainName = request.getParameter(DOMAIN_NAME_PARAMETER);
    }

    /**
     * Get the domainName.
     *
     * @return The domainName
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        WebAuthenticationDetailsWithDomain that = (WebAuthenticationDetailsWithDomain) o;

        return !(domainName != null ? !domainName.equals(that.domainName) : that.domainName != null);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (domainName != null ? domainName.hashCode() : 0);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() +
                "; " + DOMAIN_NAME_PARAMETER +
                ": " + this.getDomainName();
    }
}
