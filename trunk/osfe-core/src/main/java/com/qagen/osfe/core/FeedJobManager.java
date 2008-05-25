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

import static com.qagen.osfe.common.FeedConstants.*;
import com.qagen.osfe.common.utils.DirectoryHelper;
import com.qagen.osfe.core.utils.FileMoveHelper;
import com.qagen.osfe.dataAccess.context.DataAccessContext;
import com.qagen.osfe.dataAccess.service.*;
import com.qagen.osfe.dataAccess.vo.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * Author: Hycel Taylor
 * <p>
 * The FeedJobManager is a helper class and contains many common tasks for
 * managing a complete feed life cycle.  Other classes such as the
 * StandardStandardFeedLifeCycleService and the AbstractFeedEngine use this
 * class to simplify and handle most common tasks.
 */
public class FeedJobManager {
  final private FeedJobManagerService jobManagerService;
  final private FeedService feedService;
  final private FeedFileService feedFileService;
  final private CheckpointService checkpointService;
  final private FeedPhaseStatsService feedPhaseStatsService;

  /**
   * Constructor
   */
  public FeedJobManager() {
    feedService = (FeedService) DataAccessContext.getBean(FeedService.SERVICE_ID);
    feedFileService = (FeedFileService) DataAccessContext.getBean(FeedFileService.SERVICE_ID);
    jobManagerService = (FeedJobManagerService) DataAccessContext.getBean(FeedJobManagerService.SERVICE_ID);
    checkpointService = (CheckpointService) DataAccessContext.getBean(CheckpointService.SERVICE_ID);
    feedPhaseStatsService = (FeedPhaseStatsService) DataAccessContext.getBean(FeedPhaseStatsService.SERVICE_ID);
  }

  /**
   * Returns the feed directory related to the OSFE home directory for the givne feed.
   *
   * @param feed contains the feed directory information.
   * @return return the directory concatentated with the OSFE home directory.
   */
  private String getHomeDirectory(Feed feed) {
    return DirectoryHelper.getHomeDirectory() + feed.getFeedDirectory();
  }

  /**
   * Moves the feed file associated to the given feed job from the specified
   * source directory to the specified destination directory.
   *
   * @param feedJob   identifies the feed file to move.
   * @param sourceDir defines where to move the file from.
   * @param destDir   defines where to move the file to.
   */
  private void moveFeedFile(FeedJob feedJob, String sourceDir, String destDir) {
    final FeedFile feedFile = feedJob.getFeedFile();
    final String fileName = feedFile.getFeedFileName();
    final String homeDir = getHomeDirectory(feedFile.getFeed());

    FileMoveHelper.moveFromDirToDir(homeDir, fileName, fileName, sourceDir, destDir);
  }

  /**
   * Retrieves the Feed object for the given feedId.
   *
   * @param feedId idenitfies the Feed object to retrieve.
   * @return feed object.
   * @throws FeedErrorException if object not found.
   */
  public Feed getFeed(String feedId) {
    final Feed feed = feedService.findByPrimaryId(feedId);

    if (feed == null) {
      final String messge = "Unable to find feed record for feedId, " + feedId + ".";
      throw new FeedErrorException(messge);
    }

    return feed;
  }

  /**
   * Retrieves the feedFile object for the given feedfileId.
   *
   * @param feedFileId identifies the feedFile object to retrieve.
   * @return feedFile object.
   * @throws FeedErrorException if feedFile not found.
   */
  public FeedFile getFeedFile(Integer feedFileId) {
    final FeedFile feedFile = feedFileService.findByPrimaryId(feedFileId);

    if (feedFile == null) {
      final String message = "Unable to find feed file record for feedFileId, " + feedFileId + ".";
      throw new FeedErrorException(message);
    }

    return feedFile;
  }

