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
package com.qagen.osfe.webapp.web.interceptor;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.*;
import net.sourceforge.stripes.exception.StripesServletException;
import net.sourceforge.stripes.util.Log;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qagen.osfe.webapp.web.context.OsfeActionBeanContext;
import com.qagen.osfe.webapp.web.security.Secure;
import com.qagen.osfe.webapp.web.security.StripesSecurityManager;
import com.qagen.osfe.dataAccess.vo.FeedUser;

/**
 * Author: Gregg Bolinger
 * <p/>
 */
@Intercepts(value = LifecycleStage.HandlerResolution)
public class SecurityRoleInterceptor implements Interceptor {

  private ThreadLocal<FeedUser> currentUser = new ThreadLocal<FeedUser>();

  private static final Log logger = Log.getInstance(SecurityRoleInterceptor.class);
  private Map<SecureKey, Boolean> cache = new HashMap<SecureKey, Boolean>(128);

  public Resolution intercept(ExecutionContext ctx) throws Exception {
    final Configuration config = StripesFilter.getConfiguration();
    final String clazz = config.getBootstrapPropertyResolver().getProperty("StripesSecurityManager.class");
    final OsfeActionBeanContext context = (OsfeActionBeanContext) ctx.getActionBeanContext();
    final StripesSecurityManager securityManager = getSecurityManager(context, clazz);
    final ActionResolver resolver = config.getActionResolver();
    final ActionBean actionBean = resolver.getActionBean(context);
    final Class<? extends ActionBean> beanClass = actionBean.getClass();
    String eventName = resolver.getEventName(beanClass, context);

    // Look up the event handler method
    final Method handler;
    if (eventName != null) {
      handler = resolver.getHandler(beanClass, eventName);
    } else {
      handler = resolver.getDefaultHandler(beanClass);
      if (handler != null) {
        context.setEventName(resolver.getHandledEvent(handler));
      }
    }

    // Insist that we have a handler
    if (handler == null) {
      throw new StripesServletException(
          "No handler method found for request with  ActionBean ["
              + beanClass.getName() + "] and eventName [ "
              + eventName + "]");
    }

    if (!isAuthorizedRequest(handler, beanClass, securityManager, context)) {
      return securityManager.getUnauthorizedResolution();
    }

    // Stick the user in a threadlocal so that we can use in below the web tier
    currentUser.set(context.getFeedUser());
    return ctx.proceed();

  }

  private boolean isAuthorizedRequest(Method method, Class<?> beanClass, StripesSecurityManager securityManager, ActionBeanContext context) {
    SecureKey secureKey = new SecureKey(method, beanClass);
    logger.debug("Checking method: " + method.toGenericString());
    Boolean authorized = null; //cache.get(secureKey);
    if (authorized != null) {
      logger.debug("Found cached value: " + authorized);
      return authorized;
    }

    Secure annotation = method.getAnnotation(Secure.class);
    if (annotation != null) {
      logger.debug("Checking for role on method level annotation");
      List<String> roles = Arrays.asList(annotation.roles());
      if (!securityManager.isUserInRole(roles, context)) {
        logger.debug("Not authorized");
        authorized = false;
      } else {
        logger.debug("authorized");
        authorized = true;
      }

    } else {
      logger.debug("Checking class level annotation");
      Class<?> clazz = beanClass;
      do {
        annotation = clazz.getAnnotation(Secure.class);
        clazz = clazz.getSuperclass();
      }
      while (clazz != null && annotation == null);
      if (annotation != null) {
        List<String> roles = Arrays.asList(annotation.roles());
        if (!securityManager.isUserInRole(roles, context)) {
          authorized = false;
        } else {
          authorized = true;
        }


      } else {
        // Assume request did not require authentication if there are no class or method
        // level annotations
        authorized = true;
      }

    }
    cache.put(secureKey, authorized);
    return authorized;
  }

  private StripesSecurityManager getSecurityManager(ActionBeanContext context, String sm) {
    if (sm != null) {
      try {
        Class clazz = Class.forName(sm.trim());
        return (StripesSecurityManager) clazz.newInstance();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        return null;
      } catch (InstantiationException e) {
        e.printStackTrace();
        return null;
      } catch (IllegalAccessException e) {
        e.printStackTrace();
        return null;
      }


    }
    return null;
  }

  private final class SecureKey {
    private Method method;
    private Class<?> beanClass;
    private int hashCode;

    public SecureKey(Method method, Class<?> beanClass) {
      super();
      this.method = method;
      this.beanClass = beanClass;
      this.hashCode = method.hashCode() * 37 + beanClass.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      SecureKey that = (SecureKey) obj;
      return this.method.equals(that.method)
          && this.beanClass.equals(that.beanClass);
    }

    @Override
    public int hashCode() {
      return hashCode;
    }

    @Override
    public String toString() {
      return beanClass.getName() + "." + method.getName();
    }
  }


}
	