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
package com.qagen.osfe.core.services.inbound;

import com.qagen.osfe.common.utils.Log;
import com.qagen.osfe.core.*;
import com.qagen.osfe.core.loaders.EventPhasesLoader;
import com.qagen.osfe.core.utils.CheckpointHelper;
import com.qagen.osfe.dataAccess.vo.FeedCheckpoint;
import com.qagen.osfe.dataAccess.vo.FeedJob;

import java.io.IOException;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * The StandardFeedLifeCycle is the most common feed life cycle service.  It
 * can process most feed file configurations that use that standard phase
 * configuration where data records are represented within the feed file as
 * single rows.
 * <p/>
 * The standard phase configuration requires the following elements be defined
 * within a given feed file configuration document.
 * <ul>
 * <li>preEventPhases
 * <li>batchEventPhases
 * <li>postEventPhases
 * </ul>
 * Only the batchEventPhase must have at least one phase element definition.
 * <p/>
 * The format of the feed file doesn't matter as long as the resulting data
 * record is represented as a single row of data.  This means it can process
 * feeds formated as, delimited, fixed, XML and custom binary.
 */
public class EventPhaseLifeCycleService extends MainLifeCycleService {
  public enum EVENT_PHASE {
    preEventPhases,
    postEventPhases,
    batchEventPhases,
    preFeedFilePhases
  }

  private Splitter detailSplitter;
  private EventPhasesLoader phaseConfigLoader;
  private CheckpointService checkpointService;

  private List<Phase> preFilePhaseList;
  private List<Phase> preEventPhaseList;
  private List<Phase> batchPhaseList;
  private List<Phase> postEventPhaseList;

  private static Log logger = Log.getInstance(EventPhaseLifeCycleService.class);

  public EventPhaseLifeCycleService() {
  }

  /**
   * The life cycle of the set of detail phases are traversed using the detail splitter.
   * <p/>
   * <ul><li>Injection - required</li></ul>
   *
   * @param detailSplitter reference to the detail splitter.
   */
  public void setDetailSplitter(Splitter detailSplitter) {
    this.detailSplitter = detailSplitter;
  }

  /**
   * The PhaseConfigLoader parses and instantiates all phases.
   * <p/>
   * <ul><li>Injection - required</li></ul>
   *
   * @param phaseConfigLoader reference to phase configuration loader.
   */
  public void setPhaseConfigLoader(EventPhasesLoader phaseConfigLoader) {
    this.phaseConfigLoader = phaseConfigLoader;
  }

  /**
   * Determines whether checkpoint operations are performed.  If not set,
   * not check point operations will be performed.
   * <p/>
   * <ul><li>Injection - optional</li></ul>
   *
   * @param checkpointService reference to a class that extends CheckpointService.
   */
  public void setCheckpointService(CheckpointService checkpointService) {
    this.checkpointService = checkpointService;
  }

  /**
   * Stores the name of the given service as it is defined in the feed
   * configuration document.
   *
   * @return the name of the service as it is defined in the feed configuration
   *         document.
   */
  public String name() {
    return this.getClass().getSimpleName();
  }

  /**
   * Initialize service by peforming the following tasks:
   * <ul>
   * <li>load the feed file
   * <li>instantiate the row description loader
   * <li>load the splitters
   * </ul>
   */
  public void initialize() {
    initPhases();
  }

  /**
   * Initialize preFile, preEventPhases, batchPhases and postEventPhases.
   */
  protected void initPhases() {
    preFilePhaseList = getPhases(phaseConfigLoader.getPhaseInfoList(EVENT_PHASE.preFeedFilePhases.name()));
    context.setPreFeedFilePhases(preFilePhaseList);

    preEventPhaseList = getPhases(phaseConfigLoader.getPhaseInfoList(EVENT_PHASE.preEventPhases.name()));
    batchPhaseList = getPhases(phaseConfigLoader.getPhaseInfoList(EVENT_PHASE.batchEventPhases.name()));
    postEventPhaseList = getPhases(phaseConfigLoader.getPhaseInfoList(EVENT_PHASE.postEventPhases.name()));
  }

  /**
   * This method should contain the set of instructions necessary to perform
   * the tasks of the given service.  All exceptions are handled here by
   * catching Exception and calling method, handleFailure().
   */
  public void execute() throws FeedErrorException {
    final FeedJob feedJob = context.getFeedJob();

    try {
      doPreEventPhases();
      doBatchEventPhases();
      doPostEventPhases();

      feedJobManager.moveToCompleted(feedJob, context);
    } catch (Exception e) {
      handleFailure(feedJob, e);
    } finally {
      shutdown();
    }
  }

