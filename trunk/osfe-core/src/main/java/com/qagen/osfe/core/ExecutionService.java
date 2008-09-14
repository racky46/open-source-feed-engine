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
 * <p/>
 * The ExecutionService abstract class extends EngineService and defines an
 * additional execute() method to the contract.
 */
public abstract class ExecutionService extends EngineService {

  public ExecutionService() {
  }


  /**
   * This method should contain the set of instructions necessary to perform the tasks of the given service.
   * <p/>
   * Example: The following example calls methods that operate on the standard even phases.
   * <hr><blockquote><pre>
   * public void execute() throws FeedErrorException {
   *     final FeedJob feedJob = context.getFeedJob();
   * <p/>
   *     try {
   *       doPreEventPhases();
   *       doBatchEventPhases();
   *       doPostEventPhases();
   * <p/>
   *       feedJobManager.moveToCompleted(feedJob, context);
   *     } catch (Exception e) {
   *       handleFailure(feedJob, e);
   *     } finally {
   *       shutdown();
   *     }
   *   }
   * </pre></blockquote><hr>
   *
   * @throws FeedErrorException
   */
  public abstract void execute() throws FeedErrorException;
}
