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
import com.qagen.osfe.core.FooterSplitter;
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.SplitterFileOpener;
import com.qagen.osfe.core.row.RowValue;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * This class operates on the footer rows of a fixed feed file.
 */
public class FixedFooterSplitter extends FixedSplitter implements FooterSplitter, SplitterFileOpener {
  private FixedFileReader fileReader;
  private Integer rowIndex;
  private long fileSize;

  /**
   * Constructor
   *
   * @param context            reference to the engine context
   * @param rowDescriptionName uniquely identifies the row description in the
   *                           configuration file.
   */
  public FixedFooterSplitter(EngineContext context, String rowDescriptionName) {
    super(context, rowDescriptionName);

    openFeedFileReader();
  }

  /**
   * Instantiates a FeedFileReader object and call any method on that object
   * to open its file handler if the file handler.
   * <p/>
   * Once the FeedFileReader object has been successfully opened, it should be
   * placed in the engine context using setFeedFeedFileReader().
   */
  public void openFeedFileReader() {
    fileReader = new FixedFileReader(context);
    context.setFeedFileReader(fileReader);
  }

  /**
   * Called during second pass of splitter initialization. Should this splitter need
   * access to another splitter, all other splitters will have been instantiated in
   * the first pass.
   */
  public void initialize() {
    fileSize = fileReader.size();

    checkFileSize();

    final Long filePosition = fileSize - (rowLength * rowCount);

    fileReader.movePointer(filePosition);
    rowIndex = 0;
  }

  /**
   * This method is used to calculate the total length of the given row
   * determined by the length and eolCharacter defined in the row description.
   *
   * @return length + length of eolCharacter.
   */
  public Integer getRowLength() {
    return rowLength;
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
    final FixedHeaderSplitter headerSplitter = (FixedHeaderSplitter) context.getHeaderSplitter();
    final FixedDetailSplitter detailSplitter = (FixedDetailSplitter) context.getDetailSplitter();

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
    final FixedHeaderSplitter headerSplitter = (FixedHeaderSplitter) context.getHeaderSplitter();
    final FixedDetailSplitter detailSplitter = (FixedDetailSplitter) context.getDetailSplitter();
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
    return rowLoader.getMinusRowCount();
  }

  /**
   * Sometimes certain operations need to be executed prior to the first row
   * being retrieved but after initialization.
   */
  public void prePhaseExecute() {
    // Do noting.
  }
}
