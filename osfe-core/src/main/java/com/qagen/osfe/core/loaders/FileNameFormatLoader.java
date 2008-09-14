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
package com.qagen.osfe.core.loaders;

import com.qagen.osfe.core.Contextable;
import com.qagen.osfe.core.Loader;
import com.qagen.osfe.core.utils.ComponentBuilder;
import com.qagen.osfe.core.vo.Components;
import org.dom4j.Element;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class FileNameFormatLoader extends Loader implements Contextable {
  public static enum ELEMENT {
    components,
    fileNameFormat
  }

  private Components components;
  private Boolean isInitialized = false;

  /**
   * Constructor<p>
   * <p/>
   * This constructor in normally used for automatic dependency injection
   * when the feed config file is loaded.<p>
   * If this constructor is used then the following setters must be called:
   * <ul>
   * <li>setRoot(Element)
   * <li>setName(String)
   * <ul>
   */
  public FileNameFormatLoader() {
  }

  /**
   * Constructor
   *
   * @param root the element from which contains the sub element
   *             that will be parsed and referenced as the parent element.
   * @param name the name of the sub element to retrieve for data
   *             extraction.
   */
  public FileNameFormatLoader(Element root, String name) {
    super(root, name);
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    if (!isInitialized) {
      load(root, ELEMENT.fileNameFormat.name());
    }
    isInitialized = true;
  }

  /**
   * Contains the instructions for parsing data from child elements within
   * the parent element. If the element is complex, other loaders should
   * be used within this method to parse different sub elements.
   *
   * @param parent container of child element to parse.
   */
  public void load(Element parent) {
    final ComponentLoader loader = new ComponentLoader(parent);
    components = loader.getComponents();
  }

  /**
   * Useful for knowing the number of data objects created from the number of
   * elements that were parsed.  Also useful to know if the element set was
   * empty.
   *
   * @return 0 if no elements were parsed.
   */
  public int size() {
    return (components != null) ? components.getProperties().size() : 0;
  }

  /**
   * Retrieves a reference to the components object.
   *
   * @return reference to the components object.
   */
  public Components getComponents() {
    return components;
  }

  /**
   * Constructs the file name for the list of component properties.
   *
   * @return a filename constructed from the list of properties.
   */
  public String getFileName() {
    if (!isInitialized) {
      initialize();
    }

    final ComponentBuilder builder = new ComponentBuilder(context);
    return builder.buildComponent(components.getProperties());
  }
}
