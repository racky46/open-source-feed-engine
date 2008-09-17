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

import com.qagen.osfe.common.utils.DirectoryHelper;
import com.qagen.osfe.common.FeedConstants;
import com.qagen.osfe.programData.ProgramDataException;
import com.qagen.osfe.dataAccess.service.*;
import com.qagen.osfe.dataAccess.context.DataAccessContext;
import com.qagen.osfe.dataAccess.vo.*;
import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Author: Hycel Taylor
 * <p>
 *
 */
public abstract class ReaderWriter implements FeedConstants {
  protected static final String RESOURCE_PACKAGE = DirectoryHelper.getHomeDirectory() + "programData";
  protected Element root;
  protected Document document;
  protected String fileName;

  protected enum ELEMENT {
    row,
    rows
  }

  public ReaderWriter(String fileName) {
    document = DocumentHelper.createDocument();
    this.fileName = RESOURCE_PACKAGE + SLASH + fileName + SLASH + "config.xml";
  }

  protected void readDocument() {
    try {
      final SAXReader reader = new SAXReader();
      document = reader.read(this.fileName);
      root = document.getRootElement();
    } catch (DocumentException e) {
      throw new ProgramDataException(e);
    }
  }

  protected void saveDocument() {
    try {
      final OutputFormat format = OutputFormat.createPrettyPrint();
      final FileWriter writer = new FileWriter(fileName);
      final XMLWriter xmlWriter = new XMLWriter(writer, format);

      xmlWriter.write(document);
      xmlWriter.close();
    } catch (IOException e) {
      throw new ProgramDataException(e);
    }
  }

  protected FeedService feedService = (FeedService) DataAccessContext.getBean(FeedService.SERVICE_ID);
  protected FeedDataSourceService dataSourceService = (FeedDataSourceService) DataAccessContext.getBean(FeedDataSourceService.SERVICE_ID);
  protected FeedTypeService feedTypeService = (FeedTypeService) DataAccessContext.getBean(FeedTypeService.SERVICE_ID);
  protected FeedProtocolService feedProtocolService = (FeedProtocolService) DataAccessContext.getBean(FeedProtocolService.SERVICE_ID);
  protected FeedDirectionService feedDirectionService = (FeedDirectionService) DataAccessContext.getBean(FeedDirectionService.SERVICE_ID);
  protected FeedGroupService feedGroupService = (FeedGroupService) DataAccessContext.getBean(FeedGroupService.SERVICE_ID);

  
  protected FeedDataSource findDataSource(String primaryId) {
    final FeedDataSource dataSource = dataSourceService.findByPrimaryId(primaryId);

    if (dataSource == null) {
      throw new ProgramDataException("Bad datasourceId, " + primaryId + ".");
    }

    return dataSource;
  }

  protected FeedType findFeedType(String primaryId) {
    final FeedType feedType = feedTypeService.findByPrimaryId(primaryId);

    if (feedType == null) {
      throw new ProgramDataException("Bad feedTypeId, " + primaryId + ".");
    }

    return feedType;
  }

  protected FeedProtocol findFeedProtocol(String primaryId) {
    final FeedProtocol source = feedProtocolService.findByPrimaryId(primaryId);

    if (source == null) {
      throw new ProgramDataException("Bad feedProtocolId, " + primaryId + ".");
    }

    return source;
  }

  protected FeedDirection findFeedDirection(String primaryId) {
    final FeedDirection source = feedDirectionService.findByPrimaryId(primaryId);

    if (source == null) {
      throw new ProgramDataException("Bad feedDirectionId, " + primaryId + ".");
    }

    return source;
  }

  // FeedGroup may return a null, signifying no group.
  protected FeedGroup findFeedGroup(String primaryId) {
    if ((primaryId != null) && (primaryId.trim().length() > 0)) {
      final FeedGroup source = feedGroupService.findByPrimaryId(primaryId);

      if (source == null) {
        throw new ProgramDataException("Bad feedDirectionId, " + primaryId + ".");
      }

      return source;
    }
    return null;
  }

  public abstract void readData();

  public abstract void writeData();

  Boolean getBoolean(Element element, String name) {
    final String value = element.element(name).getText();

    if (value.equals("1")) {
      return true;
    }

    return false;
  }

  String setBoolean(Boolean value) {
    if ((value == null) || (!value)) {
      return "0";
    }

    return "1";
  }
}