  /**
   * Retrieves the checkpoint object for the given feed file.
   *
   * @param feedFile used to search for the checkpoint object.
   * @return null if not found.
   */
  public Checkpoint getCheckpoint(FeedFile feedFile) {
    return checkpointService.findByFeedFileId(feedFile.getFeedFileId());
  }

  /**
   * Retrieves a list of all feedFile objects in a processing state.
   *
   * @return List of processing feedFile objects or any empty set if none
   *         were found.
   */
  public List<FeedFile> findAllProcessingFeeds() {
    return feedFileService.findAllProcessingFeeds();
  }

  /**
   * Retrieves a list of feedFile objects in a processing state for the given feedFileId.
   *
   * @param feedId the feedId to filter the search for.
   * @return List of processing feedFile objects or any empty set if none
   *         were found.
   */
  public List<FeedFile> findAllProcessingFeedsForFeedId(String feedId) {
    return feedFileService.findAllProcessingFeedsForFeedId(feedId);
  }

  /**
   * Checks to see if there are any feed files currently in a processing state
   * for the given feed.
   *
   * @param feed the feed to check for.
   * @return true if number of processing feeds is > 0;
   */
  public Boolean anyFeedsInProcessingState(Feed feed) {
    final List<FeedFile> feedFiles = findAllProcessingFeedsForFeedId(feed.getFeedId());
    return feedFiles.size() > 0;
  }

  /**
   * Retrieves a list of feedFile objects in a failed state for the given feedFileId.
   *
   * @param feedId the feedId to filter the search for.
   * @return List of failed feedFile objects or any empty set if none
   *         were found.
   */
  public List<FeedFile> findAllFailedFeedsForFeedId(String feedId) {
    return feedFileService.findAllFailedFeedsForFeedId(feedId);
  }

  /**
   * Checks to see if there are any feed files currently in a failed state for
   * the given feed.
   *
   * @param feed the feed to check for.
   * @return true if number of failed feeds is > 0;
   */
  public Boolean anyFeedsInFailedState(Feed feed) {
    final List<FeedFile> feedFiles = findAllFailedFeedsForFeedId(feed.getFeedId());
    return feedFiles.size() > 0;
  }

  /**
   * Create a new FeedFile.
   * <p/>
   * <lu>
   * <li>	Get next unique feedFileId from unique Id server.
   * <li>	Set feed_fild_id to returned FeedFileId.
   * <li>	Set feed_file_date to current date.
   * <li>	Set feed_file_time to current time.
   * <li>	Set feed_file_id to foreign key reference in t_feed.
   * <li>	Set file_version from current file_version in t_feed.
   * <li>	Set feed_file_name.
   * <li>	Set feed_file_state = Processing.
   * <li>	Call createFeefJob.
   * </lu>
   * <p>
   * @param feed         contains the definition of the type of feedFile that will be created.
   * @param feedFileName the  feed file name.
   * @return reference to newly created FeedFile.
   */
  private FeedFile createFeedFile(Feed feed, String feedFileName) {
    final FeedFile feedFile = new FeedFile();

    feedFile.setFeed(feed);

    // The following two Calendar entry is to ensure object and
    // DB consistency when writing object data to DB and from DB
    // back to objects.  This may be MySQL dependent and may need
    // to be abstracted out.  MySQL only stores dates as yyyyMMdd
    // and nothing more, same for time as HHmmss.  Thus one has
    // to zero out the time value in date and zero out the date
    // value in time for this to occur.
    final Calendar calendarForTime = new GregorianCalendar();
    calendarForTime.set(Calendar.YEAR, 1970);
    calendarForTime.set(Calendar.MONTH, Calendar.JANUARY);
    calendarForTime.set(Calendar.DAY_OF_MONTH, 1);
    calendarForTime.set(Calendar.MILLISECOND, 0);
    feedFile.setFeedFileTime(new Time(calendarForTime.getTimeInMillis()));

    // for object and DB consistency, zero out the time in a date
    final Calendar calendarForDate = new GregorianCalendar();
    calendarForDate.set(Calendar.HOUR_OF_DAY, 0);
    calendarForDate.set(Calendar.MINUTE, 0);
    calendarForDate.set(Calendar.SECOND, 0);
    calendarForDate.set(Calendar.MILLISECOND, 0);
    feedFile.setFeedFileDate(new Date(calendarForDate.getTimeInMillis()));

    feedFile.setFeedFileName(feedFileName);
    feedFile.setFeedDocument(feed.getFeedDocument());
    feedFile.setFeedFileState(new FeedFileState(FEED_FILE_STATE.processing.getValue()));

    return feedFile;
  }

