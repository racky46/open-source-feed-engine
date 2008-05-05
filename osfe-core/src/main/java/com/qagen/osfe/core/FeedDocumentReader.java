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
package com.qagen.osfe.core;

import static com.qagen.osfe.common.CommonConstants.SLASH;
import com.qagen.osfe.common.CommonException;
import com.qagen.osfe.common.utils.DirectoryHelper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class FeedDocumentReader  {

  public static Document parseDocument(String fileName) {
    try {
      final String homeDir = DirectoryHelper.getHomeDirectory();
      final SAXReader reader = new SAXReader();
      return reader.read(homeDir + SLASH + fileName);
    } catch (DocumentException e) {
      throw new CommonException(e);
    }
  }
}
