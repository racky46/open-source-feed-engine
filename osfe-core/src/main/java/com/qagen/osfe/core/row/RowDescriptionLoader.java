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
package com.qagen.osfe.core.row;

import com.qagen.osfe.core.Loader;
import org.dom4j.Element;

import java.util.Map;

/**
 * Author : Hycel Taylor
 * <p/>
 * The abstract RowDescriptionLoader class uses the loader design pattern
 * and extends the Loader contract by adding the getRows() method. Loaders
 * that extend this class should perform the task of loading row descriptions
 * from XML feed file documents.
 */
public abstract class RowDescriptionLoader extends Loader {
  protected Map<String, RowDescription> rows;

  /**
   * Constructor
   *
   * @param root        the element from which contains the sub element
   *                    that will be parsed and referenced as the parent element.
   * @param elementName the name of the sub element to retrieve for data
   *                    extraction.
   */
  protected RowDescriptionLoader(Element root, String elementName) {
    super(root, elementName);
  }

  /**
   * Returns a map of RowDescription objects. The map will often contain the
   * following RowDescription objects.
   * <ul>
   * <li> header - describes the attributes of the header row of a feed document.
   * <li> detail - describes the attributes of the detail row of a feed document.
   * <li> footer - describes the attributes of the footer row of a feed document.
   * </ul>
   *
   * @return map of RowDescription objects.
   */
  public abstract Map<String, RowDescription> getRows();

}
