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
package com.qagen.osfe.programData.readerWriter;

import com.qagen.osfe.dataAccess.vo.FeedGroup;
import org.dom4j.Element;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class FeedGroupRW extends ReaderWriter {
  private static final String PATH = "feedGroup";

  private static enum ATTRIBUTE {
    feedGroupId,
    allowConcurrentRuns,
    allowFailedStateRuns,
    collectPhaseStats
  }

  public FeedGroupRW() {
    super(PATH);
  }

  public void readData() {
    readDocument();

    final List<Element> rows = root.elements(ELEMENT.row.name());

    for (Element element : rows) {
      final FeedGroup feedGroup = new FeedGroup();

      feedGroup.setFeedGroupId(element.element(ATTRIBUTE.feedGroupId.name()).getText());
      feedGroup.setAllowConcurrentRuns(getBoolean(element, ATTRIBUTE.allowConcurrentRuns.name()));
      feedGroup.setAllowFailedStateRuns(getBoolean(element, ATTRIBUTE.allowFailedStateRuns.name()));
      feedGroup.setCollectPhaseStats(getBoolean(element, ATTRIBUTE.collectPhaseStats.name()));

      if (feedGroupService.findByPrimaryId(feedGroup.getFeedGroupId()) == null) {
        feedGroupService.insert(feedGroup);
      } else {
        feedGroupService.update(feedGroup);
      }
    }
  }

  public void writeData() {
    final List<FeedGroup> feedGroups = feedGroupService.findAll();
    final Element rows = document.addElement(ELEMENT.rows.name());

    for (FeedGroup feedGroup : feedGroups) {
      final Element row = rows.addElement(ELEMENT.row.name());

      row.addElement(ATTRIBUTE.feedGroupId.name()).setText(feedGroup.getFeedGroupId());
      row.addElement(ATTRIBUTE.allowConcurrentRuns.name()).setText(setBoolean(feedGroup.isAllowConcurrentRuns()));
      row.addElement(ATTRIBUTE.allowFailedStateRuns.name()).setText(setBoolean(feedGroup.isAllowFailedStateRuns()));
      row.addElement(ATTRIBUTE.collectPhaseStats.name()).setText(setBoolean(feedGroup.isCollectPhaseStats()));
    }

    saveDocument();
  }

  public static void main(String[] args) {
    final ReaderWriter ReaderWriter = new FeedGroupRW();
    ReaderWriter.writeData();
    ReaderWriter.readData();
  }
}
