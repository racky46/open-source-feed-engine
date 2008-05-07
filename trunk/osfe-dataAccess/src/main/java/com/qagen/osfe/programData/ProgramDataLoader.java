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

import com.qagen.osfe.common.utils.DirectoryHelper;
import static com.qagen.osfe.common.CommonConstants.SLASH;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * Author: Hycel Taylor
 */
public class ProgramDataLoader {
  private static final String RESOURCE_PACKAGE = DirectoryHelper.getHomeDirectory() +  "programData";

  private enum ELEMENT {
    configFiles
  }

  public ProgramDataLoader(String fileName) {
    final Element root = parseDocument(fileName).getRootElement();
    processConfigFiles(root);
  }

  @SuppressWarnings("unchecked")
  private void processConfigFiles(final Element root) {
    final List<Element> elements = root.element(ELEMENT.configFiles.name()).elements();

    for (Element element : elements) {
      final String configFileName = element.getTextTrim();
      new ConfigFileLoader(configFileName);
    }
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

  public static void main(String[] args) {
    new ProgramDataLoader("/config.xml");
  }
}