  /**
   * Updates feed file stats from the context into the feed file.
   *
   * @param feedFile the feed file to update.
   * @param context  references the engine context.
   */
  private void updateFeedFileStats(FeedFile feedFile, EngineContext context) {
    if (context != null) {
      feedFile.setRecordProcessed(context.getProcessedRowCount());
      feedFile.setRecordRejected(context.getRejectedRowCount());
    }
  }

  /**
   * Updates the feed job error information from the context into the feed job.
   *
   * @param feedJob the feedJob object to update.
   * @param context references the engine context.
   */
  private void updateFeedJobStats(FeedJob feedJob, EngineContext context) {
    if (context != null) {
      feedJob.setFailedRowNumber(context.getRejectedRowNumber());
      feedJob.setFailureCode(context.getErrorCode());
      feedJob.setFailureMessage(context.getErrorMessage());
    }
  }

  /**
   * This method completes the following tasks.
   * <p>
   * <ul>
   * <li>moves feedFile to a processing state.
   * <li>creates a feedJob in an active state.
   * </ul>
   * <p>
   * @param feedFile identifies the feedjob to create a job for.
   * @return the feedJob object
   */
  private FeedJob createFeedJobObject(FeedFile feedFile) {
    final FeedJob feedJob = new FeedJob();
    final Timestamp startTimeStamp = new Timestamp(System.currentTimeMillis());

    feedJob.setFeedFile(feedFile);
    startTimeStamp.setNanos(0);
    feedJob.setProcessingStart(startTimeStamp);

    feedJob.setFeedJobState(new FeedJobState(FEED_JOB_STATE.active.getValue()));
    feedFile.setFeedFileState(new FeedFileState(FEED_FILE_STATE.processing.getValue()));

    return feedJob;
  }

  /**
   * This method completes the following tasks.
   * <p>
   * <ul>
   * <li>moves feedFile to a rejected state.
   * <li>moves feedJob to a resolved state.
   * </ul>
   * <p>
   * @param feedJob identifies the feed job to fail.
   * @return true if it all steps completed.
   */
  public boolean moveToRejectedNoFileMove(FeedJob feedJob) {
    if (feedJob.getFeedJobState().getFeedJobStateId().equals(FEED_JOB_STATE.failed.getValue())) {
      final FeedFile feedFile = feedJob.getFeedFile();

      feedJob.setFeedJobState(new FeedJobState(FEED_JOB_STATE.resolved.getValue()));
      feedFile.setFeedFileState(new FeedFileState(FEED_FILE_STATE.rejected.getValue()));

      jobManagerService.updateFeedJobAndFeedFile(feedJob, feedFile);
      return true;
    }

    return false;
  }

  /**
   * This method completes the following tasks.
   * <p>
   * <ul>
   * <li>moves feedFile to a rejected state.
   * <li>moves feedJob to a resolved state.
   * <li>moves physical feed file from the failed to rejected directory.
   * </ul>
   * <p>
   * @param feedJob identifies the feed job to fail.
   * @return true if it all steps completed.
   */
  public boolean moveToRejected(FeedJob feedJob) {
    // This statement should succeed before changing the state of the feedFile and feedJob.
    moveFeedFile(feedJob, FEED_DIR.failed.getValue(), FEED_DIR.rejected.getValue());
    return moveToRejectedNoFileMove(feedJob);
  }

