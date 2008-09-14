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

import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.FooterSplitter;
import com.qagen.osfe.core.Splitter;
import com.qagen.osfe.core.row.RowValue;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * This class operates on the footer rows of a fixed feed file.
 */
public class FixedFooterSplitter extends FixedSplitter implements FooterSplitter {
  private FixedFileReader fileReader;
  private Integer rowIndex;
  private long fileSize;

  private FixedHeaderSplitter headerSplitter;
  private FixedDetailSplitter detailSplitter;


  public void setHeaderSplitter(Splitter headerSplitter) {
    this.headerSplitter = (FixedHeaderSplitter) headerSplitter;
  }

  public void setDetailSplitter(Splitter detailSplitter) {
    this.detailSplitter = (FixedDetailSplitter) detailSplitter;
  }

  /**
   * Called during second pass of splitter initialization. Should this splitter need
   * access to another splitter, all other splitters will have been instantiated in
   * the first pass.
   */
  public void initialize() {
    super.initialize();

    fileReader = (FixedFileReader) context.getFeedFileReader();
    fileSize = fileReader.size();

    checkFileSize();

    final Long filePosition = fileSize - (rowLength * rowCount);

    fileReader.movePointer(filePosition);
    rowIndex = 0;
  }

  public List<RowValue> getNextRow() {
    if (hasNextRow()) {
      rowIndex++;
      final String row = fileReader.getRow(rowLength);
      return rowParser.parseRow(row);
    }
    return null;
  }

  /**
   * Does a validity check on the file size.
   *
   * @throws FeedErrorException if the headerSize + footerSize + detailSize do
   *                            not match the file size.
   */
  public void checkFileSize() {
    final long headerSize = headerSplitter.getRowCount() * headerSplitter.getRowLength();
    final long detailSize = detailSplitter.getRowCount() * detailSplitter.getRowLength();
    final long footerSize = getRowCount() * getRowLength();

    final long totalSize = headerSize + detailSize + footerSize;

    if (totalSize != fileSize) {
      final String feedFileName = context.getFeedFileName();
      final String message =
        "The file size for feed file " + feedFileName +
          ", does not match the file size calculated against the header, detail & footer row lengths.";
      throw new FeedErrorException(message);
    }
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
   * The footer splitter is the only splitter the calculates the total number
   * of rows in a the given feed file.  The number of footer rows are not part
   * of the calculation. Thus, the total row count is equal to the total number
   * of header rows + the total number of detail rows.
   *
   * @return total header rows + total detail rows.
   */
  public Integer getTotalRowCount() {
    return headerSplitter.getRowCount() + detailSplitter.getRowCount() + getRowCount();
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
