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

import com.qagen.osfe.core.EngineContext;
import com.qagen.osfe.core.Splitter;
import com.qagen.osfe.core.row.RowDescription;

/**
 * The DelimitedSplitter class is the base abstract class for splitters
 * that will parse data from delimited feed files.
 */
public abstract class DelimitedSplitter implements Splitter {
  protected final String rowDescriptionName;
  protected final String delimiter;
  protected final EngineContext context;
  protected final DelimitedRowParser rowParser;
  protected final RowDescription rowDescription;
  protected final DelimitedRowDescriptionLoader rowLoader;

  /**
   * Constructor
   *
   * @param context            reference to the engine context
   * @param rowDescriptionName uniquely identifies the row description in the
   *                           configuration file.
   */
  public DelimitedSplitter(EngineContext context, String rowDescriptionName) {
    this.context = context;
    this.rowDescriptionName = rowDescriptionName;

    rowLoader = (DelimitedRowDescriptionLoader) context.getRowDescriptionLoader();
    delimiter = rowLoader.getDelimiter();
    rowDescription = rowLoader.getRows().get(rowDescriptionName);
    rowParser = new DelimitedRowParser(rowDescription, delimiter);
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
   * @return reference to the DelimitedRowDescriptionLoader.
   */
  public DelimitedRowDescriptionLoader getRowsLoader() {
    return rowLoader;
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