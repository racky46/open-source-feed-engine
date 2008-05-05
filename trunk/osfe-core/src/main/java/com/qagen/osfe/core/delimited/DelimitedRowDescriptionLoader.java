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
package com.qagen.osfe.core.delimited;

import com.qagen.osfe.common.utils.DomReader;
import com.qagen.osfe.core.column.ColumnDescription;
import com.qagen.osfe.core.row.RowDescription;
import com.qagen.osfe.core.row.RowDescriptionLoader;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DelimitedRowDescriptionLoader extends RowDescriptionLoader {
  public static enum ELEMENT {
    delimitedRows,
    delimitedRow,
    delimitedColumn
  }

  public static enum ATTRIBUTE {
    description,
    minusRowCount,
    delimiterValue
  }

  protected String delimiter;
  protected Integer minusRowCount;
  protected Map<String, RowDescription> rows;

  public DelimitedRowDescriptionLoader(Element root) {
    super(root, ELEMENT.delimitedRows.name());
  }

  protected void load(Element parent) {
    delimiter = DomReader.getRequiredValue(parent, ATTRIBUTE.delimiterValue.name());
    minusRowCount = DomReader.getRequiredIntValue(parent, ATTRIBUTE.minusRowCount.name());
    rows = parceRows(parent);
  }

  public int size() {
    return rows.size();
  }

  @SuppressWarnings("unchecked")
  private Map<String, RowDescription> parceRows(Element parent) {
    final List<Element> elements = parent.elements();
    final Map<String, RowDescription> rows = new HashMap<String, RowDescription>();

    for (Element element : elements) {
      final List<ColumnDescription> columnDescriptions = parseColumns(element);
      final RowDescription rowDescription = new RowDescription(element, columnDescriptions);

      rows.put(rowDescription.getName(), rowDescription);
    }

    return rows;
  }

  @SuppressWarnings("unchecked")
  private List<ColumnDescription> parseColumns(final Element root) {
    final List<Element> elements = root.elements();
    final List<ColumnDescription> columnDescriptions = new ArrayList<ColumnDescription>();

    for (Element element : elements) {
      columnDescriptions.add(new DelimitedColumn(element));
    }

    return columnDescriptions;
  }

  public Map<String, RowDescription> getRows() {
    return rows;
  }

  public Integer getMinusRowCount() {
    return minusRowCount;
  }

  public String getDelimiter() {
    return delimiter;
  }

}
