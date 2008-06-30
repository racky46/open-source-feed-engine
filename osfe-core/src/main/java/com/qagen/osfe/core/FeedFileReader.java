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

import java.io.IOException;

/**
 * Author: Hycel Taylor
 * <p>
 * The FeedFileReader class is abstract and should be extended by classes
 * that are responsible for reading a feed file.  FeedFileReader classes
 * are most often used by splitters which are responsible for breaking a
 * feed file up into parts and breaking down the data in those parts in to
 * rows.
 * <p>
 * For example, a DelimitedFeedFileReader class may use a BufferedReader in order
 * to read data from a feed file while a FixedFeedFileReader class may use a
 * RandomAccessFile in order to read data in blocks and then break the blocks
 * of data into individual rows.
 */
public abstract class FeedFileReader {
  protected EngineContext context;

  /**
   * Constructor
   *
   * @param context reference to the engine context.
   */
  protected FeedFileReader(EngineContext context) {
    this.context = context;
  }

  /**
   * Close any open resoures.
   *
   * @throws IOException
   */
  public abstract void close() throws IOException;
  
}
