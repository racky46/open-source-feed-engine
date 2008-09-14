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

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class FixedFeedEngineTest extends FeedTestBase {
  private Boolean clearFirst;

  public FixedFeedEngineTest(Boolean clearFirst) {
    this.clearFirst = clearFirst;
  }

  public void runTest() {
    final String feedId = "acme_qagen_testf_request";

    if (clearFirst) {
      setup(feedId);
    }

    final String feedFiles[] =
      {
        "acme_qagen_testf_request_20080617010641.txt",
        "acme_qagen_testf_request_20080727080747.txt",
        "acme_qagen_testf_request_20080727080731.txt",
        "acme_qagen_testf_request_20080727080719.txt",
        "acme_qagen_testf_request_20080727080759.txt",
        "acme_qagen_testf_request_20080727080717.txt",
        "acme_qagen_testf_request_20080727080753.txt",
        "acme_qagen_testf_request_20080727080729.txt",
        "acme_qagen_testf_request_20080727080745.txt",
        "acme_qagen_testf_request_20080727080705.txt",
        "acme_qagen_testf_request_20080730070721.txt"
      };

    for (String feedFile : feedFiles) {
      final Thread thread = new Thread(new InboundFeedEngine(feedId, feedFile));
      thread.start();
    }
  }

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: FixedFeedEngineTest clearFirst");
      System.err.println("Example: FixedFeedEngineTest true");
    }

    final FixedFeedEngineTest test = new FixedFeedEngineTest(Boolean.parseBoolean(args[0]));

    test.runTest();
  }

}