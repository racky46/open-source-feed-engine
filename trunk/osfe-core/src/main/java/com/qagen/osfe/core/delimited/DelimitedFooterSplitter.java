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
import com.qagen.osfe.core.parsers.TextFooterParser;
import com.qagen.osfe.core.row.RowValue;

import java.util.List;

public class DelimitedFooterSplitter extends DelimitedSplitter {
  private TextFooterParser textFooterParser;
  private Integer rowIndex;
  private Integer totalRowCount;

  public DelimitedFooterSplitter(EngineContext context, String rowName) {
    super(context, rowName);
    context.setFooterSplitter(this);
  }

  public void initialize() {
    final Integer numberOfFooterRows = rowDescription.getRowCount();
    textFooterParser = new TextFooterParser(context, numberOfFooterRows);
    totalRowCount = textFooterParser.getRowCount();
    rowIndex = 0;
  }

  public List<RowValue> getNextRow() {
    if (hasNextRow()) {
      final String row = textFooterParser.getFooterRows().get(rowIndex++);
      return rowParser.parseRow(row);
    }
    return null;
  }

  public Boolean hasNextRow() {
    return (rowIndex < textFooterParser.getRowCount());
  }

  public Integer getTotalRowCount() {
    return totalRowCount;
  }

  public void prePhaseExecute() {
    // Do noting.
  }
}