  /**
   * This method completes the following tasks.
   * <p>
   * <ul>
   * <li>moves feedFile to a retry state.
   * <li>moves feedJob to a resolved state.
   * <li>moves physical feed file from the failed to incoming directory.
   * </ul>
   * <p>
   * @param feedJob identifies the feed job to fail.
   * @return true if it all steps completed.
   */
  public boolean moveToRetry(FeedJob feedJob) {
    // This statement should succeed before changing the state of the feedFile and feedJob.
    moveFeedFile(feedJob, FEED_DIR.failed.getValue(), FEED_DIR.incoming.getValue());

    if (feedJob.getFeedJobState().getFeedJobStateId().equals(FEED_JOB_STATE.failed.getValue())) {
      final FeedFile feedFile = feedJob.getFeedFile();

      feedJob.setFeedJobState(new FeedJobState(FEED_JOB_STATE.resolved.getValue()));
      feedFile.setFeedFileState(new FeedFileState(FEED_FILE_STATE.retry.getValue()));

      jobManagerService.updateFeedJobAndFeedFile(feedJob, feedFile);
      return true;
    }
    return false;
  }

  /**
   * This method completes the following tasks.
   * <p>
   * <ul>
   * <li>moves feedFile to a completed state.
   * <li>moves feedJob to a completed state.
   * <li>moves physical feed file from the workarea to archive directory.
   * </ul>
   * <p/>
   * This method will not update feed phase stats because stats are managed by
   * the engine context. Use method moveToCompleted(FeedJob, EngineContext)
   * instead.
   *
   * @param feedJob identifies the feed job to fail.
   * @return true if it all steps completed.
   */
  public boolean moveToCompleted(FeedJob feedJob) {
    return moveToCompleted(feedJob, null);
  }

  /**
   * This method completes the following tasks.
   * <p>
   * <ul>
   * <li>moves feedFile to a completed state.
   * <li>moves feedJob to a completed state.
   * <li>moves physical feed file from the workarea to archive directory.
   * </ul>
   * <p>
   * @param feedJob identifies the feed job to fail.
   * @param context references the engine context.
   * @return true if it all steps completed.
   */
  public boolean moveToCompleted(FeedJob feedJob, EngineContext context) {
    if (feedJob.getFeedJobState().getFeedJobStateId().equals(FEED_JOB_STATE.active.getValue())) {
      final FeedFile feedFile = feedJob.getFeedFile();

      updateFeedFileStats(feedFile, context);
      feedJob.setFeedJobState(new FeedJobState(FEED_JOB_STATE.completed.getValue()));
      feedFile.setFeedFileState(new FeedFileState(FEED_FILE_STATE.completed.getValue()));

      jobManagerService.updateFeedJobAndFeedFile(feedJob, feedFile);
      moveFeedFile(feedJob, FEED_DIR.workarea.getValue(), FEED_DIR.archive.getValue());

      return true;
    }
    return false;
  }

  /**
   * This method completes the following tasks.
   * <p>
   * <ul>
   * <li>moves feedFile to a failed state.
   * <li>moves feedJob to a failed state.
   * <li>moves physical feed file from the workarea to failed directory.
   * </ul>
   * <p>
   * @param feedJob identifies the feed job to fail.
   * @param context references the engine context.
   * @return true if it all steps completed.
   */
  public boolean moveToFailed(FeedJob feedJob, EngineContext context) {
    if (feedJob.getFeedJobState().getFeedJobStateId().equals(FEED_JOB_STATE.active.getValue())) {
      final FeedFile feedFile = feedJob.getFeedFile();

      // Commandline utilities could send an null context.
      if ((feedJob.getFailureMessage() == null) && (context != null)) {
        feedJob.setFailureMessage(context.getErrorMessage());
      }

      updateFeedFileStats(feedFile, context);
      updateFeedJobStats(feedJob, context);

      feedJob.setFeedJobState(new FeedJobState(FEED_JOB_STATE.failed.getValue()));
      feedFile.setFeedFileState(new FeedFileState(FEED_FILE_STATE.failed.getValue()));

      jobManagerService.updateFeedJobAndFeedFile(feedJob, feedFile);
      moveFeedFile(feedJob, FEED_DIR.workarea.getValue(), FEED_DIR.failed.getValue());

      return true;
    }
    return false;
  }

