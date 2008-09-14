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
package com.qagen.osfe.dataAccess.context;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

/**
 * Author: Hycel Taylor
 * <p>
 * The DataAccessContext contains static methods that reference the Spring
 * application context and reference Spring beans.
 */
public class DataAccessContext {
  private static ApplicationContext context = null;

  /**
   * Retrieves a reference to the Spring application context.
   *
   * @return reference to the Spring application context.
   */
  public static ApplicationContext getContext() {
    if (context == null) {
      context = new ClassPathXmlApplicationContext("OSFEContext.xml");
    }
    return context;
  }

  /**
   * Retrieve a reference to a given Spring bean.
   *
   * @param name identifies the bean to reference.
   *
   * @return null if bean not identified.
   */
  public static Object getBean(String name) {
    final BeanFactory factory = getContext();
    return factory.getBean(name);
  }

  /**
   * Retrieves a reference to the applicationContext datasouce.
   *
   * @return reference to the datasource.
   */
  public static DataSource getDataSource() {
    return (DataSource) getBean("dataSource");
  }
}
