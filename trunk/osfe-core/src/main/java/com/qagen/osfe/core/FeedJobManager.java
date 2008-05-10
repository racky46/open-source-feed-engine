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

public class FeedJobManager {
  final private FeedJobManagerService jobManagerService;
  final private FeedService feedService;
  final private FeedFileService feedFileService;
  final private CheckpointService checkpointService;
  final private FeedPhaseStatsService feedPhaseStatsService;

  public FeedJobManager() {
    feedService = (FeedService) DataAccessContext.getBean(FeedService.SERVICE_ID);
    feedFileService = (FeedFileService) DataAccessContext.getBean(FeedFileService.SERVICE_ID);
    jobManagerService = (FeedJobManagerService) DataAccessContext.getBean(FeedJobManagerService.SERVICE_ID);
    checkpointService = (CheckpointService) DataAccessContext.getBean(CheckpointService.SERVICE_ID);
    feedPhaseStatsService = (FeedPhaseStatsService) DataAccessContext.getBean(FeedPhaseStatsService.SERVICE_ID);
  }

  private String getHomeDirectory(Feed feed) {
    return DirectoryHelper.getHomeDirectory() + feed.getFeedDirectory();
  }

  private void moveFeedFile(FeedJob feedJob, String sourceDir, String destDir) {
    final FeedFile feedFile = feedJob.getFeedFile();
    final String fileName = feedFile.getFeedFileName();
    final String homeDir = getHomeDirectory(feedFile.getFeed());

    FileMoveHelper.moveFromDirToDir(homeDir, fileName, fileName, sourceDir, destDir);
  }

  public Feed getFeed(String feedId) {
    final Feed feed = feedService.findByPrimaryId(feedId);

    if (feed == null) {
      final String messge = "Unable to find feed record for feedId, " + feedId + ".";
      throw new FeedErrorException(messge);
    }

    return feed;
  }

  public FeedFile getFeedFile(Integer feedFileId) {
    final FeedFile feedFile = feedFileService.findByPrimaryId(feedFileId);

    if (feedFile == null) {
      final String message = "Unable to find feed file record for feedFileId, " + feedFileId + ".";
      throw new FeedErrorException(message);
    }

    return feedFile;
  }

  public List<FeedFile> findAllProcessingFeeds() {
    return feedFileService.findAllProcessingFeeds();
  }

  public List<FeedFile> findAllProcessingFeedsForFeedId(String feedId) {
    return feedFileService.findAllProcessingFeedsForFeedId(feedId);
  }

  public Boolean anyFeedsInProcessingState(Feed feed) {
    final List<FeedFile> feedFiles = findAllProcessingFeedsForFeedId(feed.getFeedId());
    return feedFiles.size() > 0;
  }

  public List<FeedFile> findAllFailedFeedsForFeedId(String feedId) {
    return feedFileService.findAllFailedFeedsForFeedId(feedId);
  }

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
   *
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

  private void updateFeedFileStats(FeedFile feedFile, EngineContext context) {
    if (context != null) {
      feedFile.setRecordProcessed(context.getProcessedRowCount());
      feedFile.setRecordRejected(context.getRejectedRowCount());
    }
  }

  private void updateFeedJobStats(FeedJob feedJob, EngineContext context) {
    if (context != null) {
      feedJob.setFailedRowNumber(context.getRejectedRowNumber());
      feedJob.setFailureCode(context.getErrorCode());
      feedJob.setFailureMessage(context.getErrorMessage());
    }
  }

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

  public boolean moveToRejected(FeedJob feedJob) {
    // This statement should succeed before changing the state of the feedFile and feedJob.
    moveFeedFile(feedJob, FEED_DIR.failed.getValue(), FEED_DIR.rejected.getValue());
    return moveToRejectedNoFileMove(feedJob);
  }

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

  public boolean moveToCompleted(FeedJob feedJob) {
    return moveToCompleted(feedJob, null);
  }

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

  public boolean moveToFailed(FeedJob feedJob) {
    return moveToFailed(feedJob, null);
  }

  public boolean moveToFailed(FeedJob feedJob, EngineContext context) {
    if (feedJob.getFeedJobState().getFeedJobStateId().equals(FEED_JOB_STATE.active.getValue())) {
      final FeedFile feedFile = feedJob.getFeedFile();

      if (feedJob.getFailureMessage() == null) {
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
   * <ul>
   * <li>feedFile.state must be in a processing state.
   * <li>feed.restartAtCheckpoint set to true.
   * <li>feedFile.checkpoint must not be null.
   * </ul>
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
   * If it is not, an IllegalStateTransitionException is thrown.
   *
   * @param feedFile contains the feedFileId used to retrieve the most
   *                 recent feedJob.
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
    FeedFile feedFile = checkIfFeedFileExists(feedFileName);
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
    }

    return feedJob;
  }


  public FeedFile checkIfFeedFileExists(String feedFileName) {
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

  public void initPhaseStats(Integer feedFileId, EngineContext context) {
    final Feed feed = getFeedFile(feedFileId).getFeed();

    if (!feed.getCollectPhaseStats()) {
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

  public void startPhaseStats(Phase phase, EngineContext context) {
    final FeedPhaseStats stats = context.getPhaseStatsMap().get(phase.getName());
    stats.setStartTime(System.currentTimeMillis());
  }

  public void endPhaseStats(Phase phase, EngineContext context) {
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

  public void savePhaseStats(EngineContext context) {
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
