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

import com.qagen.osfe.common.utils.Log;
import com.qagen.osfe.dataAccess.vo.Feed;
import com.qagen.osfe.dataAccess.vo.FeedFile;
import com.qagen.osfe.dataAccess.vo.FeedJob;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * The abstract FeedLifeCycleService class defines the contract and base behavior
 * for classes that manage the primary phases of the feed life cycle process.
 * The format complexity of a feed file will determine the behavior of a class that
 * extends FeedLifeCycleService.
 * <p/>
 * For example, the phase life cycle for parsing a
 * complex XML file using a SAX parser may be drastically different from the phase
 * life cycle for parsing a simple formatted feed file.
 */
public abstract class FeedLifeCycleService extends ExecutionService {
  protected enum SERVICES {
    exceptionService("exceptionService");

    private String value;

    SERVICES(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  protected FeedJobManager feedJobManager;

  private static Log logger = Log.getInstance(FeedLifeCycleService.class);

  /**
   * Constructor
   *
   * @param context references the engine context.
   */
  public FeedLifeCycleService(EngineContext context) {
    super(context);
    feedJobManager = new FeedJobManager();
  }

  /**
   * Logic in this method should perform the task of loading the feed file.
   */
  protected abstract void loadFeedFile();

  /**
   * Locic in this method should perform the task of instantiating a row
   * descritption loader apprpriate for the loading the specific type of
   * row data.
   */
  protected abstract void initRowLoader();

  /**
   * Logic in this method should perform the task of instantiate
   */
  protected abstract void initSplitters();

  /**
   * Traverses the list of phases and calls their shutdown() method.
   *
   * @param phases list of phase objects to shutdown.
   */
  protected void shutdownPhases(List<Phase> phases) {
    for (Integer index = phases.size() - 1; index > 0; index--) {
      phases.get(index).shutdown();
    }
  }

  /**
   * Retrieve the exception handler service defined in the configuration file
   * and now loaded in the engine context.  Once retrieve, run the exception
   * handler service.
   *
   * @param exception the exception to handle.
   */
  private void doCustomExceptionHandler(Exception exception) {
    final ExceptionHandler exceptionHandler = (ExceptionHandler) context.getServiceMap(SERVICES.exceptionService.getValue());

    if (exceptionHandler != null) {
      exceptionHandler.handleException(exception);
    }
  }

  /**
   * This method performs the common house keeping tasks on a failed feed job.
   * A log massage is created providing a detiled explanation about the
   * failed feed job and an explanation as to why the feed job was failed.
   * It also performs the task of moving the feed job to a failed state.
   *
   * @param feedJob   reference to the failed feedJob.
   * @param exception contains an explanation of why the feed failed.
   */
  protected void handleFailure(FeedJob feedJob, Exception exception) {
    doCustomExceptionHandler(exception);

    final FeedFile feedFile = feedJob.getFeedFile();
    final Feed feed = feedFile.getFeed();
    final Integer rejectedRowNumber = context.getRejectedRowNumber();
    final StringBuilder builder = new StringBuilder();

    builder.append("*** Failure: feedJobId=").append(feedJob.getFeedJobId());
    builder.append(", feedFileId=").append(feedFile.getFeedFileId());
    builder.append(", feedId=").append(feed.getFeedId());
    builder.append(", feedFileName=").append(feedFile.getFeedFileName());
    builder.append("\n*** Likely row that caused exception: ").append(rejectedRowNumber);
    builder.append("\n*** Explanation: ").append(exception.getMessage());

    logger.error(builder.toString());

    if (context.getErrorMessage() == null) {
      context.setErrorMessage(exception.getMessage());
    }

    feedJobManager.moveToFailed(feedJob, context);
  }

}
