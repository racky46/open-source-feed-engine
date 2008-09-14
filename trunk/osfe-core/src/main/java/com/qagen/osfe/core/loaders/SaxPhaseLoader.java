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

import com.qagen.osfe.core.Loader;
import com.qagen.osfe.core.vo.SaxPhaseSetInfo;
import org.dom4j.Element;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class SaxPhaseLoader extends Loader {
  private enum ELEMENT {
    saxPhases,
    saxEventPhases,
    postEventPhases,
    preFeedFilePhases
  }

  private enum ATTRIBUTE {
    batchSize
  }

  private SaxPhaseSetInfo phaseSetInfo;

  public SaxPhaseLoader(Element root, String elementName) {
    super(root, elementName);
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  /**
   * Contains the instructions for parsing data from child elements within
   * the parent element. If the element is complex, other loaders should
   * be used within this method to parse different sub elements.
   *
   * @param parent container of child element to parse.
   */
  public void load(Element parent) {
    final Integer batchSize = Integer.parseInt(parent.attributeValue(ATTRIBUTE.batchSize.name()));

    phaseSetInfo = new SaxPhaseSetInfo(batchSize);

    PhasesLoader loader = new PhasesLoader(parent, ELEMENT.preFeedFilePhases.name());
    phaseSetInfo.setPreFeedFilePhases(loader.getList());

    final SaxEventPhaseElementLoader saxLoader = new SaxEventPhaseElementLoader(parent, ELEMENT.saxEventPhases.name());
    phaseSetInfo.setSaxEventPhaseInfoList(saxLoader.getPhases());

    loader = new PhasesLoader(parent, ELEMENT.postEventPhases.name());
    phaseSetInfo.setPostEventPhaseList(loader.getList());

  }

  /**
   * Useful for knowing the number of data objects created from the number of
   * elements that were parsed.  Also useful to know if the element set was
   * empty.
   *
   * @return 0 if no elements were parsed.
   */
  public int size() {
    return 0; // Size is not applicable here.
  }

  public SaxPhaseSetInfo getPhaseSetInfo() {
    return phaseSetInfo;
  }
}
