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

/**
 * Author: Hycel Taylor
 * <p>
 * The Phase abstract class defines the contract for all concrete Phase
 * implementations.  Concrete classes that extend phase most often perform
 * a specific set of operations on a given set of Row objects during each
 * iteration of a feed phase life cycle.  However, a phase can perform many
 * types of operations that may have nothing to do with operations on the set
 * of Row objects.  The following are just a few example of types of phase
 * implementations:
 * <ul>
 * <li>FileNameValidationPhase: checks that the file name is valid during the
 *     preFeedFilePhase phase lifecycle.
 * <li>FooterPhase:  checks that the number of detail rows defined in the
 *     footer row is the same number of actual rows in the feed file.  Executed
 *     in the preEventPhase life cycle.
 * <li>GradingPhase: grade test scores during the batchEventPhase phase life cycle.
 * <li>StatsPhase: calls set of stored procedures to perform statistical analysis
 *     at the end of the feed process during the postEventPhase life cycle.
 * </ul>
 */
public abstract class Phase {
  protected final EngineContext context;
  protected final String name;

  /**
   * Constructor
   *
   * @param context reference to the engine context.
   * @param name uniquely identifies the given phase.
   */
  public Phase(EngineContext context, String name) {
    this.context = context;
    this.name = name;
  }

  /**
   * Uniquely identifies a phase within a given set of phases.
   *
   * @return the name of the phase
   */
  public String getName() {
    return name;
  }

  /**
   * Use this method to initialize references to other enging context objects and
   * not it's constructor.  Since a given phase may depend on other objects which
   * are loaded from the configuration document such as splitters, properties and
   * services, OSFE initializes phases in two passes.  The first pass instantiates
   * all phases by calling the phase constructor and passes it the engine context.
   * The second pass calls the initialization method.  It is during the second
   * pass that a phase will be able to reference any objects that have been loaded
   * from the feed configuration document.
   */
  public abstract void initialize();

  /**
   * During each iteration a given feed phase life cycle, as the set of phases
   * within the phase life cycle is traversed, the execute method of the
   * given phase is called.  The execute method call contains the necessary
   * instructions to perform the phases intended goal.
   * <p>
   * Example: This is an example of a filter phase that disables a given row if
   *          the student is not in good standing.
   * <hr><blockquote><pre>
   * public void execute() {
   *   final List<DetailRow> rows = (List<DetailRow>) context.get(AcmeConstants.detailData.name());
   *
   *   context.resentCurrentRowIndex();
   *   for (DetailRow row : rows) {
   *     context.incrementCurrentRowIndex();
   *     if (row.getEnable()) {
   *       final String studentId = row.getStudentId();
   *
   *       if (!studentInGoodStanding(studentId)) {
   *         row.setEnable(false);
   *         context.incrementRejectedRowCount();
   *       } else {
   *         context.incrementProcessedRowCount();
   *       }
   *     }
   *   }
   * }
   * </pre></blockquote><hr>
   */
  public abstract void execute();

  /**
   * The method should be used to close resources. Any core FifeCycleService
   * should make sure to call the shutdown() method in all phases as a final
   * processe of its life cycle.
   */
  public abstract void shutdown();

}
