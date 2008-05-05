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
package com.qagen.osfe.core.parsers;

import com.qagen.osfe.core.EngineContext;
import com.qagen.osfe.core.FeedErrorException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFooterParser {
  private BufferedReader bufferedReader;
  private List<String> footerRows;
  private Integer footerRowCount;
  private Integer rowCount;

  public TextFooterParser(EngineContext context, Integer footerRowCount) {
    this.footerRowCount = footerRowCount;

    final FileReader fileReader = openFile(context.getFullFeedFileName());
    bufferedReader = new BufferedReader(fileReader);
    footerRows = new ArrayList<String>();
    rowCount = 0;
    parseToEofFile();
  }

  private FileReader openFile(String fileName) {
    try {
      return new FileReader(fileName);
    } catch (FileNotFoundException e) {
      throw new FeedErrorException(e);
    }
  }

  private void parseToEofFile() {
    try {
      Integer currentFooterCount = 0;
      String currentLine;
      while ((currentLine = bufferedReader.readLine()) != null) {
        rowCount++;
        if (currentFooterCount < footerRowCount) {
          footerRows.add(currentLine);
          currentFooterCount++;
        } else {
          footerRows.add(currentLine);
          footerRows.remove(0);
        }
      }
    } catch (IOException e) {
      throw new FeedErrorException(e);
    }
  }

  public List<String> getFooterRows() {
    return footerRows;
  }

  public Integer getRowCount() {
    return rowCount;
  }
}
