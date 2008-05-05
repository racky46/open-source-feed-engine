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

public abstract class DelimitedSplitter implements Splitter {
  protected final String rowName;
  protected final String delimiter;
  protected final EngineContext context;
  protected final DelimitedRowParser rowParser;
  protected final RowDescription rowDescription;
  protected final DelimitedRowDescriptionLoader rowLoader;

  public DelimitedSplitter(EngineContext context, String rowName) {
    this.context = context;
    this.rowName = rowName;

    rowLoader = (DelimitedRowDescriptionLoader) context.getRowDescriptionLoader();
    delimiter = rowLoader.getDelimiter();
    rowDescription = rowLoader.getRows().get(rowName);
    rowParser = new DelimitedRowParser(rowDescription, delimiter);

    initialize();
  }

  public EngineContext getContext() {
    return context;
  }

  public String getRowName() {
    return rowName;
  }

  public DelimitedRowDescriptionLoader getRowsLoader() {
    return rowLoader;
  }

  public DelimitedRowParser getRowParser() {
    return rowParser;
  }

  public RowDescription getRowDescription() {
    return rowDescription;
  }

  public String getDelimiter() {
    return delimiter;
  }
}
