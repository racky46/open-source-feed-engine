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
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.SplitterFileOpener;
import com.qagen.osfe.core.row.RowValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * This class operates on the header rows of a delimited feed file.
 */
public class DelimitedHeaderSplitter extends DelimitedSplitter implements SplitterFileOpener {
  private List<List<RowValue>> rows;
  private Integer rowIndex;
  private Integer rowCount;

  private DelimitedFileReader fileReader;

  /**
   * Constructor
   *
   * @param context            reference to the engine context
   * @param rowDescriptionName uniquely identifies the row description in the
   *                           configuration file.
   */
  public DelimitedHeaderSplitter(EngineContext context, String rowDescriptionName) {
    super(context, rowDescriptionName);
  }

  /**
   * Instantiates a FeedFileReader object and call any method on that object
   * to open its file handler if the file handler.
   * <p/>
   * Once the FeedFileReader object has been successfully opened, it should be
   * placed in the engine context using setFeedFeedFileReader().
   */
  public void openFeedFileReader() {
    fileReader = new DelimitedFileReader(context);
    context.setFeedFileReader(fileReader);
  }

  /**
   * Parse and load all of the header rows into a list of RowValue objects.
   */
  public void initialize() {
    final BufferedReader bufferedReader = fileReader.getBufferedReader();
    rowCount = rowDescription.getRowCount();
    rows = new ArrayList<List<RowValue>>();
    rowIndex = 0;

    try {
      String row;
      for (int index = 0; index < rowCount; index++) {
        row = bufferedReader.readLine();
        if (row == null) {
          throw new FeedErrorException("Feed header count of " + rowCount + " exceeded number of rows in file.");
        }

        rows.add(rowParser.parseRow(row));
      }
    } catch (IOException e) {
      throw new FeedErrorException(e);
    }
  }

  /**
   * Retrieves a list or RowValue objects for the given feed file.
   *
   * @return reference to a list of RowValue objects.
   */
  public List<RowValue> getNextRow() {
    if (hasNextRow()) {
      return rows.get(rowIndex++);
    }
    return null;
  }

  /**
   * Determines if there is another row to retrieve from the feed file.
   *
   * @return true if more rows. false if end of file has been reached.
   */
  public Boolean hasNextRow() {
    return rowIndex < rowCount;
  }

  /**
   * Sometimes certain operations need to be executed prior to the first row
   * being retrieved but after initialization.
   */
  public void prePhaseExecute() {
    // Do nothing.
  }
}
