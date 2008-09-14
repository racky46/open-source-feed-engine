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
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.FeedFileReader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Author: Hycel Taylor
 * <p/>
 * The FixedFileReader class uses a RandomAccessFile reader to access the data
 * within a given feed file which contains rows of fixed data.
 */
public class FixedFileReader extends FeedFileReader {
  private RandomAccessFile raf;
  private Long size;

  /**
   * Constructor
   *
   * @param context reference to the engine context.
   */
  public FixedFileReader(EngineContext context) {
    super(context);

    try {
      final File file = new File(context.getFullFeedFileName());
      raf = new RandomAccessFile(file, "r");
      size = file.length();
      context.setFeedFileReader(this);
    } catch (IOException e) {
      throw new FeedErrorException(e);
    }
  }

  /**
   * Close any open resoures.
   *
   * @throws IOException
   */
  public void close() throws IOException {
    raf.close();
  }

  /**
   * Retreives the total size of the file.
   *
   * @return the total size of the file.
   */
  public Long size() {
    return size;
  }

  /**
   * Moves the file pointer to the given position within the file.
   *
   * @param position the position the file point should move to.
   */
  public void movePointer(Long position) {
    try {
      raf.seek(0);
      raf.seek(position);
    } catch (IOException e) {
      throw new FeedErrorException(e);
    }
  }

  /**
   * Retrieves a block of data from the current position of the file pointer
   * to the value contained in rowLength and increments the file pointer by the
   * value contained in rowLength.
   *
   * @param rowLength the number of characters to return.
   * @return String of characters of rowLength size.
   */
  public String getRow(int rowLength) {
    final byte[] buffer = new byte[rowLength];
    try {
      raf.read(buffer, 0, rowLength);
    } catch (IOException e) {
      throw new FeedErrorException(e);
    }

    return new String(buffer);
  }

  /**
   * Retrives the current position of the file pointer within the given
   * feed file.
   *
   * @return the current position of the file pointer.
   */
  public long getFilePointer() {
    try {
      return raf.getFilePointer();
    } catch (IOException e) {
      throw new FeedErrorException(e);
    }
  }

}
