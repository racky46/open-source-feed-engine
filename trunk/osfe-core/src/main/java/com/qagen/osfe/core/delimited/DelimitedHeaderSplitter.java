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
import com.qagen.osfe.core.row.RowValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DelimitedHeaderSplitter extends DelimitedSplitter {
  private List<List<RowValue>> rows;
  private Integer rowIndex;
  private Integer rowCount;

  public DelimitedHeaderSplitter(EngineContext context, String rowName) {
    super(context, rowName);
    context.setHeaderSplitter(this);
  }

  public void initialize() {
    final BufferedReader bufferedReader = (BufferedReader) context.getFeedFileReader();
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

  public List<RowValue> getNextRow() {
    if (hasNextRow()) {
      return rows.get(rowIndex++);
    }
    return null;
  }

  public Boolean hasNextRow() {
    return rowIndex < rowCount;
  }

  public void prePhaseExecute() {
    // Do nothing.
  }
}
