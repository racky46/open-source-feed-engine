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

import com.qagen.osfe.dataAccess.vo.FeedType;
import org.dom4j.Element;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p>
 *
 */
public class FeedTypeRW extends ReaderWriter {
  private static final String PATH = "feedType";

  private static enum ATTRIBUTE {
    feedTypeId,
    description
  }

  public FeedTypeRW() {
    super(PATH);
  }

  public void readData() {
    readDocument();

    final List<Element> rows = root.elements(ELEMENT.row.name());

    for (Element element : rows) {
      final FeedType feedType = new FeedType();

      feedType.setFeedTypeId(element.element(ATTRIBUTE.feedTypeId.name()).getText());
      feedType.setDescription(element.element(ATTRIBUTE.description.name()).getText());

      if (feedTypeService.findByPrimaryId(feedType.getFeedTypeId()) == null) {
        feedTypeService.insert(feedType);
      } else {
        feedTypeService.update(feedType);
      }
    }
  }

  public void writeData() {
    final List<FeedType> feedTypes = feedTypeService.findAll();
    final Element rows = document.addElement(ELEMENT.rows.name());

    for (FeedType feedType : feedTypes) {
      final Element row = rows.addElement(ELEMENT.row.name());

      row.addElement(ATTRIBUTE.feedTypeId.name()).setText(feedType.getFeedTypeId());
      row.addElement(ATTRIBUTE.description.name()).setText(feedType.getDescription());
    }

    saveDocument();
  }

  public static void main(String[] args) {
    final ReaderWriter ReaderWriter = new FeedTypeRW();
    ReaderWriter.writeData();
    ReaderWriter.readData();
  }
}
