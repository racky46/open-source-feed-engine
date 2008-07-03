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
package com.qagen.osfe.engine;

import org.testng.annotations.Test;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class DelimitedFeedEngineTest extends FeedTestBase {

  public void runTest() {
    final String feedId = "acme_qagen_test_request";
    final String feedFile = "acme_qagen_test_request_20080420050424.txt";

    setup(feedId);
    final FeedEngine engine = new FeedEngine(feedId, feedFile);
    engine.execute();
  }

  public static void main(String[] args) {
    final DelimitedFeedEngineTest test = new DelimitedFeedEngineTest();

    test.runTest();
  }

}
