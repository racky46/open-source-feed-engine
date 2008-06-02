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
package com.qagen.osfe.core.phases;

import com.qagen.osfe.core.EngineContext;
import com.qagen.osfe.core.Phase;
import com.qagen.osfe.dataAccess.context.DataAccessContext;
import com.qagen.osfe.dataAccess.service.FeedCheckpointService;
import com.qagen.osfe.dataAccess.vo.FeedCheckpoint;
import com.qagen.osfe.dataAccess.vo.FeedFile;

/**
 * Author: Hycel Taylor
 * <p/>
 * CheckPointPhase defines the basic actions for checkpointing a feed file.
 * Custom checkpoint classes should extend CheckPoint and override the
 * initialize() and execute() methods.  In a feed configuration file, a
 * checkpoint phase can be inserted anywere within a list of phase
 * definitions.  If necessary, a checkpoint phase can be inserted within a
 * list of phases definitions multiple times.  However, like all phases, each
 * checkpoint must be defined with a unique name.
 * <p>
 * It is important to understand that even though more than one checkpoint
 * phase can be defined within a set of phases, only one instance of the
 * checkpoint object will be stored in the engine context and thus shared
 * by all checkpoint phases.
 * <p/>
 * It is necessary to give each checkpoint, within a list of phases, a unique
 * name, so that during an engine restart the engine will be able to determine
 * which checkpoint to start from.
 * <p>
 * Example: In the example below, the CheckPointPhase class is defined twice within
 * the batchEventPhase definition.
 * <hr><blockquote><pre>
 * &lt;batchEventPhases&gt;
 *   &lt;phase name="beanPopulatorPhase" enable="true" className="com.acme.phases.BeanPopulatorPhase"/&gt;
 *   &lt;phase name="startTransactionPhase" enable="true" className="com.acme.phases.BeginTransactionPhase"/&gt;
 *   &lt;phase name="coursePhase" enable="true" className="com.acme.phases.CourseInsertPhase"/&gt;
 *   &lt;phase name="studentPhase" enable="true" className="com.acme.phases.StudentInsertPhase"/&gt;
 *   &lt;phase name="endTransactionPhase" enable="true" className="com.acme.phases.EndTransactionPhase"/&gt;
 *   &lt;phase name="checkPoint1" enable="true" className="com.qagen.osfe.core.phases.CheckPointPhase"/&gt;
 *   &lt;phase name="gradingPhase" enable="true" className="com.acme.phases.GradingPhase"/&gt;
 *   &lt;phase name="checkPoint2" enable="true" className="com.qagen.osfe.core.phases.CheckPointPhase"/&gt;
 * &lt;/batchEventPhases&gt;
 * </pre></blockquote>
 */
public class CheckpointPhase extends Phase {
  public static final String NO_PHASE_ID = "NO_PHASE_ID";
  private FeedCheckpointService service;

  /**
   * Constructor
   *
   * @param context refereces the engine context
   * @param name uniquely idetifies the phase
   */
  public CheckpointPhase(EngineContext context, String name) {
    super(context, name);
    service = (FeedCheckpointService) DataAccessContext.getBean(FeedCheckpointService.SERVICE_ID);
  }

  /**
   * Creat a checkpoint object for the current feedFile
   * if one does not already exist.
   */
  public void initialize() {
    FeedCheckpoint checkpoint = context.getCheckpoint();

    if (checkpoint == null) {
      final FeedFile feedFile = context.getFeedJob().getFeedFile();
      final Integer feedFileId = context.getFeedJob().getFeedFile().getFeedFileId();

      checkpoint = service.findByFeedFileId(feedFileId);

      if (checkpoint == null) {
        checkpoint = new FeedCheckpoint(NO_PHASE_ID, 0, feedFile);
        service.insert(checkpoint);
      }
    }
    
    context.setCheckpoint(checkpoint);
  }

  /**
   * Updates the checkpoint.
   */
  public void execute() {
    final FeedCheckpoint checkpoint = context.getCheckpoint();

    checkpoint.setPhaseId(context.getCurrentPhaseId());
    checkpoint.setCurrentFileIndex(context.getPreviousSplitterIndex());
    
    service.update(checkpoint);
  }

  /**
   * The method should be used to close resources. Any core FifeCycleService
   * should make sure to call the shutdown() method in all phases as a final
   * processe of its life cycle.
   */
  public void shutdown() {
    // No resources to close...
  }
}
