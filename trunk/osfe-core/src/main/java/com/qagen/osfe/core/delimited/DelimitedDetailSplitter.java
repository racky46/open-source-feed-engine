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

import com.qagen.osfe.common.utils.ElapsedTime;
import com.qagen.osfe.common.utils.HeapStats;
import com.qagen.osfe.common.utils.Log;
import com.qagen.osfe.core.CheckpointHandler;
import com.qagen.osfe.core.EngineContext;
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.row.RowValue;
import com.qagen.osfe.dataAccess.vo.FeedCheckpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DelimitedDetailSplitter extends DelimitedSplitter implements CheckpointHandler {
  private List<List<RowValue>> rows;
  private Integer batchSize;
  private Integer linesToSkip;
  private Integer feedRowCount;
  private Integer currentRowIndex;
  private BufferedReader bufferedReader;

  private Integer localRowIndex;
  private Integer localRowSize;
  private Long startTime = System.currentTimeMillis();

  private static Log logger = Log.getInstance(DelimitedDetailSplitter.class);

  public DelimitedDetailSplitter(EngineContext context, String rowName) {
    super(context, rowName);
    context.setDetailSplitter(this);
  }

  public void initialize() {
    batchSize = context.getBatchSize();
    linesToSkip = getRowDescription().getLinesToSkip();
    currentRowIndex = context.getCurrentSplitterIndex();
    bufferedReader = (BufferedReader) context.getFeedFileReader();

    localRowIndex = 0;
    localRowSize = 0;
  }

  public void prePhaseExecute() {
    feedRowCount = context.getFeedRowCount();
    skipLines();
  }

  private void skipLines() {
    if (linesToSkip == null) {
      return;
    }

    for (int index = 0; index < linesToSkip; index++) {
      if (currentRowIndex > feedRowCount) {
        final String message = "Lines to skip, " + linesToSkip +
          ", has exceed the total number of lines in the file, " + feedRowCount + ".";
        throw new FeedErrorException(message);
      }

      try {
        bufferedReader.readLine();
      } catch (IOException e) {
        throw new FeedErrorException(e);
      }
    }
  }

  private void getNextBlockOfRows() {
    try {
      context.setPreviousSplitterIndex(context.getCurrentSplitterIndex());
      rows = new ArrayList<List<RowValue>>();

      for (int index = 0; (index < batchSize) && (currentRowIndex < feedRowCount); index++) {
        final String row = bufferedReader.readLine();
        rows.add(rowParser.parseRow(row));
        ++currentRowIndex;
      }

      context.setCurrentSplitterIndex(currentRowIndex);

      localRowIndex = 0;
      localRowSize = rows.size();

      final Long heapSize = (HeapStats.getHeapSize() / 1024) / 1024;
      final Long heapFree = (HeapStats.getFreeHeapSize() / 1024) / 1024;
      final String status = "***** " + localRowSize + " elapased time: " + ElapsedTime.getElapsedTime(startTime) +
        " Heap Size = " + heapSize + "M Heap Free = " + heapFree + "M";

      logger.info(status);
      startTime = System.currentTimeMillis();

    } catch (IOException e) {
      throw new FeedErrorException(e);
    }
  }

  public List<RowValue> getNextRow() {
    if (hasNextRow()) {
      return rows.get(localRowIndex++);
    }

    return null;
  }

  public Boolean hasNextRow() {
    if (localRowIndex > localRowSize - 1) {
      getNextBlockOfRows();
    }

    return (localRowSize != 0);
  }

  public void moveToCheckPoint(FeedCheckpoint checkpoint) {
    final int fileIndex = checkpoint.getCurrentFileIndex();
    int currentIndex = context.getCurrentSplitterIndex();
    
    while (fileIndex != currentIndex) {
      getNextBlockOfRows();
      currentIndex = context.getCurrentSplitterIndex();
    }
  }
}
