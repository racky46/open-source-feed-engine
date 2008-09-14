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

import com.qagen.osfe.core.FooterSplitter;
import com.qagen.osfe.core.row.RowValue;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * This class operates on the footer rows of a delimited feed file.
 */
public class DelimitedFooterSplitter extends DelimitedSplitter implements FooterSplitter {
  private DelimitedFooterParser delimitedFooterParser;
  private Integer rowIndex;
  private Integer totalRowCount;

  /**
   * Called during second pass of splitter initialization. Should this splitter need
   * access to another splitter, all other splitters will have been instantiated in
   * the first pass.
   */
  public void initialize() {
    super.initialize();

    final Integer numberOfFooterRows = rowDescription.getRowCount();
    delimitedFooterParser = new DelimitedFooterParser(context, numberOfFooterRows);
    totalRowCount = delimitedFooterParser.getRowCount();
    rowIndex = 0;
  }

  /**
   * Retrieves a list or RowValue objects for the given feed file.
   *
   * @return reference to a list of RowValue objects.
   */
  public List<RowValue> getNextRow() {
    if (hasNextRow()) {
      final String row = delimitedFooterParser.getFooterRows().get(rowIndex++);
      return rowParser.parseRow(row);
    }
    return null;
  }

  /**
   * Determines if there is another row to retrieve from the feed file.
   *
   * @return true if more rows. false if end of file has been reached.
   */
  public Boolean hasNextRow() {
    return (rowIndex < delimitedFooterParser.getRowCount());
  }

  /**
   * Many feed files have footer rows that specify the total number of rows
   * in a given feed file. This methood should perform the actions that
   * determine that calculation.
   *
   * @return the count of the number of rows in a feed file.
   */
  public Integer getTotalRowCount() {
    return totalRowCount;
  }

  /**
   * The total number of rows in a feed file is calculated as the
   * total number rows in the detail, minus the total number of rows
   * in the header and the footer. This method should return the total
   * number of rows that should be subtracted the calculation of the
   * total row count.
   *
   * @return the total numbers of rows that should be subtracted from
   *         the calculation of the total row count.
   */
  public Integer getMinusRowCount() {
    return rowDescriptionLoader.getMinusRowCount();
  }

  /**
   * Sometimes certain operations need to be executed prior to the first row
   * being retrieved but after initialization.
   */
  public void prePhaseExecute() {
    // Do noting.
  }

  /**
   * Stores the name of the given service as it is defined in the feed
   * configuration document.
   *
   * @return the name of the service as it is defined in the feed configuration
   *         document.
   */
  public String name() {
    return this.getClass().getSimpleName();
  }

  /**
   * Depending on the behavior of the service, it's shutdown method may be
   * called in order to perform house keeping tasks such as closing files
   * and other depended services.
   */
  public void shutdown() {
  }
}
