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
import com.qagen.osfe.core.vo.Reference;
import org.dom4j.Element;

import java.util.*;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class ReferencesLoader extends Loader {
  public enum ELEMENT {
    references,
    reference
  }

  public enum ATTRIBUTE {
    name,
    ref
  }

  private Map<String, String> map;
  private List<Reference> list;

  /**
   * Constructor
   */
  public ReferencesLoader() {
  }

  /**
   * Constructor
   *
   * @param root reference to root of a DOM document.
   */
  public ReferencesLoader(Element root) {
    super(root, ELEMENT.references.name());
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    load(root, ELEMENT.references.name());
  }

  public void load(Element parent) {
    map = new HashMap<String, String>();
    list = new ArrayList<Reference>();

    final Iterator it = parent.elementIterator(ELEMENT.reference.name());

    while (it.hasNext()) {
      final Element element = (Element) it.next();
      final String name = DomReader.getRequiredValue(element, ATTRIBUTE.name.name());
      final String reference = DomReader.getRequiredValue(element, ATTRIBUTE.ref.name());

      map.put(name, reference);
      list.add(new Reference(name, reference));
    }
  }

  public int size() {
    return list != null ? list.size() : 0;
  }

  public Map<String, String> getReferenceMap() {
    return map;
  }

  public List<Reference> getReferenceList() {
    return list;
  }
}