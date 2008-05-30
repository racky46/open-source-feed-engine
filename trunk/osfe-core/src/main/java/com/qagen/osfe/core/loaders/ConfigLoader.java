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

public class ConfigLoader extends Loader {
  public enum ELEMENT {
    configLoaders,
    configLoader
  }

  public enum ATTRIBUTE {
    name,
    className,
  }

  private Map<String, String> map;
  private List<Clazz> list;

  public ConfigLoader(Element root) {
    super(root, ELEMENT.configLoaders.name());
  }

  protected void load(Element parent) {
    map = new HashMap<String, String>();
    list = new ArrayList<Clazz>();
    final Iterator it = parent.elementIterator();

    while (it.hasNext()) {
      final Element element = (Element) it.next();
      final String name = element.getName();
      final String value = element.getTextTrim();

      map.put(name, value);
      list.add(new Clazz(name, value));
    }
  }

  public int size() {
    return list.size();
  }

  public Map<String, String> getMap() {
    return map;
  }

  public List<Clazz> getList() {
    return list;
  }
}
