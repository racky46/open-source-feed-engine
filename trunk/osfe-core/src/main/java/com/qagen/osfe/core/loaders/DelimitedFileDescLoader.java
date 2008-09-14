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
import com.qagen.osfe.core.vo.Components;
import com.qagen.osfe.core.vo.DelimitedColumn;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class DelimitedFileDescLoader extends Loader {
  public static enum ELEMENT {
    headers,
    footers,
    detail,
    components,
    delimitedFileDescription
  }

  public static enum ATTRIBUTE {
    delimiterValue,
    endOfLineValue
  }

  private String delimiterValue;
  private String endOfLineValue;

  private List<Components> headers;
  private List<Components> footers;
  private List<DelimitedColumn> detail;

  public DelimitedFileDescLoader(Element root) {
    super(root, ELEMENT.delimitedFileDescription.name());
  }

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
  public DelimitedFileDescLoader() {
  }

  /**
   * Constructor
   *
   * @param root the element from which contains the sub element
   *             that will be parsed and referenced as the parent element.
   * @param name the name of the sub element to retrieve for data
   *             extraction.
   */
  public DelimitedFileDescLoader(Element root, String name) {
    super(root, name);
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    load(root, ELEMENT.delimitedFileDescription.name());
  }

  /**
   * Contains the instructions for parsing data from child elements within
   * the parent element. If the element is complex, other loaders should
   * be used within this method to parse different sub elements.
   *
   * @param parent container of child element to parse.
   */
  public void load(Element parent) {
    delimiterValue = DomReader.getRequiredValue(parent, ATTRIBUTE.delimiterValue.name());
    endOfLineValue = DomReader.getRequiredValue(parent, ATTRIBUTE.endOfLineValue.name());

    // These to variables need to be set in the context before components are created.
    context.setDelimiterValue(delimiterValue);
    context.setEndOfLineValue(endOfLineValue);

    {
      final Element element = parent.element(ELEMENT.detail.name());
      final DelimitedColumnLoader loader = new DelimitedColumnLoader(element);
      detail = loader.getDelimitedColumns();
    }

    headers = new ArrayList<Components>();
    footers = new ArrayList<Components>();

    Iterator it = parent.element(ELEMENT.headers.name()).elementIterator(ELEMENT.components.name());

    while (it.hasNext()) {
      final Element element = (Element) it.next();
      final ComponentLoader loader = new ComponentLoader();
      loader.load(element);

      headers.add(loader.getComponents());
    }

    it = parent.element(ELEMENT.footers.name()).elementIterator(ELEMENT.components.name());

    while (it.hasNext()) {
      final Element element = (Element) it.next();
      final ComponentLoader loader = new ComponentLoader();
      loader.load(element);

      footers.add(loader.getComponents());
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
    return 0;
  }

  public String getDelimiterValue() {
    return delimiterValue;
  }

  public String getEndOfLineValue() {
    return endOfLineValue;
  }

  public List<DelimitedColumn> getDetailColumns() {
    return detail;
  }

  public List<Components> getHeaders() {
    return headers;
  }

  public List<Components> getFooters() {
    return footers;
  }

}