  /**
   * Depending on the behavior of the service, it's shutdown method may be
   * called in order to perform house keeping tasks such as closing files
   * and other depended services.
   */
  public void shutdown() {
    try {
      feedJobManager.savePhaseStats(context);
      doPhaseShutdown();
      FeedFileReader reader = context.getFeedFileReader();
      reader.close();
    } catch (IOException e) {
      logger.warn("*** Unable to close feed file, " + context.getFeedFileName());
    }
  }

  /**
   * Perform shutdown operations on all phases.
   */
  protected void doPhaseShutdown() {
    shutdownPhases(postEventPhaseList);
    shutdownPhases(batchPhaseList);
    shutdownPhases(preEventPhaseList);
    shutdownPhases(preFilePhaseList);
  }

  /**
   * Iterates and executes the list of preEventPhases.
   */
  private void doPreEventPhases() {
    final List<Phase> phases = preEventPhaseList;

    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      feedJobManager.startPhaseStats(phase, context);
      phase.execute();
      feedJobManager.endPhaseStats(phase, context);
    }
  }


  /**
   * Iterates and executes the list of batchEventPhases.
   */
  private void doBatchEventPhases() {
    // Must be called after initialization and before anything else.
    detailSplitter.prePhaseExecute();

    // Get checkpoint from context after checkpoint phase.initialize().
    if (checkpointService != null) {
      traversDetailWithCheckpoint(checkpointService.getCheckpoint());
    } else {
      traversDetailWithOutCheckpoint();
    }
  }

  protected void traversDetailWithOutCheckpoint() {
    final List<Phase> phases = batchPhaseList;

    while (detailSplitter.hasNextRow()) {
      for (Phase phase : phases) {
        context.setCurrentPhaseId(phase.getName());
        feedJobManager.startPhaseStats(phase, context);
        phase.execute();
        feedJobManager.endPhaseStats(phase, context);
      }
    }
  }

  protected void traversDetailWithCheckpoint(FeedCheckpoint checkpoint) {
    final List<Phase> phases = batchPhaseList;

    // Determine if a checkpoint has been created and if so, move to it.
    Boolean movingToCheckpoint = performCheckpoint(detailSplitter, checkpoint);

    while (detailSplitter.hasNextRow()) {
      for (Phase phase : phases) {
        // Bypass phases processing when restart from a checkpoint.
        if (!movingToCheckpoint) {
          context.setCurrentPhaseId(phase.getName());
          feedJobManager.startPhaseStats(phase, context);
          phase.execute();
          feedJobManager.endPhaseStats(phase, context);
        }

        // Checkpoint restart has be reached.
        if (movingToCheckpoint && checkpoint.getPhaseId().equals(phase.getName())) {
          movingToCheckpoint = false;
        }
      }
    }
  }

  /**
   * Checks to see if there is a check point.  If so, and the checkpoint phaseId
   * does not equal, NO_PHASE_ID, call the necessary opertions to move to the
   * current checkpoint.
   *
   * @param splitter   the splitter to see if it implments the interface CheckpointHandler.
   * @param checkpoint the checkpoint object associated with the given feed file.
   * @return true if a move to check point was initiated.
   */
  private Boolean performCheckpoint(Splitter splitter, FeedCheckpoint checkpoint) {
    // Move to checkpoint if splitter is instance of checkpoint
    if (splitter instanceof Checkpointable) {
      // Determine if a checkpoint has been created.
      if (checkpoint != null && !checkpoint.getPhaseId().equals(CheckpointHelper.NO_PHASE_ID)) {
        if (checkpointService != null) checkpointService.beforeCheckpoint();

        ((Checkpointable) splitter).moveToCheckpoint(checkpoint);

        if (checkpointService != null) checkpointService.afterCheckpoint();

        return true;
      }
    }
    return false;
  }

  /**
   * Iterates and executes the list of postEventPhases.
   */
  private void doPostEventPhases() {
    final List<Phase> phases = postEventPhaseList;

    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      feedJobManager.startPhaseStats(phase, context);
      phase.execute();
      feedJobManager.endPhaseStats(phase, context);
    }
  }
}
