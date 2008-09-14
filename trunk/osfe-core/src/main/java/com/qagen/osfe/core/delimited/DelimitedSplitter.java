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

import com.qagen.osfe.core.EngineService;
import com.qagen.osfe.core.Splitter;
import com.qagen.osfe.core.row.RowDescription;
import com.qagen.osfe.core.utils.CheckNullUtil;

/**
 * The DelimitedSplitter class is the base abstract class for splitters
 * that will parse data from delimited feed files.
 */
public abstract class DelimitedSplitter extends EngineService implements Splitter {
  protected String delimiter;
  protected DelimitedRowParser rowParser;
  protected RowDescription rowDescription;
  protected String rowDescriptionName;

  protected DelimitedRowDescriptionLoader rowDescriptionLoader;

  public void setName(String name) {
    rowDescriptionName = name;
  }

  /**
   * Sets the reference to the delimited row description loader.
   * <p/>
   * <ul><li>Injection - required</li></ul>
   *
   * @param rowDescriptionLoader reference to the delimited row description loader.
   */
  public void setRowDescriptionLoader(DelimitedRowDescriptionLoader rowDescriptionLoader) {
    this.rowDescriptionLoader = rowDescriptionLoader;
  }

  /**
   * Called during second pass of splitter initialization. Should this splitter need
   * access to another splitter, all other splitters will have been instantiated in
   * the first pass.
   */
  public void initialize() {
    delimiter = rowDescriptionLoader.getDelimiter();
    rowDescription = rowDescriptionLoader.getRows().get(rowDescriptionName);
    CheckNullUtil.checkNull(rowDescription, rowDescriptionName);
    rowParser = new DelimitedRowParser(rowDescription, delimiter);
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
   * @return reference to the DelimitedRowDescriptionLoader.
   */
  public DelimitedRowDescriptionLoader getRowLoader() {
    return rowDescriptionLoader;
  }

  /**
   * Retrieves the object that knows how to parser a delimited row.
   *
   * @return reference to the DelimitedRowParser.
   */
  public DelimitedRowParser getRowParser() {
    return rowParser;
  }

  /**
   * Retrieves the object that contains object that describe the row.
   *
   * @return reference to the rowDescription object.
   */
  public RowDescription getRowDescription() {
    return rowDescription;
  }

  /**
   * Retrieves the character(s) being used to delimit the rows.
   *
   * @return the delimiter.
   */
  public String getDelimiter() {
    return delimiter;
  }
}
