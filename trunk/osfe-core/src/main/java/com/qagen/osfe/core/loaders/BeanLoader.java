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
package com.qagen.osfe.core.loaders;

import com.qagen.osfe.common.utils.DomReader;
import com.qagen.osfe.core.*;
import com.qagen.osfe.core.utils.BeanInjector;
import com.qagen.osfe.core.vo.Property;
import com.qagen.osfe.core.vo.Reference;
import org.dom4j.Element;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: htaylor
 * Date: Aug 6, 2008
 * Time: 6:02:48 PM
 */
public class BeanLoader extends Loader {
  public static enum ELEMENT {
    bean,
    beans
  }

  public enum ATTRIBUTE {
    name("name"),
    reference("ref"),
    value("value"),
    className("class");

    public String token;

    public String getToken() {
      return token;
    }

    ATTRIBUTE(String token) {
      this.token = token;
    }
  }

  public Map<String, Object> beanMap;
  private EngineContext context;

  public BeanLoader(Element root, EngineContext context) {
    this.context = context;
    load(root, ELEMENT.beans.name());
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    load(root, ELEMENT.beans.name());
  }

  /**
   * Contains the instructions for parsing data from child elements within
   * the parent element. If the element is complex, other loaders should
   * be used within this method to parse different sub elements.
   *
   * @param parent container of child element to parse.
   */
  public void load(Element parent) {
    beanMap = new HashMap<String, Object>();

    // Pass One - instantiate all classes
    loadBeansIntoBeanMap(beanMap, parent);

    // Pass Two - inject references to other classes
    loadBeanReferences(beanMap, parent);
  }

  /**
   * Pass One - instantiate all classes
   */
  private void loadBeansIntoBeanMap(Map<String, Object> beanMap, Element parent) {
    final Iterator categoryIterator = parent.elementIterator();

    while (categoryIterator.hasNext()) {
      final Element category = (Element) categoryIterator.next();
      final Iterator it = category.elementIterator(ELEMENT.bean.name());

      while (it.hasNext()) {
        final Element element = (Element) it.next();
        final String name = DomReader.getRequiredValue(element, ATTRIBUTE.name.getToken());
        final String value = DomReader.getRequiredValue(element, ATTRIBUTE.className.getToken());
        final Object clazz = loadClass(element, value);

        beanMap.put(name, clazz);
      }
    }
  }

  /**
   * Pass Two - inject references to other classes
   */
  private void loadBeanReferences(Map<String, Object> beanMap, Element parent) {
    final Iterator categoryIterator = parent.elementIterator();

    while (categoryIterator.hasNext()) {
      final Element category = (Element) categoryIterator.next();
      final Iterator it = category.elementIterator(ELEMENT.bean.name());

      while (it.hasNext()) {
        final Element element = (Element) it.next();
        final String name = DomReader.getValue(element, ATTRIBUTE.name.getToken());
        final Object bean = beanMap.get(name);

        injectReferences(bean, element, beanMap);
        injectProperties(bean, element);
      }
    }
  }

  private static enum SPECIFIC_FIELD {
    context,
    root,
    name
  }

  public Object loadClass(Element element, String className) {
    try {
      final Class clazz = Class.forName(className);
      final Class argTypes[] = new Class[]{};
      final Constructor constructor = clazz.getConstructor(argTypes);
      final Object bean = constructor.newInstance();

      if (bean instanceof Contextable) {
        BeanInjector.injectRequiredBean(bean, SPECIFIC_FIELD.context.name(), context);
      }

      if (bean instanceof Configurable) {
        BeanInjector.injectRequiredBean(bean, SPECIFIC_FIELD.root.name(), context.getFeedDocument().getRootElement());
      }

      final String name = element.attributeValue(SPECIFIC_FIELD.name.name());
      if (name != null) {
        BeanInjector.injectNonRequiredBean(bean, SPECIFIC_FIELD.name.name(), name);
      }

      return bean;

    } catch (ClassNotFoundException e) {
      throw new FeedErrorException(e);
    } catch (NoSuchMethodException e) {
      throw new FeedErrorException(e);
    } catch (InstantiationException e) {
      throw new FeedErrorException(e);
    } catch (IllegalAccessException e) {
      throw new FeedErrorException(e);
    } catch (InvocationTargetException e) {
      throw new FeedErrorException(e);
    }
  }

  public void injectReferences(Object bean, Element element, Map<String, Object> map) {
    final ReferencesLoader loader = new ReferencesLoader(element);
    final List<Reference> references = loader.getReferenceList();

    if (references == null) {
      return;
    }

    try {
      for (Reference reference : references) {
        final String referenceId = reference.getReference();

        BeanInjector.injectRequiredBean(bean, referenceId, map.get(referenceId));
      }
    } catch (IllegalArgumentException e) {
      throw new FeedErrorException(e);
    }
  }

  public void injectProperties(Object bean, Element element) {
    final PropertiesLoader loader = new PropertiesLoader(element);
    final List<Property> properties = loader.getPropertyList();

    if (properties == null) {
      return;
    }

    try {
      for (Property property : properties) {
        final String name = property.getName();
        final String value = property.getValue();
        final String type = property.getType();
        final String format = property.getFormat();

        BeanInjector.injectRequiredValue(bean, name, value, type, format);
      }
    } catch (IllegalArgumentException e) {
      throw new FeedErrorException(e);
    }
  }

  /**
   * Useful for knowing the number of data objects created from the number of
   * elements that were parsed.  Also useful to know if the element set was
   * empty.
   *
   * @return 0 if no elements were parsed.
   */
  public int size() {
    return beanMap.size();
  }

  public Map<String, Object> getBeanMap() {
    return beanMap;
  }

  public List<Object> getBeans() {
    final List<Object> beans = new ArrayList<Object>();

    for (Object bean : beanMap.values()) {
      beans.add(bean);
    }

    return beans;
  }
}
