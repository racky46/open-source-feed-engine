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

import com.qagen.osfe.core.EngineService;
import com.qagen.osfe.core.Splitter;
import com.qagen.osfe.core.utils.CheckNullUtil;

/**
 * Author: Hycel Taylor
 * <p/>
 * The FixedSplitter class is the base abstract class for splitters
 * that will parse data from fixed feed files.
 */
public abstract class FixedSplitter extends EngineService implements Splitter {
  protected Integer rowCount;
  protected Integer rowLength;
  protected FixedRowParser rowParser;
  protected FixedRowDescription rowDescription;
  protected String rowDescriptionName;

  protected FixedRowDescriptionLoader rowDescriptionLoader;

  public void setRowDescriptionLoader(FixedRowDescriptionLoader rowDescriptionLoader) {
    this.rowDescriptionLoader = rowDescriptionLoader;
  }

  public void setName(String name) {
    this.rowDescriptionName = name;
  }

  public void initialize() {
    rowDescription = (FixedRowDescription) rowDescriptionLoader.getRows().get(rowDescriptionName);
    CheckNullUtil.checkNull(rowDescription, rowDescriptionName);
    rowParser = new FixedRowParser(rowDescription);
    rowCount = rowDescription.getRowCount();
    rowLength = rowDescription.getRowLength() + rowDescriptionLoader.getEolCharacter().length();
  }

  /**
   * Retrieves the name that uniquely identifies the row description.
   *
   * @return the name of the row description.
   */
  public String getRowDescriptionName() {
    return rowDescriptionName;
  }

  /**
   * Retrieves the object that knows how to load the row description.
   *
   * @return reference to the FixedRowDescriptionLoader.
   */
  public FixedRowDescriptionLoader getRowLoader() {
    return rowDescriptionLoader;
  }

  /**
   * Retrieves the object that knows how to parser a fixed row.
   *
   * @return reference to the FixedRowParser.
   */
  public FixedRowParser getRowParser() {
    return rowParser;
  }

  /**
   * Retrieves the object that contains object that describe the row.
   *
   * @return reference to the rowDescription object.
   */
  public FixedRowDescription getRowDescription() {
    // It is necessary to check this because fixed splitters reference each during initialization.
    if (rowDescription == null) {
      rowDescription = (FixedRowDescription) rowDescriptionLoader.getRows().get(rowDescriptionName);
      CheckNullUtil.checkNull(rowDescription, rowDescriptionName);
    }

    return rowDescription;
  }

  /**
   * Retrieves the total number of rows the splitter has access to.
   *
   * @return the total number of rows the splitter has access to.
   */
  public Integer getRowCount() {
    // It is necessary to check this because fixed splitters reference each during initialization.
    if (rowCount == null) {
      rowCount = getRowDescription().getRowCount();
    }

    return rowCount;
  }

  /**
   * Retrieves the length of the fixed row.
   *
   * @return the length of the fixed row.
   */
  public Integer getRowLength() {
    // It is necessary to check this because fixed splitters reference each during initialization.
    if (rowLength == null) {
      rowLength = getRowDescription().getRowLength() + rowDescriptionLoader.getEolCharacter().length();
    }

    return rowLength;
  }
}
