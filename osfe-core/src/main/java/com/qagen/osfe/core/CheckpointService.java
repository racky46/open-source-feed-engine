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

import com.qagen.osfe.core.utils.CheckpointHelper;
import com.qagen.osfe.dataAccess.vo.FeedCheckpoint;

/**
 * Author: Hycel Taylor
 * <p/>
 * The CheckpointService is absrtact and must be extended by
 * classes that need to perform some custom actions prior to
 * a checkpoint recovery and after a checkpoint recovery.
 * <p/>
 * Classes like the FeedLifeCycleService will look for a
 * checkpoint service in the engine context and call its
 * beforeCheckpoint() and afterCheckpoint methods when
 * performing a checkpoint recovery.
 */
public abstract class CheckpointService extends EngineService {
  protected FeedCheckpoint checkpoint;

  /**
   * Constructor
   */
  protected CheckpointService() {
  }


  /**
   * This method ensures that the database is hit only once to attrain a
   * reference to the feed check point row.  It also ensures that is a
   * check point row does not exist, one is created.
   *
   * @return always returns a check row.
   */
  public FeedCheckpoint getCheckpoint() {
    if (checkpoint == null) {
      checkpoint = CheckpointHelper.getFeedCheckpoint(context);
    }

    return checkpoint;
  }

  /**
   * Perform some action before the checkpoint has stared.
   */
  public abstract void beforeCheckpoint();

  /**
   * Perform some action after the checkpoint has completed.
   */
  public abstract void afterCheckpoint();

}