  /**
   * This method completes the following tasks.
   * <p>
   * <ul>
   * <li>moves feedFile to a completed state.
   * <li>moves feedJob to a completed state.
   * <li>moves physical feed file from the workarea to archive directory.
   * </ul>
   * <p/>
   * This method will not update feed phase stats because stats are managed by
   * the engine context. Use method moveToFailed(FeedJob, EngineContext)
   * instead.
   *
   * @param feedJob identifies the feed job to fail.
   * @return true if it all steps completed.
   */
  public boolean moveToFailed(FeedJob feedJob) {
    return moveToFailed(feedJob, null);
  }

  /**
   * Retrieves the most recent FeedJob for the given feedFileId.
   *
   * @param feedFileId the id of the feedFile to reference.
   * @return the most recent feedJob instance variable.
   */
  public FeedJob getMostRecentFeedJob(Integer feedFileId) {
    final FeedFile feedFile = getFeedFile(feedFileId);
    final List<FeedJob> feedJobs = feedFile.getFeedJobs();

    return feedJobs.get(feedJobs.size() - 1);
  }

  /**
   * This method can only restart a feed file if the following conditions
   * are true:
   * <p>
   * <ul>
   * <li>feedFile.state must be in a processing state.
   * <li>feed.restartAtCheckpoint set to true.
   * <li>feedFile.checkpoint must not be null.
   * </ul>
   * <p>
   * If the above conditions are true, then the feed file is moved to a failed
   * state and then to a retry state.
   * <p/>
   * If the above conditions are not true, but the feedFile.state is in
   * a processing state, then the feed file is moved to a failed state.
   * <p/>
   * If the above conditions are not true, but the feedFile.state is not
   * in a processing state, then no action is taken.
   *
   * @param feedFileId the feed file to restart.
   * @return true and move to retry state else false and moved to a failed state if
   *         feedFile.state is in a processing state.
   */
  public Boolean restartFeedFile(Integer feedFileId) {
    final FeedFile feedFile = getFeedFile(feedFileId);
    final String feedFileState = feedFile.getFeedFileState().getFeedFileStateId();

    if (feedFileState.equals(FEED_FILE_STATE.processing.getValue())) {
      final Feed feed = feedFile.getFeed();
      final Checkpoint checkpoint = checkpointService.findByFeedFileId(feedFileId);

      if ((checkpoint != null) && (feed.getRestartAtCheckpoint())) {
        final FeedJob feedJob = getMostRecentFeedJob(feedFileId);

        moveToFailed(feedJob);
        moveToRetry(feedJob);
        return true;
      } else {
        final FeedJob feedJob = getMostRecentFeedJob(feedFileId);

        moveToFailed(feedJob);
        return false;
      }
    }
    return false;
  }

  /**
   * Checks to see if most recent FeedJob is in a 'RESOLVED' state.
   *
   * @param feedFile contains the feedFileId used to retrieve the most
   *                 recent feedJob.
   * @throws FeedErrorException if not in resolved state.
   */
  public void checkLastFeedJobIsResolved(FeedFile feedFile) {
    final FeedJob feedJob = getMostRecentFeedJob(feedFile.getFeedFileId());
    final String feedJobState = feedJob.getFeedJobState().getFeedJobStateId();

    if (!feedJobState.equals(FEED_JOB_STATE.resolved.getValue())) {
      final String message =
        "\nThe most recent feed job is not in a 'Resolved' state. " +
          "\nIt's current state is '" + feedJob.getFeedJobState() + ".";
      throw new FeedErrorException(message);
    }
  }

