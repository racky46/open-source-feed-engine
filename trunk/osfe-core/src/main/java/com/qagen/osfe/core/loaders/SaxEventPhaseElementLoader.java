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
import com.qagen.osfe.core.vo.EventTypeEnum;
import com.qagen.osfe.core.vo.PhaseInfo;
import com.qagen.osfe.core.vo.SaxEventPhaseInfo;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class SaxEventPhaseElementLoader extends Loader {
  private enum ELEMENT {
    saxEventPhase,
    phases,
    phase
  }

  private enum ATTRIBUTE {
    nameSpace,
    event
  }

  private List<SaxEventPhaseInfo> phases;

  public SaxEventPhaseElementLoader(Element root, String elementName) {
    super(root, elementName);
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  /**
   * Contains the instructions for parsing data from child elements within
   * the parent element. If the element is complex, other loaders should
   * be used within this method to parse different sub elements.
   *
   * @param parent container of child element to parse.
   */
  public void load(Element parent) {
    phases = new ArrayList<SaxEventPhaseInfo>();

    final Iterator it = parent.elementIterator(ELEMENT.saxEventPhase.name());

    while (it.hasNext()) {
      final Element element = (Element) it.next();
      final String nameSpace = DomReader.getRequiredValue(element, ATTRIBUTE.nameSpace.name());
      final String eventType = DomReader.getRequiredValue(element, ATTRIBUTE.event.name());

      final EventTypeEnum event = EventTypeEnum.valueOf(eventType);
      final List<PhaseInfo> phaseInfoList = loadPhases(element);

      final SaxEventPhaseInfo info = new SaxEventPhaseInfo(nameSpace, event, phaseInfoList);

      phases.add(info);
    }
  }

  private List<PhaseInfo> loadPhases(Element parent) {
    final List<PhaseInfo> list = new ArrayList<PhaseInfo>();
    final Iterator it = parent.element(ELEMENT.phases.name()).elementIterator(ELEMENT.phase.name());

    while (it.hasNext()) {
//      final PhaseInfo phaseInfo = PhaseInfoLoader.createPhaseInfo((Element) it.next());
//      list.add(phaseInfo);
    }

    return list;
  }

  /**
   * Useful for knowing the number of data objects created from the number of
   * elements that were parsed.  Also useful to know if the element set was
   * empty.
   *
   * @return 0 if no elements were parsed.
   */
  public int size() {
    return phases.size();
  }

  /**
   * Returns a reference to the list of SaxEventPhaseInfo objects.
   *
   * @return reference to the list of SaxEventPhaseInfo objects.
   */
  public List<SaxEventPhaseInfo> getPhases() {
    return phases;
  }
}
