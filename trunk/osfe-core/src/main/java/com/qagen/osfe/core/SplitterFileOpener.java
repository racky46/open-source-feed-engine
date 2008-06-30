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

/**
 * Author: Hycel Taylor
 * <p/>
 * Only one Splitter for a given set of splitters needs to be responseible for
 * instantiating the FeedFileReader class the set of splitters will use. That
 * splitter should implement this interface.  A class which instantiates a
 * given set of splitters perform the following sequence:
 * <ul>
 * <li> instantiate each splitter
 * <li> find the spliter that is an instanceof SplltterFileOpener and call openFeedFileReader()
 * <li> call the initialize() method on each splitter
 * </ul>
 */
public interface SplitterFileOpener {

  /**
   * Instantiates a FeedFileReader object and call any method on that object
   * to open its file handler if the file handler.
   * <p>
   * Once the FeedFileReader object has been successfully opened, it should be
   * placed in the engine context using setFeedFeedFileReader().
   */
  public void openFeedFileReader();
}
