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
import com.qagen.osfe.core.vo.PhaseSetInfo;
import org.dom4j.Element;

public class PhaseConfigLoader extends Loader {
  public enum ELEMENT {
    phase,
    eventPhases,
    preEventPhases,
    postEventPhases,
    batchEventPhases,
    preFeedFilePhases
  }

  public enum ATTRIBUTE {
    name,
    enable,
    className,
    batchSize
  }

  private PhaseSetInfo phaseSetInfo;

  public PhaseConfigLoader(Element root) {
    super(root, ELEMENT.eventPhases.name());
  }

  protected void load(Element parent) {
    final Integer batchSize = Integer.parseInt(parent.attributeValue(ATTRIBUTE.batchSize.name()));

    phaseSetInfo = new PhaseSetInfo(batchSize);

    PhaseElementLoader loader = new PhaseElementLoader(parent, ELEMENT.preFeedFilePhases.name());
    phaseSetInfo.setPreFeedFilePhases(loader.getList());

    loader = new PhaseElementLoader(parent, ELEMENT.preEventPhases.name());
    phaseSetInfo.setPreEventPhaseList(loader.getList());

    loader = new PhaseElementLoader(parent, ELEMENT.batchEventPhases.name());
    phaseSetInfo.setBatchPhaseList(loader.getList());

    loader = new PhaseElementLoader(parent, ELEMENT.postEventPhases.name());
    phaseSetInfo.setPostEventPhaseList(loader.getList());

  }

  public int size() {
    return 0;  // Size is not applicable here.
  }

  public PhaseSetInfo getPhaseSetInfo() {
    return phaseSetInfo;
  }
}
