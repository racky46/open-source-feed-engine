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
import com.qagen.osfe.core.vo.PhaseInfo;
import com.qagen.osfe.core.vo.Property;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PhaseElementLoader extends Loader {
  public enum ELEMENT {
    phase,
  }

  public enum ATTRIBUTE {
    name,
    enable,
    className
  }

  private List<PhaseInfo> list;

  public PhaseElementLoader(Element root, String elementName) {
    super(root, elementName);
  }

  protected void load(Element parent) {
    list = new ArrayList<PhaseInfo>();

    final Iterator it = parent.elementIterator(ELEMENT.phase.name());

    while (it.hasNext()) {
      final Element element = (Element) it.next();
      final String name = DomReader.getRequiredValue(element, ATTRIBUTE.name.name());
      final Boolean enable = DomReader.getRequiredBooleanValue(element, ATTRIBUTE.enable.name());
      final String className = DomReader.getRequiredValue(element, ATTRIBUTE.className.name());

      final PropertiesConfigLoader loader = new PropertiesConfigLoader(element);
      final Map<String, String> propertyMap = loader.getPropertyMap();

      final PhaseInfo phaseInfo = new PhaseInfo(name, enable, className, propertyMap);

      list.add(phaseInfo);
    }
  }

  public int size() {
    return list.size();
  }

  public List<PhaseInfo> getList() {
    return list;
  }
}
