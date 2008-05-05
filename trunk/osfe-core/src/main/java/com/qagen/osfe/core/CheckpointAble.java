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

import com.qagen.osfe.dataAccess.vo.Checkpoint;

/**
 * Author: Hycel Taylor
 * <p/>
 * This interface should be implmented by detail splitter classes that
 * allow checkpointing.
 */
public interface CheckpointAble {

  /**
   * Performs the task of moving the file pointer to the last checkpoint
   * position in the file.
   *
   * @param checkpoint contains the information about the position in
   *                   the file to move to.
   */
  public void moveToCheckPoint(Checkpoint checkpoint);

}
