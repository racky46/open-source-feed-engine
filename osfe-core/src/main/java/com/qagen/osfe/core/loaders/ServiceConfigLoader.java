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

import com.qagen.osfe.core.Loader;
import com.qagen.osfe.core.vo.Clazz;
import org.dom4j.Element;

import java.util.*;

public class ServiceConfigLoader extends Loader {
  public enum ELEMENT {
    services,
    service
  }

  public enum ATTRIBUTE {
    name,
    className,
  }

  private Map<String, Clazz> map;
  private List<Clazz> list;

  public ServiceConfigLoader(Element root) {
    super(root, ELEMENT.services.name());
  }

  protected void load(Element parent) {
    map = new HashMap<String, Clazz>();
    list = new ArrayList<Clazz>();
    final Iterator it = parent.elementIterator();

    while (it.hasNext()) {
      final Element element = (Element) it.next();
      final String name = element.getName();
      final String value = element.getTextTrim();

      final PropertiesConfigLoader loader = new PropertiesConfigLoader(element);
      final Map<String, String> propertyMap = loader.getPropertyMap();

      final Clazz clazz = new Clazz(name, value, propertyMap);

      map.put(name, clazz);
      list.add(clazz);
    }
  }

  public int size() {
    return list.size();
  }

  public Map<String, Clazz> getMap() {
    return map;
  }

  public List<Clazz> getList() {
    return list;
  }
}
