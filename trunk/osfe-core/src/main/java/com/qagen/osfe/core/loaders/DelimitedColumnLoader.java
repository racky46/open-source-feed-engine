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
import com.qagen.osfe.core.vo.DelimitedColumn;
import org.dom4j.Element;

import java.util.*;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class DelimitedColumnLoader extends Loader {
  public enum ELEMENT {
    delimitedColumns,
    delimitedColumn
  }

  public enum ATTRIBUTE {
    name,
    type,
    format
  }

  private Map<String, DelimitedColumn> delimitedColumnMap;
  private List<DelimitedColumn> delimitedColumns;

  /**
   * Constructor
   */
  public DelimitedColumnLoader() {
  }

  /**
   * Constructor
   *
   * @param root reference to root of a DOM document.
   */
  public DelimitedColumnLoader(Element root) {
    super(root, ELEMENT.delimitedColumns.name());
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    load(root, ELEMENT.delimitedColumns.name());
  }

  public void load(Element parent) {
    delimitedColumnMap = new HashMap<String, DelimitedColumn>();
    delimitedColumns = new ArrayList<DelimitedColumn>();

    final Iterator it = parent.elementIterator(ELEMENT.delimitedColumn.name());

    while (it.hasNext()) {
      final Element element = (Element) it.next();
      final String name = DomReader.getRequiredValue(element, ATTRIBUTE.name.name());
      final String type = DomReader.getRequiredValue(element, ATTRIBUTE.type.name());
      final String format = DomReader.getValue(element, ATTRIBUTE.format.name());
      final DelimitedColumn column = new DelimitedColumn(name, type, format);

      delimitedColumnMap.put(name, column);
      delimitedColumns.add(column);
    }
  }

  public int size() {
    return delimitedColumns != null ? delimitedColumns.size() : 0;
  }

  public Map<String, DelimitedColumn> getDelimitedColumnMap() {
    return delimitedColumnMap;
  }

  public List<DelimitedColumn> getDelimitedColumns() {
    return delimitedColumns;
  }
}