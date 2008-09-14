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
package com.qagen.osfe.core.phases.outbound.delimited;

import com.qagen.osfe.core.BufferedFeedFileWriter;
import com.qagen.osfe.core.Phase;
import com.qagen.osfe.core.loaders.FileNameFormatLoader;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: htaylor
 * Date: Aug 6, 2008
 * Time: 4:15:16 PM
 */
public class CreateBufferedFeedFilePhase extends Phase {

  /**
   * Gets the filename and creates the filewriter before any phase's
   * initializ() methods is called.  Some phases may need access to file
   * fileWriter during their initialization phase.  The complete path for
   * the file name is automatically added before the file name by the
   * fileWriter.
   * <p/>
   * <ul><li>Injection - required</li></ul>
   *
   * @param fileNameFormatLoader contains the components that make up the file name.
   */
  public void setFileNameFormatLoader(FileNameFormatLoader fileNameFormatLoader) {
    new BufferedFeedFileWriter(context, fileNameFormatLoader.getFileName());
  }

  /**
   * Creates the filewriter before any phase's initialize() methods is called.
   * Some phases may need access to file fileWriter during their initialization
   * phase. The complete path for the file name is automatically added before
   * the file name by the fileWriter.
   * <p/>
   * <ul><li>Injection - optional</li></ul>
   *
   * @param fileName the name of the file to create.
   */
  public void setFileName(String fileName) {
    new BufferedFeedFileWriter(context, fileName);
  }

  /**
   * Use this method to initialize references to other enging context objects and
   * not it's constructor.  Since a given phase may depend on other objects which
   * are loaded from the configuration document such as splitters, properties and
   * services, OSFE initializes phases in two passes.  The first pass instantiates
   * all phases by calling the phase constructor and passes it the engine context.
   * The second pass calls the initialization method.  It is during the second
   * pass that a phase will be able to reference any objects that have been loaded
   * from the feed configuration document.
   */
  public void initialize() {
  }


  public void execute() {
  }

  /**
   * The method should be used to close resources. Any core FifeCycleService
   * should make sure to call the shutdown() method in all phases as a final
   * processe of its life cycle.
   */
  public void shutdown() {
    try {
      context.getFeedFileWriter().close();
    } catch (IOException e) {
      // Do nothing.
    }
  }
}
