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
package com.qagen.osfe.core.fixed;

import com.qagen.osfe.common.utils.DomReader;
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.column.ColumnDescription;
import com.qagen.osfe.core.row.RowDescription;
import com.qagen.osfe.core.row.RowDescriptionLoader;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Hycel Taylor
 * <p/>
 * This class performs the task of loading row descriptions from XML feed file
 * documents for fixed feed files.
 */
public class FixedRowDescriptionLoader extends RowDescriptionLoader {
  protected static enum ELEMENT {
    fixedRows,
    fixedRow,
    fixedColumn
  }

  protected static enum ATTRIBUTE {
    length,
    description,
    eolCharacter,
    minusRowCount
  }

  protected static enum EOL_CHARACTER {
    Windows("\r\n"),
    Unix("\n"),
    Mac("\r"),
    None("");

    private String value;

    EOL_CHARACTER(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  protected Integer minusRowCount;
  protected Map<String, RowDescription> rows;
  protected Integer eolLength;  // Used by fixed feed row description.  Defines the length eol characters.
  protected String eolCharacter; // Defines what the end of the line character should be.  Could be nothing.

  /**
   * Constructor
   */
  public FixedRowDescriptionLoader() {
  }

  /**
   * Constructor
   *
   * @param root the element from which contains the sub element
   *             that will be parsed and referenced as the parent element.
   */
  public FixedRowDescriptionLoader(Element root) {
    super(root, ELEMENT.fixedRows.name());
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    load(root, ELEMENT.fixedRows.name());
  }

  /**
   * Returns a map of RowDescription objects. The map will often contain the
   * following RowDescription objects.
   * <ul>
   * <li> header - describes the attributes of the header row of a feed document.
   * <li> detail - describes the attributes of the detail row of a feed document.
   * <li> footer - describes the attributes of the footer row of a feed document.
   * </ul>
   *
   * @return map of RowDescription objects.
   */
  public Map<String, RowDescription> getRows() {
    return rows;
  }

  /**
   * Contains the instructions for parsing data from child elements within
   * the parent element. If the element is complex, other loaders should
   * be used within this method to parse different sub elements.
   *
   * @param parent container of child element to parse.
   */
  public void load(Element parent) {
    minusRowCount = DomReader.getRequiredIntValue(parent, ATTRIBUTE.minusRowCount.name());
    eolCharacter = DomReader.getValue(parent, ATTRIBUTE.eolCharacter.name());
    eolLength = getEOLLength(eolCharacter);

    rows = parceRows(parent);
  }

  /**
   * Retrieve the end of line character and determine its' length.
   *
   * @param name defines the eol enumation type to lookup.
   *             Name may be one of the choices:
   *             <ul>
   *             <li>Windows</li>
   *             <li>Linux</li>
   *             <li>Mac</li>
   *             <li>None</li>
   *             </ul>
   * @return length of the number of characters for the given eol.
   */
  private int getEOLLength(String name) {
    try {
      return EOL_CHARACTER.valueOf(name).toString().length();
    } catch (IllegalArgumentException e) {
      throw new FeedErrorException(e);
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
    return rows.size();
  }

  @SuppressWarnings("unchecked")
  private Map<String, RowDescription> parceRows(Element parent) {
    final List<Element> elements = parent.elements();
    final Map<String, RowDescription> rows = new HashMap<String, RowDescription>();

    for (Element element : elements) {
      final List<ColumnDescription> columnDescriptions = parseColumns(element);
      final FixedRowDescription rowDescription = new FixedRowDescription(element, columnDescriptions);

      rows.put(rowDescription.getName(), rowDescription);
    }

    return rows;
  }

  @SuppressWarnings("unchecked")
  private List<ColumnDescription> parseColumns(final Element root) {
    final List<Element> elements = root.elements();
    final List<ColumnDescription> columnDescriptions = new ArrayList<ColumnDescription>();

    for (Element element : elements) {
      columnDescriptions.add(new FixedColumnDescription(element));
    }

    return columnDescriptions;
  }

  /**
   * Retrieves number of rows to initially subtract from footer row count.
   *
   * @return number of rows to initially subtract from footer row count.
   */
  public Integer getMinusRowCount() {
    return minusRowCount;
  }

  /**
   * Retrieves the end of line characters.
   *
   * @return the end of line characters.
   */
  public String getEolCharacter() {
    return EOL_CHARACTER.valueOf(eolCharacter).getValue();
  }

}
