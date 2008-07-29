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
package com.qagen.osfe.engine;

import static com.qagen.osfe.common.FeedConstants.FEED_FILE_STATE;
import static com.qagen.osfe.common.FeedConstants.FEED_JOB_STATE;
import com.qagen.osfe.core.FeedJobManager;
import com.qagen.osfe.core.cmdlnUtils.RejectFeed;
import com.qagen.osfe.core.cmdlnUtils.RetryFeed;
import com.qagen.osfe.dataAccess.vo.FeedFile;
import com.qagen.osfe.dataAccess.vo.FeedJob;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Setting up test data.
 * <p/>
 * 1) Create an environment variable for OSFE_WORK
 * <p/>
 * example: OSFE_WORK=/home/yourName/osfe
 * <p/>
 * 2) Copy test data from testData module to you defined directory.
 */
public class FailedFeedTest extends FeedTestBase {

  private void runFeedEngine(String feedId, String feedFileName) {
    try {
      final FeedEngine engine = new FeedEngine(feedId, feedFileName);
      engine.execute();
    } catch (Exception e) {
      // do nothing.
    }
  }

  @Test
  public void failedFeedTest() {
    final String feedId = "acme_qagen_testd_request";
    final String feedFileName = "acme_qagen_testd_request_20080403134506.txt";
    final FeedJobManager jobManager = new FeedJobManager();

    cleanDataTables();

    // Run Failed Feed
    runFeedEngine(feedId, feedFileName);

    // Test for failed Feed.
    List<FeedFile> feedFiles = jobManager.findAllFailedFeedsForFeedId(feedId);
    Assert.assertTrue(feedFiles.size() == 1, "No Failed Feeds.");

    // Test feedFile is in a failed state.
    FeedFile feedFile = feedFiles.get(0);
    Assert.assertEquals(feedFile.getFeedFileState().getFeedFileStateId(), FEED_FILE_STATE.failed.getValue());

    // Test feedJob is in failed state.
    List<FeedJob> feedJobs = feedFile.getFeedJobs();
    FeedJob feedJob = feedJobs.get(0);
    Assert.assertEquals(feedJob.getFeedJobState().getFeedJobStateId(), FEED_JOB_STATE.failed.getValue());

    // Move feed to retry state.
    Integer feedFileId = feedFile.getFeedFileId();
    final RetryFeed retryFeed = new RetryFeed(feedFileId);
    retryFeed.execute();

    // Test feedFile is in a retry state.
    feedFile = jobManager.getFeedFile(feedFileId);
    Assert.assertEquals(feedFile.getFeedFileState().getFeedFileStateId(), FEED_FILE_STATE.retry.getValue());

    // Test feedJob is in a resolved state.
    feedJobs = feedFile.getFeedJobs();
    feedJob = feedJobs.get(0);
    Assert.assertEquals(feedJob.getFeedJobState().getFeedJobStateId(), FEED_JOB_STATE.resolved.getValue());

    // Run failed feed again.
    runFeedEngine(feedId, feedFileName);

    // Test for failed feed again.
    feedFiles = jobManager.findAllFailedFeedsForFeedId(feedId);
    Assert.assertTrue(feedFiles.size() == 1, "No Failed Feeds.");

    // Test feedFile is in a failed state.
    feedFile = feedFiles.get(0);
    Assert.assertEquals(feedFile.getFeedFileState().getFeedFileStateId(), FEED_FILE_STATE.failed.getValue());

    // Test feedJob is in failed state.
    feedJobs = feedFile.getFeedJobs();
    feedJob = feedJobs.get(1);
    Assert.assertEquals(feedJob.getFeedJobState().getFeedJobStateId(), FEED_JOB_STATE.failed.getValue());

    // Move feed to rejected state.
    final RejectFeed rejectFeed = new RejectFeed(feedFileId);
    rejectFeed.execute();

    // Test feedFile is in a rejected state.
    feedFile = jobManager.getFeedFile(feedFileId);
    Assert.assertEquals(feedFile.getFeedFileState().getFeedFileStateId(), FEED_FILE_STATE.rejected.getValue());

    // Test feedJob is in a resolved state.
    feedJobs = feedFile.getFeedJobs();
    feedJob = feedJobs.get(1);
    Assert.assertEquals(feedJob.getFeedJobState().getFeedJobStateId(), FEED_JOB_STATE.resolved.getValue());
  }
}
