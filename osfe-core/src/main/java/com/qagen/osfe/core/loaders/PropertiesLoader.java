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
import com.qagen.osfe.core.Loader;
import com.qagen.osfe.core.vo.Property;
import org.dom4j.Element;

import java.util.*;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class PropertiesLoader extends Loader {
  public enum ELEMENT {
    properties,
    property
  }

  public enum ATTRIBUTE {
    name,
    value,
    type,
    format
  }

  private Map<String, String> map;
  private List<Property> list;

  /**
   * Constructor
   */
  public PropertiesLoader() {
  }

  /**
   * Constructor
   *
   * @param root reference to root of a DOM document.
   */
  public PropertiesLoader(Element root) {
    super(root, ELEMENT.properties.name());
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    load(root, ELEMENT.properties.name());
  }

  public void load(Element parent) {
    map = new HashMap<String, String>();
    list = new ArrayList<Property>();

    final Iterator it = parent.elementIterator(ELEMENT.property.name());

    while (it.hasNext()) {
      final Element element = (Element) it.next();
      final String name = DomReader.getRequiredValue(element, ATTRIBUTE.name.name());
      final String value = DomReader.getRequiredValue(element, ATTRIBUTE.value.name());
      final String type = DomReader.getRequiredValue(element, ATTRIBUTE.type.name());
      final String format = DomReader.getValue(element, ATTRIBUTE.format.name());

      map.put(name, value);
      list.add(new Property(name, value, type, format));
    }
  }

  public int size() {
    return list != null ? list.size() : 0;
  }

  public Map<String, String> getPropertyMap() {
    return map;
  }

  public List<Property> getPropertyList() {
    return list;
  }
}
  
