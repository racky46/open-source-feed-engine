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

import com.qagen.osfe.dataAccess.vo.Feed;
import com.qagen.osfe.programData.ProgramDataException;
import org.dom4j.Element;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p>
 *
 */
public class FeedRW extends ReaderWriter {
  private static final String PATH = "feed";

  private static enum ATTRIBUTE {
    feedId,
    activationDate,
    terminationDate,
    allowConcurrentRuns,
    allowFailedStateRuns,
    collectPhaseStats,
    restartAtCheckpoint,
    nextSequenceNumber,
    maxConcurrentRuns,
    feedDirectory,
    feedDocument,
    fromDataSourceId,
    toDataSourceId,
    feedTypeId,
    feedProtocolId,
    feedDirectionId,
    feedGroupId
  }

  public FeedRW() {
    super(PATH);
  }

  public void readData() {
    readDocument();

    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final List<Element> rows = root.elements(ELEMENT.row.name());

    try {
      for (Element element : rows) {
        final Feed feed = new Feed();

        feed.setFeedId(element.element(ATTRIBUTE.feedId.name()).getText());
        feed.setActivationDate(new Timestamp(sdf.parse(element.element(ATTRIBUTE.activationDate.name()).getText()).getTime()));
        feed.setTerminationDate(new Timestamp(sdf.parse(element.element(ATTRIBUTE.terminationDate.name()).getText()).getTime()));
        feed.setAllowConcurrentRuns(getBoolean(element, ATTRIBUTE.allowConcurrentRuns.name()));
        feed.setAllowFailedStateRuns(getBoolean(element, ATTRIBUTE.allowFailedStateRuns.name()));
        feed.setCollectPhaseStats(getBoolean(element, ATTRIBUTE.collectPhaseStats.name()));
        feed.setRestartAtCheckpoint(getBoolean(element, ATTRIBUTE.restartAtCheckpoint.name()));
        feed.setMaxConcurrentRuns(Integer.valueOf(element.element(ATTRIBUTE.maxConcurrentRuns.name()).getText()));
        feed.setFeedDirectory(element.element(ATTRIBUTE.feedDirectory.name()).getText());
        feed.setFeedDocument(element.element(ATTRIBUTE.feedDocument.name()).getText());
        feed.setFromDataSource(findDataSource(element.element(ATTRIBUTE.fromDataSourceId.name()).getText()));
        feed.setToDataSource(findDataSource(element.element(ATTRIBUTE.toDataSourceId.name()).getText()));
        feed.setFeedType(findFeedType(element.element(ATTRIBUTE.feedTypeId.name()).getText()));
        feed.setFeedProtocol(findFeedProtocol(element.element(ATTRIBUTE.feedProtocolId.name()).getText()));
        feed.setFeedDirection(findFeedDirection(element.element(ATTRIBUTE.feedDirectionId.name()).getText()));
        feed.setFeedGroup(findFeedGroup(element.element(ATTRIBUTE.feedGroupId.name()).getText()));

        final Feed temp = feedService.findByPrimaryId(feed.getFeedId());

        if (temp == null) {
          feed.setNextSequenceNumber(Integer.valueOf(element.element(ATTRIBUTE.nextSequenceNumber.name()).getText()));
          feedService.insert(feed);
        } else {
          feed.setNextSequenceNumber(temp.getNextSequenceNumber());
          feedService.update(feed);
        }
      }
    } catch (ParseException e) {
      throw new ProgramDataException(e);
    }
  }

  public void writeData() {
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final List<Feed> feeds = feedService.findAll();
    final Element rows = document.addElement(ELEMENT.rows.name());

    for (Feed feed : feeds) {
      final Element row = rows.addElement(ELEMENT.row.name());

      row.addElement(ATTRIBUTE.feedId.name()).setText(feed.getFeedId());
      row.addElement(ATTRIBUTE.activationDate.name()).setText(sdf.format(feed.getActivationDate()));
      row.addElement(ATTRIBUTE.terminationDate.name()).setText(sdf.format(feed.getTerminationDate()));
      row.addElement(ATTRIBUTE.allowConcurrentRuns.name()).setText(setBoolean(feed.getAllowConcurrentRuns()));
      row.addElement(ATTRIBUTE.allowFailedStateRuns.name()).setText(setBoolean(feed.getAllowFailedStateRuns()));
      row.addElement(ATTRIBUTE.collectPhaseStats.name()).setText(setBoolean(feed.getCollectPhaseStats()));
      row.addElement(ATTRIBUTE.restartAtCheckpoint.name()).setText(setBoolean(feed.getRestartAtCheckpoint()));
      row.addElement(ATTRIBUTE.nextSequenceNumber.name()).setText(feed.getNextSequenceNumber().toString());
      row.addElement(ATTRIBUTE.maxConcurrentRuns.name()).setText(feed.getMaxConcurrentRuns().toString());
      row.addElement(ATTRIBUTE.feedDirectory.name()).setText(feed.getFeedDirectory());
      row.addElement(ATTRIBUTE.feedDocument.name()).setText(feed.getFeedDocument());
      row.addElement(ATTRIBUTE.fromDataSourceId.name()).setText(feed.getFromDataSource().getFeedDataSourceId());
      row.addElement(ATTRIBUTE.toDataSourceId.name()).setText(feed.getToDataSource().getFeedDataSourceId());
      row.addElement(ATTRIBUTE.feedTypeId.name()).setText(feed.getFeedType().getFeedTypeId());
      row.addElement(ATTRIBUTE.feedProtocolId.name()).setText(feed.getFeedProtocol().getFeedProtocolId());
      row.addElement(ATTRIBUTE.feedDirectionId.name()).setText(feed.getFeedDirection().getFeedDirectionId());

      row.addElement(ATTRIBUTE.feedGroupId.name()).setText(feed.getFeedGroup() != null ? feed.getFeedGroup().getFeedGroupId() : "");
    }

    saveDocument();
  }

  public static void main(String[] args) {
    final ReaderWriter ReaderWriter = new FeedRW();
    ReaderWriter.writeData();
    ReaderWriter.readData();
  }
}
