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
import com.qagen.osfe.core.vo.Components;
import com.qagen.osfe.core.vo.Property;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: htaylor
 * Date: Aug 6, 2008
 * Time: 10:59:47 AM
 */
public class ComponentLoader extends Loader {
  public enum ELEMENT {
    feedId,
    literal,
    timestamp,
    separator,
    lineCount,
    delimiter,
    component,
    components,
    sequenceNumber
  }

  public enum ATTRIBUTE {
    name,
    value,
    format,
    initialize
  }

  private String name;
  private List<Property> list;


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
  public ComponentLoader() {
  }

  /**
   * Constructor
   *
   * @param root the element from which contains the sub element
   *             that will be parsed and referenced as the parent element.
   * @param name the name of the sub element to retrieve for data
   *             extraction.
   */
  public ComponentLoader(Element root, String name) {
    super(root, name);
  }

  /**
   * Constructor
   *
   * @param root
   */
  public ComponentLoader(Element root) {
    super(root, ELEMENT.components.name());
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    load(root, ELEMENT.components.name());
  }

  /**
   * Contains the instructions for parsing data from child elements within
   * the parent element. If the element is complex, other loaders should
   * be used within this method to parse different sub elements.
   *
   * @param parent container of child element to parse.
   */
  public void load(Element parent) {
    list = new ArrayList<Property>();
    name = parent.attributeValue(ATTRIBUTE.name.name());

    final Iterator it = parent.elementIterator();

    while (it.hasNext()) {
      final Element element = (Element) it.next();
      final String name = element.getName();

      switch (ELEMENT.valueOf(name)) {
        case feedId:
          list.add(new Property(ELEMENT.feedId.name(), null));
          break;
        case delimiter:
          list.add(new Property(ELEMENT.delimiter.name(), null));
          break;
        case lineCount:
          addPropertyWithFormat(element, list);
          break;
        case sequenceNumber:
          addSequenceNumber(element, list);
          break;
        case separator:
          addPropertyWithValue(element, list);
          break;
        case literal:
          addPropertyWithValue(element, list);
          break;
        case timestamp:
          addPropertyWithFormat(element, list);
          break;
      }
    }
  }

  private void addSequenceNumber(Element element, List<Property> components) {
    final String value = element.attributeValue(ATTRIBUTE.initialize.name());

    components.add(new Property(element.getName(), value));
  }

  private void addPropertyWithValue(Element element, List<Property> components) {
    final String value = element.attributeValue(ATTRIBUTE.value.name());

    components.add(new Property(element.getName(), value));
  }

  private void addPropertyWithFormat(Element element, List<Property> components) {
    final String value = element.attributeValue(ATTRIBUTE.format.name());

    components.add(new Property(element.getName(), value));
  }

  /**
   * Usefull for knowing the number of data objects created from the number of
   * elements that were parsed.  Also useful to know if the element set was
   * empty.
   *
   * @return 0 if no elements were parsed.
   */
  public int size() {
    return list.size();
  }

  /**
   * Retrieves a reference to the components object.
   *
   * @return reference to the components object.
   */
  public Components getComponents() {
    return new Components(name, list);
  }

}
