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
  private Boolean clearFirst;

  public DelimitedFeedEngineTest(Boolean clearFirst) {
    this.clearFirst = clearFirst;
  }

  public void runTest() {
    final String feedId = "acme_qagen_testd_request";

    if (clearFirst) {
      setup(feedId);
    }

    final String feedFiles[] =
      {
        "acme_qagen_testd_request_20080420050424.txt",
        "acme_qagen_testd_request_20080420050419.txt",
        "acme_qagen_testd_request_20080420050432.txt",
        "acme_qagen_testd_request_20080420050436.txt",
        "acme_qagen_testd_request_20080420050448.txt",
        "acme_qagen_testd_request_20080420050449.txt",
        "acme_qagen_testd_request_20080420050451.txt",
        "acme_qagen_testd_request_20080420050454.txt",
        "acme_qagen_testd_request_20080420060449.txt",
        "acme_qagen_testd_request_20080617010641.txt"
      };

    for (String feedFile : feedFiles) {
      final Thread thread = new Thread(new FeedEngine(feedId, feedFile));
      thread.start();
    }
  }

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: DelimitedFeedEngineTest clearFirst");
      System.err.println("Example: DelimitedFeedEngineTest true");
    }

    final DelimitedFeedEngineTest test = new DelimitedFeedEngineTest(Boolean.parseBoolean(args[0]));

    test.runTest();
  }

}
