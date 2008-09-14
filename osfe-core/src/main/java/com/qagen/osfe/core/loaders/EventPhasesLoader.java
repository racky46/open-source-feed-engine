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
import com.qagen.osfe.core.vo.PhaseInfo;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class EventPhasesLoader extends Loader {
  public enum ELEMENT {
    eventPhases
  }

  public enum ATTRIBUTE {
    batchSize
  }

  private Integer batchSize;
  private Map<String, List<PhaseInfo>> map;

  /**
   *
   */
  public EventPhasesLoader() {
  }

  public EventPhasesLoader(Element root) {
    super(root, ELEMENT.eventPhases.name());
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    load(root, ELEMENT.eventPhases.name());
  }

  public void load(Element parent) {
    map = new HashMap<String, List<PhaseInfo>>();
    batchSize = Integer.parseInt(parent.attributeValue(ATTRIBUTE.batchSize.name()));

    context.setBatchSize(batchSize);

    final Iterator it = parent.elementIterator();

    while (it.hasNext()) {
      final Element element = (Element) it.next();
      final String name = element.getName();
      final PhasesLoader phaseLoader = new PhasesLoader(parent, name);

      map.put(name, phaseLoader.getList());
    }
  }

  public int size() {
    return 0;  // Size is not applicable here.
  }

  public Integer getBatchSize() {
    return batchSize;
  }

  public List<PhaseInfo> getPhaseInfoList(String name) {
    return map.get(name);
  }
}