  /**
   * Creates a new FeedJob and a new FeedFile if a FeedJob does not already exists.<p>
   * The FeedFile is checked for existance by search for the unique feedFileName.<p>
   * If the FeedFile already exists, then it must be in a retry state.<p>
   * The new FeedJob will reference the FeedFile.
   *
   * @param feedId  uniquely identifies the specific feed to process
   * @param context references the engine context
   * @return new FeedJob which references the new FeedFile.
   */
  public FeedJob createFeedJob(String feedId, EngineContext context) {
    final String feedFileName = context.getFeedFileName();
    final Feed feed = getFeed(feedId);

    Boolean createFeedFile = false;
    FeedFile feedFile = checkIfFeedFileExistsInRetry(feedFileName);
    if (feedFile == null) {
      createFeedFile = true;
      feedFile = createFeedFile(feed, feedFileName);
    } else {
      checkLastFeedJobIsResolved(feedFile);
    }

    final FeedJob feedJob = createFeedJobObject(feedFile);

    moveFeedFile(feedJob, FEED_DIR.incoming.getValue(), FEED_DIR.workarea.getValue());

    if (createFeedFile) {
      jobManagerService.createFeedFileAndFeedJob(feedFile, feedJob);
      initPhaseStats(feedFile.getFeedFileId(), context);
    } else {
      jobManagerService.createFeedJob(feedFile, feedJob);
      reInitPhaseStats(feedFile.getFeedFileId(), context);
    }

    return feedJob;
  }

  /**
   * Checks if a given feed file exists.
   *
   * @param feedFileName the name of the feed file to check for.
   * @return the feedFile object if the feedFile is found.
   * @throws FeedErrorException if exists but is not in a retry state.
   */
  public FeedFile checkIfFeedFileExistsInRetry(String feedFileName) {
    final FeedFile feedFile = feedFileService.findByFeedFileName(feedFileName);

    if (feedFile != null) {
      final String state = feedFile.getFeedFileState().getFeedFileStateId();
      if (!state.equals(FEED_FILE_STATE.retry.getValue())) {
        final String message =
          "The feed file, " + feedFileName + ", already exists and is currently in a " + feedFile.getFeedFileState() + " state." +
            " The feed state must 'retry'.";
        throw new FeedErrorException(message);
      }
    }

    return feedFile;
  }

  /**
   * Checks wether the given feed should track phase statistics.  It checks
   * to see if the feed is associated to a group.  If it is, it uses the
   * group collectPhaseStats value to override the feed's.
   *
   * @param feed contains configuration information about the given feed.
   * @return true if phase statistics should be collected.
   */
  public Boolean checkCollectPhasStats(Feed feed) {
    final FeedGroup feedGroup = feed.getFeedGroup();
    return feedGroup != null ? feedGroup.getCollectPhaseStats() : feed.getCollectPhaseStats();
  }

  /**
   * Reinitializes and persists phase stat records for the given feedFileId.
   * This should only be called when rerunning a failed feed and after a restart
   * or retry has been executed.
   *
   * @param feedFileId identfies the feed file the phase stats are related to.
   * @param context    references the engine context.
   */
  public void reInitPhaseStats(Integer feedFileId, EngineContext context) {
    final Feed feed = getFeedFile(feedFileId).getFeed();

    if (!checkCollectPhasStats(feed)) {
      return;
    }

    final List<FeedPhaseStats> phaseStatsList = feedPhaseStatsService.findByFeedFileId(feedFileId);
    final Map<String, FeedPhaseStats> phaseStatsMap = new HashMap<String, FeedPhaseStats>();

    for (FeedPhaseStats stats : phaseStatsList) {
      stats.setStartTime(0L);
      stats.setTotalTimeInMs(0L);
      stats.setAvgProcessingTime(0D);
      stats.setIterationCount(0);
      phaseStatsMap.put(stats.getPhaseId(), stats);
    }

    feedPhaseStatsService.update(phaseStatsList);

    context.setPhaseStatsMap(phaseStatsMap);
  }

