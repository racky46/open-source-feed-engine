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
package com.qagen.osfe.examples.acme.phases;

import com.qagen.osfe.core.Phase;
import com.qagen.osfe.examples.acme.AcmeConstants;
import com.qagen.osfe.examples.acme.GradingStatistics;

/**
 * Author: Hycel Taylor
 * <p/>
 * Computes the class average and echos it to the console.
 */
public class StatsPhase extends Phase {

  public void initialize() {
  }

  /**
   * Computes the class average and echos it to the console.
   */
  public void execute() {
    final GradingStatistics stats = (GradingStatistics) context.get(AcmeConstants.stats.name());
    final Float average = (float) stats.getTotalScore().intValue() / (float) stats.getExamCount().intValue();

    System.out.println("The class average for " + stats.getExamCount() + " students: " + average);
  }

  /**
   * The method should be used to close resources. Any core FifeCycleService
   * should make sure to call the shutdown() method in all phases as a final
   * processe of its life cycle.
   */
  public void shutdown() {
    // No resources to close...
  }
}
