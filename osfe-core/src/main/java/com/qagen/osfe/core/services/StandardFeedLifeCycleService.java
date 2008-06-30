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
package com.qagen.osfe.core.services;

import com.qagen.osfe.common.utils.Log;
import com.qagen.osfe.core.*;
import com.qagen.osfe.core.loaders.SplitterConfigLoader;
import com.qagen.osfe.core.phases.CheckpointPhase;
import com.qagen.osfe.core.row.RowDescriptionLoader;
import com.qagen.osfe.dataAccess.vo.FeedCheckpoint;
import com.qagen.osfe.dataAccess.vo.FeedJob;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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
public class StandardFeedLifeCycleService extends FeedLifeCycleService {
  // This LifeCycle service is responsible for loader the splitters and row description.
  private enum LOADERS {
    splitterLoader("splitterLoader"),
    rowDescriptionLoader("rowDescriptionLoader");

    private String value;

    LOADERS(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  // This LifeCycle service excpects a header, detail & footer splitters.
  public static enum SPLITTERS {
    header("header"),
    detail("detail"),
    footer("footer");

    private String value;

    SPLITTERS(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  private static Log logger = Log.getInstance(StandardFeedLifeCycleService.class);

  /**
   * Constructor
   *
   * @param context references the engine context.
   */
  public StandardFeedLifeCycleService(EngineContext context) {
    super(context);
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
    initRowLoader();
    initSplitters();
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
      FeedFileReader reader =  context.getFeedFileReader();
      reader.close();
    } catch (IOException e) {
      logger.warn("*** Unable to close feed file, " + context.getFeedFileName());
    }
  }

  /**
   * Perform shutdown operations on all phases.
   */
  protected void doPhaseShutdown() {
    shutdownPhases(context.getPostEventPhases());
    shutdownPhases(context.getBatchEventPhases());
    shutdownPhases(context.getPreEventPhases());
    shutdownPhases(context.getPreFeedFilePhases());
  }

  /**
   * Initialize the row description loader and store it in the context.
   */
  protected void initRowLoader() {
    final Loader loader = context.getLoaderMap().get(LOADERS.rowDescriptionLoader.getValue());
    final RowDescriptionLoader rowDescriptionLoader = ((RowDescriptionLoader) loader);

    context.setRowDescriptionLoader(rowDescriptionLoader);
  }

  /**
   * Load the header, detail and footer splitter and store the in the context.
   */
  protected void initSplitters() {
    final Loader loader = context.getLoaderMap().get(LOADERS.splitterLoader.getValue());
    final Map<String, String> map = ((SplitterConfigLoader) loader).getMap();
    final List<Splitter> splitters = new ArrayList<Splitter>();

    Splitter header = loadSplitter(SPLITTERS.header.getValue(), map.get(SPLITTERS.header.getValue()));
    context.setHeaderSplitter(header);
    splitters.add(header);

    Splitter detail = loadSplitter(SPLITTERS.detail.getValue(), map.get(SPLITTERS.detail.getValue()));
    context.setDetailSplitter(detail);
    splitters.add(detail);

    Splitter footer = loadSplitter(SPLITTERS.footer.getValue(), map.get(SPLITTERS.footer.getValue()));
    context.setFooterSplitter(footer);
    splitters.add(footer);

    // First find the splitter that will instantiate the FeedFileReader.
    for (Splitter splitter : splitters) {
      if (splitter instanceof SplitterFileOpener) {
        ((SplitterFileOpener) splitter).openFeedFileReader();
      }
    }

    // Initialize all splitters.
    for (Splitter splitter : splitters) {
      splitter.initialize();
    }
  }

  /**
   * Instantiates a specific splitter using java reflection.
   *
   * @param splitterName uniquely identifies the splitter.
   * @param className    the full class name of the splltter to load.
   * @return the instantiated splitter.
   * @throws FeedErrorException wrapps the original exception.
   */
  private Splitter loadSplitter(String splitterName, String className) {
    try {
      final Class clazz = Class.forName(className);
      final Class argTypes[] = new Class[]{EngineContext.class, String.class};
      final Constructor constructor = clazz.getConstructor(argTypes);
      return (Splitter) constructor.newInstance(context, splitterName);

    } catch (ClassNotFoundException e) {
      throw new FeedErrorException(e);
    } catch (NoSuchMethodException e) {
      throw new FeedErrorException(e);
    } catch (InstantiationException e) {
      throw new FeedErrorException(e);
    } catch (IllegalAccessException e) {
      throw new FeedErrorException(e);
    } catch (InvocationTargetException e) {
      throw new FeedErrorException(e);
    }
  }

  /**
   * Iterates and executes the list of preEventPhases.
   */
  private void doPreEventPhases() {
    final List<Phase> phases = context.getPreEventPhases();

    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      feedJobManager.startPhaseStats(phase, context);
      phase.initialize();
      phase.execute();
      feedJobManager.endPhaseStats(phase, context);
    }
  }

  /**
   * Iterates and executes the list of batchEventPhases.
   */
  private void doBatchEventPhases() {
    final Splitter splitter = context.getDetailSplitter();
    final List<Phase> phases = context.getBatchEventPhases();

    // perform batch phase initialization.
    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      feedJobManager.startPhaseStats(phase, context);
      phase.initialize();
      feedJobManager.endPhaseStats(phase, context);
    }

    // Must be called after initialization and before anything else.
    splitter.prePhaseExecute();

    // Get checkpoint from context after checkpoint phase.initialize().
    final FeedCheckpoint checkpoint = context.getCheckpoint();

    // Determine if a checkpoint has been created and if so, move to it.
    Boolean movingToCheckpoint = performCheckpoint(splitter, checkpoint);

    while (splitter.hasNextRow()) {
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
    if (splitter instanceof CheckpointHandler) {
      // Determine if a checkpoint has been created.
      if (checkpoint != null && !checkpoint.getPhaseId().equals(CheckpointPhase.NO_PHASE_ID)) {
        final CheckpointService service = (CheckpointService) context.getServiceMap(SERVICES.checkpointService.getValue());
        if (service != null) service.beforeCheckpoint();
        ((CheckpointHandler) splitter).moveToCheckPoint(checkpoint);
        if (service != null) service.afterCheckpoint();
        return true;
      }
    }
    return false;
  }

  /**
   * Iterates and executes the list of postEventPhases.
   */
  private void doPostEventPhases() {
    final List<Phase> phases = context.getPostEventPhases();

    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      feedJobManager.startPhaseStats(phase, context);
      phase.initialize();
      phase.execute();
      feedJobManager.endPhaseStats(phase, context);
    }
  }
}