  /**
   * Creates and persists phase stat records for the given feedFileId.
   *
   * @param feedFileId identfies the feed file the phase stats are related to.
   * @param context    references the engine context.
   */
  public void initPhaseStats(Integer feedFileId, EngineContext context) {
    final Feed feed = getFeedFile(feedFileId).getFeed();

    if (!checkCollectPhasStats(feed)) {
      return;
    }

    final List<FeedPhaseStats> phaseStatsList = new ArrayList<FeedPhaseStats>();

    addToPhaseList(feedFileId, context.getPreEventPhases(), phaseStatsList);
    addToPhaseList(feedFileId, context.getPostEventPhases(), phaseStatsList);
    addToPhaseList(feedFileId, context.getBatchEventPhases(), phaseStatsList);

    feedPhaseStatsService.insert(phaseStatsList);

    final Map<String, FeedPhaseStats> phaseStatsMap = new HashMap<String, FeedPhaseStats>();

    for (FeedPhaseStats stats : phaseStatsList) {
      phaseStatsMap.put(stats.getPhaseId(), stats);
    }

    context.setPhaseStatsMap(phaseStatsMap);
  }

  /**
   * Initalizes the phase stats for the given phase.
   *
   * @param phase   the phase to collect statistics for.
   * @param context reference to the engine context.
   */
  public void startPhaseStats(Phase phase, EngineContext context) {
    final Feed feed = context.getFeed();

    if (!checkCollectPhasStats(feed)) {
      return;
    }

    final FeedPhaseStats stats = context.getPhaseStatsMap().get(phase.getName());

    stats.setStartTime(System.currentTimeMillis());
  }

  /**
   * Calculates feed statistcs and the end of a phase.
   *
   * @param phase   the phase to collect statistics for.
   * @param context reference to the engine context.
   */
  public void endPhaseStats(Phase phase, EngineContext context) {
    final Feed feed = context.getFeed();

    if (!checkCollectPhasStats(feed)) {
      return;
    }

    final FeedPhaseStats stats = context.getPhaseStatsMap().get(phase.getName());

    final long startTime = stats.getStartTime();
    final long endTime = System.currentTimeMillis();
    final long totalTime = (stats.getTotalTimeInMs() + (endTime - startTime));
    final int count = stats.getIterationCount() == null ? 1 : stats.getIterationCount() + 1;
    final double avgTime = ((double) totalTime / (double) count) / 1000;

    stats.setIterationCount(count);
    stats.setTotalTimeInMs(totalTime);
    stats.setAvgProcessingTime(avgTime);
  }

  /**
   * Updates the current phase statistics and associated to the current feed file
   * to the t_feed_stats.
   *
   * @param context reference to the engine context
   */
  public void savePhaseStats(EngineContext context) {
    final Feed feed = context.getFeed();

    if (!checkCollectPhasStats(feed)) {
      return;
    }

    final Map<String, FeedPhaseStats> map = context.getPhaseStatsMap();
    final List<FeedPhaseStats> list = new ArrayList<FeedPhaseStats>();

    for (FeedPhaseStats stats : map.values()) {
      list.add(stats);
    }

    feedPhaseStatsService.update(list);
  }

  private void addToPhaseList(Integer feedFileId, List<Phase> source, List<FeedPhaseStats> phaseStatsList) {
    if (source != null) {
      for (Phase phase : source) {
        final FeedPhaseStats stats = new FeedPhaseStats(phase.getName(), feedFileId);
        phaseStatsList.add(stats);
      }
    }
  }
}
