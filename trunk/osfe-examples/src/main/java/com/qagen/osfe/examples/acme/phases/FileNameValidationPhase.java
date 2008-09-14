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
package com.qagen.osfe.examples.acme.phases;

import static com.qagen.osfe.common.FeedConstants.UNDER_SCORE;
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.Phase;
import com.qagen.osfe.dataAccess.vo.Feed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileNameValidationPhase extends Phase {
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

  public void initialize() {
  }

  public void execute() {
    final String feedFileName = context.getFeedFileName();
    final Feed feed = context.getFeed();
    final StringTokenizer tokenizer = new StringTokenizer(feedFileName, UNDER_SCORE);

    List<String> tokens = new ArrayList<String>();
    while (tokenizer.hasMoreElements()) {
      tokens.add((String) tokenizer.nextElement());
    }

    if (tokens.size() < 5) {
      final String message =
        "Illegal file name format file file, " + feedFileName + ". Check file description for feed file Id," + feed.getFeedId() + ".";
      throw new FeedErrorException(message);
    }

    String token = tokens.get(0);
    String match = feed.getFromDataSource().getFeedDataSourceId();
    if (!match.equals(token)) {
      final String message =
        "FromDataSource does not match for FeedId: " + feed.getFeedId() + ". Found: " + token + ". Shound be: " + match + ".";
      throw new FeedErrorException(message);
    }

    token = tokens.get(1);
    match = feed.getToDataSource().getFeedDataSourceId();
    if (!match.equals(token)) {
      final String message =
        "ToDataSource does not match for FeedId: " + feed.getFeedId() + ". Found: " + token + ". Shound be: " + match + ".";
      throw new FeedErrorException(message);
    }

    token = tokens.get(2);
    match = feed.getFeedType().getFeedTypeId();
    if (!match.equals(token)) {
      final String message =
        "FeedType does not match for FeedId: " + feed.getFeedId() + ". Found: " + token + ". Shound be: " + match + ".";
      throw new FeedErrorException(message);
    }

    token = tokens.get(3);
    match = feed.getFeedProtocol().getFeedProtocolId();
    if (!match.equals(token)) {
      final String message =
        "FeedProtocol does not match for FeedId: " + feed.getFeedId() + ". Found: " + token + ". Shound be: " + match + ".";
      throw new FeedErrorException(message);
    }

    token = tokens.get(4);
    try {
      token = token.substring(0, token.indexOf(".txt"));
      dateFormat.parse(token);
    } catch (Exception e) {
      final String message = "Illegl date format for file, " + feedFileName + ". " + e.getMessage() + ".";
      throw new FeedErrorException(message);
    }
  }

  /**
   * The method should be used to close resources. Any core FifeCycleService
   * should make sure to call the shutdown() method in all phases as a final
   * processe of its life cycle.
   */
  public void shutdown() {
    // No resources to close...
  }
}
