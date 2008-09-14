package com.qagen.osfe.core.services.outbound;

import com.qagen.osfe.common.utils.Log;
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.MainLifeCycleService;
import com.qagen.osfe.core.Phase;
import com.qagen.osfe.core.loaders.PhasesLoader;
import com.qagen.osfe.dataAccess.vo.FeedJob;

import java.io.IOException;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class StandardPhaseLifeCycleService extends MainLifeCycleService {
  private PhasesLoader phaseConfigLoader;
  private List<Phase> phases;

  private static Log logger = Log.getInstance(StandardPhaseLifeCycleService.class);

  /**
   * The phase config loader parses and loads the list of phases defined within
   * the given feed document.
   * <p/>
   * <ul><li>Injection - required</li></ul>
   *
   * @param phaseConfigLoader reference to list of phases.
   */
  public void setPhaseConfigLoader(PhasesLoader phaseConfigLoader) {
    this.phaseConfigLoader = phaseConfigLoader;
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
   * OSFE uses dependency injection. This second pass should initialize objects
   * that have dependency on other objects.
   */
  public void initialize() {
    initPhases();
  }

  protected void initPhases() {
    phases = getPhases(phaseConfigLoader.getList());
  }

  /**
   * @throws FeedErrorException
   */
  public void execute() throws FeedErrorException {
    final FeedJob feedJob = context.getFeedJob();

    try {
      processPhases();
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
      shutdownPhases(phases);
      context.getFeedFileWriter().close();
    } catch (IOException e) {
      logger.warn("*** Unable to close feed file, " + context.getFeedFileName());
    }
  }

  protected void processPhases() {
    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      feedJobManager.startPhaseStats(phase, context);
      phase.execute();
      feedJobManager.endPhaseStats(phase, context);
    }
  }
}
