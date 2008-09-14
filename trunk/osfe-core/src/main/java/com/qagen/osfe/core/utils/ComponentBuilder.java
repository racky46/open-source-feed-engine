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
package com.qagen.osfe.core.utils;

import com.qagen.osfe.common.utils.NumberFormatter;
import com.qagen.osfe.core.EngineContext;
import com.qagen.osfe.core.loaders.ComponentLoader;
import com.qagen.osfe.core.vo.Property;
import com.qagen.osfe.dataAccess.context.DataAccessContext;
import com.qagen.osfe.dataAccess.service.FeedService;
import com.qagen.osfe.dataAccess.vo.Feed;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: htaylor
 * Date: Aug 6, 2008
 * Time: 11:33:12 AM
 */
public class ComponentBuilder {
  private EngineContext context;

  public ComponentBuilder(EngineContext context) {
    this.context = context;
  }

  public String buildComponent(List<Property> properties) {
    final StringBuilder builder = new StringBuilder();

    for (Property property : properties) {
      final String name = property.getName();
      final String value = property.getValue();

      switch (ComponentLoader.ELEMENT.valueOf(name)) {
        case feedId:
          addFeedId(builder);
          break;
        case sequenceNumber:
          addSequenceNumber(builder);
          break;
        case timestamp:
          addFormattedDateTime(value, builder);
          break;
        case separator:
          addLiteral(value, builder);
          break;
        case literal:
          addLiteral(value, builder);
          break;
        case delimiter:
          addDelimiter(builder);
          break;
        case lineCount:
          addLineCount(value, builder);
          break;
      }
    }

    return builder.toString();
  }

  private void addLineCount(String pattern, StringBuilder builder) {
    final Integer count = context.getProcessedRowCount();

    if (pattern == null) {
      builder.append(count);
    } else {
      builder.append(NumberFormatter.formatNumber(count, pattern));
    }
  }

  private void addLiteral(String value, StringBuilder builder) {
    builder.append(value);
  }

  private void addFormattedDateTime(String value, StringBuilder builder) {
    final SimpleDateFormat sdf = new SimpleDateFormat(value);
    final String format = sdf.format(context.getTimestamp());

    builder.append(format);
  }

  private void addFeedId(StringBuilder builder) {
    final String feedId = context.getFeed().getFeedId();

    builder.append(feedId);
  }

  private void addDelimiter(StringBuilder builder) {
    final String delimiter = context.getDelimiterValue();

    builder.append(delimiter);
  }

  private void addSequenceNumber(StringBuilder builder) {
    Integer sequenceNumber = context.getSequenceNumber();

    if (sequenceNumber == null) {
      final Feed feed = context.getFeed();
      final FeedService service = (FeedService) DataAccessContext.getBean(FeedService.SERVICE_ID);

      sequenceNumber = service.getNextSequenceNumber(feed.getFeedId());
      feed.setNextSequenceNumber(sequenceNumber);
      context.setSequenceNumber(sequenceNumber);
    }

    builder.append(sequenceNumber);
  }

}
