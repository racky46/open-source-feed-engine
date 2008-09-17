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

import com.qagen.osfe.dataAccess.vo.FeedDataSource;
import org.dom4j.Element;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p>
 *
 */
public class DataSourceRW extends ReaderWriter {
  private static final String PATH = "feedDataSource";

  private static enum ATTRIBUTE {
    feedDataSourceId,
    description
  }

  public DataSourceRW() {
    super(PATH);
  }

  public void readData() {
    readDocument();

    final List<Element> rows = root.elements(ELEMENT.row.name());

    for (Element element : rows) {
      final FeedDataSource dataSource = new FeedDataSource();

      dataSource.setFeedDataSourceId(element.element(ATTRIBUTE.feedDataSourceId.name()).getText());
      dataSource.setDescription(element.element(ATTRIBUTE.description.name()).getText());

      if (dataSourceService.findByPrimaryId(dataSource.getFeedDataSourceId()) == null) {
        dataSourceService.insert(dataSource);
      } else {
        dataSourceService.update(dataSource);
      }
    }
  }

  public void writeData() {
    final List<FeedDataSource> dataSources = dataSourceService.findAll();
    final Element rows = document.addElement(ELEMENT.rows.name());

    for (FeedDataSource dataSource : dataSources) {
      final Element row = rows.addElement(ELEMENT.row.name());

      row.addElement(ATTRIBUTE.feedDataSourceId.name()).setText(dataSource.getFeedDataSourceId());
      row.addElement(ATTRIBUTE.description.name()).setText(dataSource.getDescription());
    }

    saveDocument();
  }

  public static void main(String[] args) {
    final ReaderWriter ReaderWriter = new DataSourceRW();
    ReaderWriter.writeData();
    ReaderWriter.readData();
  }
}
