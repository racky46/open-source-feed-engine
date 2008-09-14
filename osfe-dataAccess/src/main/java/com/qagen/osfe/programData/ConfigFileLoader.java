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
package com.qagen.osfe.programData;

import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.qagen.osfe.common.utils.DirectoryHelper;

/**
 * Author: Hycel Taylor
 * <p>
 * 
 */
public class ConfigFileLoader {
  private enum ELEMENT {
    table,
    row,
    rows,
    column,
    columns
  }

  private enum ATTRIBUTE {
    name,
    isString,
    bean
  }

  private static final String RESOURCE_PACKAGE = DirectoryHelper.getHomeDirectory() +  "programData";
  private static final String APP_CONTEXT = "OSFEContext.xml";
  private static final String COMMA = ",";
  private static final String SINGLE_QUOTE = "'";

  private Map<String, ColumnInfo> columnInfoMap;
  private String tableName;

  private static Logger logger = Logger.getLogger(ConfigFileLoader.class.getName());

  public ConfigFileLoader(String filename) {
    logger.info("***** Loading program data for " + filename + " *****\n");

    final Element root = parseDocument(filename).getRootElement();
    initTableName(root);
    loadColumnInfo(root);
    loadData(root);
  }

  private Document parseDocument(String fileName) {
    try {
      fileName = RESOURCE_PACKAGE + fileName;
      final SAXReader reader = new SAXReader();
      return reader.read(fileName);
    } catch (DocumentException e) {
      throw new ProgramDataException(e);
    }
  }

  private void initTableName(final Element root) {
    tableName = root.element(ELEMENT.table.name()).attribute(ATTRIBUTE.name.name()).getValue();
  }

  @SuppressWarnings("unchecked")
  private void loadColumnInfo(final Element root) {
    final List<Element> elements = root.element(ELEMENT.table.name()).element(ELEMENT.columns.name()).elements();

    columnInfoMap = new HashMap<String, ColumnInfo>();
    for (Element element : elements) {
      final String name = element.attribute(ATTRIBUTE.name.name()).getValue();
      final Boolean isString = Boolean.parseBoolean(element.attribute(ATTRIBUTE.isString.name()).getValue());
      final ColumnInfo info = new ColumnInfo(name, isString);
      columnInfoMap.put(name, info);
    }
  }

  @SuppressWarnings("unchecked")
  private void loadData(Element root) {
    try {
      final List<Element> elements = root.element(ELEMENT.table.name()).element(ELEMENT.rows.name()).elements(ELEMENT.row.name());
      final ApplicationContext context = new ClassPathXmlApplicationContext(APP_CONTEXT);

      final DataSource dataSource = (DataSource) context.getBean("dataSource");
      final Connection con = dataSource.getConnection();

      for (Element element : elements) {
        final String statement = getStatement(element);
        final PreparedStatement preparedStatement = con.prepareStatement(statement);

        preparedStatement.execute();
      }
    } catch (SQLException e) {
      throw new ProgramDataException(e);
    }
  }

  @SuppressWarnings("unchecked")
  private String getStatement(Element root) {
    final List<Element> elements = root.elements();
    final StringBuilder statement = new StringBuilder();

    statement.append("insert ignore into ").append(tableName).append(" (");

    int count = 1;
    for (Element element : elements) {
      statement.append(element.getName());

      if (count++ < elements.size()) {
        statement.append(COMMA);
      }
    }

    statement.append(") values(");

    count = 1;
    for (Element element : elements) {
      final String name = element.getName();
      final String value = element.getTextTrim();
      final ColumnInfo info = columnInfoMap.get(name);

      if (info.isString()) {
        statement.append(SINGLE_QUOTE).append(value).append(SINGLE_QUOTE);
      } else {
        statement.append(value);
      }

      if (count++ < elements.size()) {
        statement.append(COMMA);
      }
    }

    statement.append(")");

    return statement.toString();
  }

  private class ColumnInfo {
    private final String name;
    private final Boolean isString;

    public ColumnInfo(String name, Boolean isString) {
      this.name = name;
      this.isString = isString;
    }

    public String getName() {
      return name;
    }

    public Boolean isString() {
      return isString;
    }
  }
}
