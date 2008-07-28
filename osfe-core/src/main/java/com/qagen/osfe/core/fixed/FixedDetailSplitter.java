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

import com.qagen.osfe.core.row.RowValue;
import com.qagen.osfe.core.EngineContext;
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.CheckpointHandler;
import com.qagen.osfe.common.utils.Log;
import com.qagen.osfe.common.utils.HeapStats;
import com.qagen.osfe.common.utils.ElapsedTime;
import com.qagen.osfe.dataAccess.vo.FeedCheckpoint;

import java.util.List;
import java.util.ArrayList;

/**
 * Author: Hycel Taylor
 * <p/>
 * The FixedDetailSplitter parses the detail data in Fixed feed files.
 * This splitter will read and parse N number of rows where N is the batch size
 * defined in the given feed file document and deliver the parsed data in a list of
 * RowValue objects. This splitter will continue to parse N rows of data until all
 * rows in the given feed file have been parsed.
 */
public class FixedDetailSplitter extends FixedSplitter implements CheckpointHandler {
  private FixedFileReader fileReader;
  private List<List<RowValue>> rows;
  private Integer batchSize;
  private Integer linesToSkip;
  private Long currentRowIndex;

  private Integer localRowIndex;
  private Integer localRowSize;
  private long totalFileSize;
  private Long startTime = System.currentTimeMillis();

  private Integer headerBlockSize;
  private Integer footerBlockSize;

  private static Log logger = Log.getInstance(FixedDetailSplitter.class);

  /**
   * Constructor
   *
   * @param context            reference to the engine context
   * @param rowDescriptionName uniquely identifies the row description in the
   *                           configuration file.
   */
  public FixedDetailSplitter(EngineContext context, String rowDescriptionName) {
    super(context, rowDescriptionName);
  }

  /**
   * Called during second pass of splitter initialization. Should this splitter need
   * access to another splitter, all other splitters will have been instantiated in
   * the first pass.
   */
  public void initialize() {
    fileReader = (FixedFileReader) context.getFeedFileReader();
    fileReader.movePointer(0L);

    final FixedHeaderSplitter headerSplitter = (FixedHeaderSplitter) context.getHeaderSplitter();
    headerBlockSize = headerSplitter.getRowCount() * headerSplitter.getRowLength();

    final FixedFooterSplitter footerSplitter = (FixedFooterSplitter) context.getFooterSplitter();
    footerBlockSize = footerSplitter.getRowCount() * footerSplitter.getRowLength();

    totalFileSize = fileReader.size();

    batchSize = context.getBatchSize();
    linesToSkip = getRowDescription().getLinesToSkip();
    currentRowIndex = context.getCurrentSplitterIndex();

    localRowIndex = 0;
    localRowSize = 0;
  }

  /**
   * Skip over any lines that need to be skipped.
   */
  public void prePhaseExecute() {
    skipLines();
  }

  private void skipLines() {
    // First skip any header rows.
    fileReader.movePointer((long) headerBlockSize);
    currentRowIndex += headerBlockSize;

    // Then skip number of lines specfied for attribute linesToSkip.
    if ((linesToSkip != null) && (linesToSkip > 0)) {
      final Integer blockSize = linesToSkip * getRowLength();
      fileReader.movePointer((long) blockSize);
      currentRowIndex += blockSize;
    }
  }

  /**
   * Calculates the next block of data and converts to a list of RowValue objects.
   */
  private void getNextBlockOfRows() {
    context.setPreviousSplitterIndex(context.getCurrentSplitterIndex());
    rows = new ArrayList<List<RowValue>>();
    int jumpSize = batchSize * rowLength;

    final long newBlockSize = fileReader.getFilePointer() + (jumpSize);
    final long fileSize = totalFileSize - footerBlockSize;

    if (currentRowIndex != fileSize) {

      if (newBlockSize > fileSize) {
        jumpSize = jumpSize - (int) (newBlockSize - fileSize);
      }

      currentRowIndex += jumpSize;
      context.setCurrentSplitterIndex(currentRowIndex);

      localRowSize = jumpSize / rowLength;

      for (int count = 0; count < localRowSize; count++) {
        final String row = fileReader.getRow(rowLength);
        rows.add(rowParser.parseRow(row));
      }
    }

    localRowIndex = 0;
    localRowSize = rows.size();

    final Integer feedFileId = context.getFeedJob().getFeedFile().getFeedFileId();
    final Long heapSize = (HeapStats.getHeapSize() / 1024) / 1024;
    final Long heapFree = (HeapStats.getFreeHeapSize() / 1024) / 1024;
    final String status = "***** FeedFileId: " + feedFileId + "  batch size " + localRowSize + " elapased time: " + ElapsedTime.getElapsedTime(startTime) +
      " Heap Size = " + heapSize + "M Heap Free = " + heapFree + "M";

    logger.info(status);
    startTime = System.currentTimeMillis();
  }

  /**
   * Retrieves a list or RowValue objects for the given feed file.
   *
   * @return reference to a list of RowValue objects.
   */
  public List<RowValue> getNextRow() {
    if (hasNextRow()) {
      return rows.get(localRowIndex++);
    }
    return null;
  }

  /**
   * Determines if there is another row to retrieve from the feed file.
   *
   * @return true if more rows. false if end of file has been reached.
   */
  public Boolean hasNextRow() {
    if (localRowIndex > localRowSize - 1) {
      getNextBlockOfRows();
    }

    return (localRowSize != 0);
  }

  /**
   * Performs the task of moving the file pointer to the last checkpoint
   * position in the file.
   *
   * @param checkpoint contains the information about the position in
   *                   the file to move to.
   */
  public void moveToCheckPoint(FeedCheckpoint checkpoint) {
    final long fileIndex = checkpoint.getCurrentFileIndex();

    currentRowIndex = fileIndex;
    fileReader.movePointer(fileIndex);
    getNextBlockOfRows();
  }

  /**
   * Retrieves the total number of rows the splitter has access to.
   *
   * @return the total number of rows the splitter has access to.
   */
  public Integer getRowCount() {
    final FixedHeaderSplitter headerSplitter = (FixedHeaderSplitter) context.getHeaderSplitter();
    final FixedFooterSplitter footerSplitter = (FixedFooterSplitter) context.getFooterSplitter();

    long headerSize = headerSplitter.getRowCount() * headerSplitter.getRowLength();
    long footerSize = footerSplitter.getRowCount() * footerSplitter.getRowLength();
    long detailSize = totalFileSize - headerSize - footerSize;
    long remainder = detailSize % getRowLength();

    if (remainder < 0) {
      final String message = "Unable to calculate detail row count without remainder for file, " + context.getFeedFileName() + ".";
      throw new FeedErrorException(message);
    }

    final Long result = detailSize / getRowLength();
    return result.intValue();
  }
}