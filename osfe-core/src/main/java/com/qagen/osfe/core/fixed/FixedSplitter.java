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

import com.qagen.osfe.core.EngineContext;
import com.qagen.osfe.core.Splitter;

/**
 * Author: Hycel Taylor
 * <p/>
 * The FixedSplitter class is the base abstract class for splitters
 * that will parse data from fixed feed files.
 */
public abstract class FixedSplitter implements Splitter {
  protected final String rowDescriptionName;
  protected final Integer rowCount;
  protected final Integer rowLength;
  protected final EngineContext context;
  protected final FixedRowParser rowParser;
  protected final FixedRowDescription rowDescription;
  protected final FixedRowDescriptionLoader rowLoader;

  /**
   * Constructor
   *
   * @param context reference to the engine context
   * @param rowDescriptionName uniquely identifies the row description in the
   *                configuration file.
   */
  public FixedSplitter(EngineContext context, String rowDescriptionName) {
    this.context = context;
    this.rowDescriptionName = rowDescriptionName;

    rowLoader = (FixedRowDescriptionLoader) context.getRowDescriptionLoader();
    rowDescription = (FixedRowDescription) rowLoader.getRows().get(rowDescriptionName);
    rowParser = new FixedRowParser(rowDescription);
    rowCount = rowDescription.getRowCount();
    rowLength = rowDescription.getRowLength() + rowLoader.getEolCharacter().length();
  }

  /**
   * Retrieves the refence to the engine context.
   *
   * @return reference to the engine context.
   */
  public EngineContext getContext() {
    return context;
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
  public FixedRowDescriptionLoader getRowsLoader() {
    return rowLoader;
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
    return rowDescription;
  }

  /**
   * Retrieves the total number of rows the splitter has access to.
   *
   * @return the total number of rows the splitter has access to.
   */
  public Integer getRowCount() {
    return rowCount;
  }

  /**
   * Retrieves the length of the fixed row.
   *
   * @return the length of the fixed row.
   */
  public Integer getRowLength() {
    return rowLength;
  }
}
