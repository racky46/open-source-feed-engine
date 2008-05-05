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

import com.qagen.osfe.core.EngineContext;
import com.qagen.osfe.core.Phase;
import com.qagen.osfe.core.utils.BeanPopulator;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;


/**
 * Author: Gregg Bolinger, Hycel Taylor
 * <p/>
 */
public class PhasePropertyReflectionTest {

  @Test
  public void runTest() {
    final TestPhase phase = new TestPhase();
    final Map<String, String> properties = new HashMap<String, String>();

    properties.put("scoreRangCheck", "true");
    properties.put("courseNumber", "405");

    BeanPopulator.populatePhaseProperties(phase, properties);

    Assert.assertEquals(Boolean.TRUE, phase.getScoreRangCheck());
    Assert.assertEquals(new Integer(405), phase.getCourseNumber());
  }

  public class TestPhase extends Phase {
    private Boolean scoreRangCheck;
    private Integer courseNumber;

    public TestPhase(EngineContext context, String name) {
      super(context, name);
    }

    public TestPhase() {
      super(null, null);
    }

    public Boolean getScoreRangCheck() {
      return scoreRangCheck;
    }

    public void setScoreRangCheck(Boolean scoreRangCheck) {
      this.scoreRangCheck = scoreRangCheck;
    }

    public Integer getCourseNumber() {
      return courseNumber;
    }

    public void setCourseNumber(Integer courseNumber) {
      this.courseNumber = courseNumber;
    }

    public void initialize() {
    }

    public void execute() {
    }

    public void shutdown() {
    }
  }
}
