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
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.MainLifeCycleService;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class SaxFeedLifeCycleService extends MainLifeCycleService {

  private static Log logger = Log.getInstance(SaxFeedLifeCycleService.class);

  /**
   * Locic in this method should perform the task of instantiating a row
   * descritption loader apprpriate for the loading the specific type of
   * row data.
   */
  protected void initRowLoader() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  /**
   * Logic in this method should perform the task of instantiate
   */
  protected void initSplitters() {
    // Splitters are not applicable to sax parsing.
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
   * @throws com.qagen.osfe.core.FeedErrorException
   *
   */
  public void execute() throws FeedErrorException {
    //To change body of implemented methods use File | Settings | File Templates.
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
   * Use this method to reference engine context objects and other services
   * instead of the constructor.  Services may have inner dependencies with
   * each and are thus, initialized in two passes.  In the first pass, all
   * service constructors are instantiated.  In the second pass, the
   * initialization method on each service is called.
   */
  public void initialize() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  /**
   * Depending on the behavior of the service, it's shutdown method may be
   * called in order to perform house keeping tasks such as closing files
   * and other depended services.
   */
  public void shutdown() {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
