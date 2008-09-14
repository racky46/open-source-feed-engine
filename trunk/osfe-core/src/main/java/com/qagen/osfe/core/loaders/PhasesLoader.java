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
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PhasesLoader extends Loader {
  public enum ELEMENT {
    phase,
    phases
  }

  private enum ATTRIBUTE {
    ref,
    enable
  }

  private List<PhaseInfo> list;

  /**
   * Constructor<p>
   * <p/>
   * This constructor in normally used for automatic dependency injection
   * when the feed config file is loaded.<p>
   * If this constructor is used then the following setters must be called:
   * <ul>
   * <li>setRoot(Element)
   * <li>setName(String)
   * <ul>
   */
  public PhasesLoader() {
  }

  /**
   * Constructor
   *
   * @param root the element from which contains the sub element
   *             that will be parsed and referenced as the parent element.
   * @param name the name of the sub element to retrieve for data
   *             extraction.
   */
  public PhasesLoader(Element root, String name) {
    super(root, name);
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    load(root, ELEMENT.phases.name());
  }

  public void load(Element parent) {
    list = new ArrayList<PhaseInfo>();

    final Iterator it = parent.elementIterator(ELEMENT.phase.name());

    while (it.hasNext()) {
      final Element element = (Element) it.next();
      final String ref = DomReader.getRequiredValue(element, ATTRIBUTE.ref.name());
      final Boolean enable = DomReader.getRequiredBooleanValue(element, ATTRIBUTE.enable.name());

      list.add(new PhaseInfo(ref, enable));
    }
  }

  public int size() {
    return list.size();
  }

  public List<PhaseInfo> getList() {
    return list;
  }
}
