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
 * <p>
 * 
 */
public class FeedEngineTest {


  public static void main(String[] args) {

    final String feedId = "acme_qagen_test_request";

   final String feedFile = "acme_qagen_test_request_20080420050424.txt";
//   final String feedFile = "acme_qagen_test_request_20080420050419.txt";
//   final String feedFile = "acme_qagen_test_request_20080421060401.txt";

    try {
      final FeedEngine engine = new FeedEngine(feedId, feedFile);
      engine.execute();
    } catch (Exception e) {
      // do nothing.
    }
  }

}
