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
package com.qagen.osfe.core;

import com.qagen.osfe.common.FeedConstants;
import com.qagen.osfe.common.utils.DirectoryHelper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Author: Hycel Taylor
 * <p/>
 * The DelimitedFileWriter class uses a BufferedWriter to access the data
 * within a given feed file which contains rows of delimited data.
 */
public class BufferedFeedFileWriter extends FeedFileWriter implements FeedConstants {
  final BufferedWriter bufferedWriter;

  /**
   * Contructor
   *
   * @param context  reference to the engine context.
   * @param fileName the name assigned to the output file.
   */
  public BufferedFeedFileWriter(EngineContext context, String fileName) {
    super(context);

    try {
      final String homeDirectory = DirectoryHelper.getHomeDirectory();
      final String feedDirectory = context.getFeed().getFeedDirectory();
      final String workDirectory = FEED_DIR.workarea.getValue();

      final String fullFileName = homeDirectory + SLASH + feedDirectory + SLASH + workDirectory + SLASH + fileName;
      final FileWriter fileWriter = new FileWriter(fullFileName);

      bufferedWriter = new BufferedWriter(fileWriter);

      context.setFeedFileName(fileName);
      context.setFullFeedFileName(fullFileName);
      context.setFeedFileWriter(this);

    } catch (IOException e) {
      throw new FeedErrorException(e);
    }
  }

  /**
   * Close any open resoures.
   *
   * @throws java.io.IOException
   */
  public void close() throws IOException {
    bufferedWriter.close();
  }

  /**
   * Retrieves the bufferedWriter.
   *
   * @return bufferedWriter.
   */
  public BufferedWriter getBufferedWriter() {
    return bufferedWriter;
  }
}