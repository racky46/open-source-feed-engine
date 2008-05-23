/* Copyright 2008 Hycel Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qagen.osfe.webapp.web.security;

import java.lang.annotation.*;

/**
 * Annotation used to secure ActionBean classes and methods with roles within the web appliction. If
 * the
 *
 * @Secure annotation is used, the default behavior is to deny access even if no roles are
 *         specified. If roles are specified, they are processed in the order of notAllowed,
 *         requiresAll, and then requiresAny. Any combination of these may be set. You must create a
 *         class that implements StripesSecurityManager and set this as your security manager in the
 *         web.xml config as shown below.
 *
 * <servlet> <servlet-name>StripesDispatcher</servlet-name>
 * <servlet-class>net.sourceforge.stripes.security.controller.DispatcherServlet</servlet-class> <init-param>
 * <param-name>SecurityManager</param-name>
 * <param-value>net.sourceforge.stripes.security.MyContainerSecurityManager</param-value>
 * </init-param> <load-on-startup>1</load-on-startup> </servlet>
 *
 * You can then add the
 * @Secure annotation to any of your action class or method declarations like so.
 *
 * @Secure( roles={"MANAGE_USERS", "MANAGE_CUSTOMERS"} )
 *
 * @author Nic Holbrook, Gregg Bolinger
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface Secure {
	String[] roles() default {};
}

