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
import com.qagen.osfe.core.FeedFileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Author: Hycel Taylor
 * <p/>
 * The DelimitedFileReader class uses a BufferedReader to access the data
 * within a given feed file which contains rows of delimited data.
 */
public class DelimitedFileReader extends FeedFileReader {
  final BufferedReader bufferedReader;

  /**
   * Contructor
   *
   * @param context reference to the engine context.
   */
  public DelimitedFileReader(EngineContext context) {
    super(context);

    try {
      final String fullFeedFileName = context.getFullFeedFileName();
      final FileReader fileReader = new FileReader(fullFeedFileName);
      bufferedReader = new BufferedReader(fileReader);
      context.setFeedFileReader(this);
    } catch (FileNotFoundException e) {
      throw new FeedErrorException(e);
    }
  }

  /**
   * Close any open resoures.
   *
   * @throws IOException
   */
  public void close() throws IOException {
    bufferedReader.close();
  }

  /**
   * Retrieves the bufferedReader.
   *
   * @return bufferedReader.
   */
  public BufferedReader getBufferedReader() {
    return bufferedReader;
  }
}
