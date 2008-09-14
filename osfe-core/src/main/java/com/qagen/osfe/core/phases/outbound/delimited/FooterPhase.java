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
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.Phase;
import com.qagen.osfe.core.loaders.DelimitedFileDescLoader;
import com.qagen.osfe.core.utils.ComponentBuilder;
import com.qagen.osfe.core.utils.FeedFileWriterUtil;
import com.qagen.osfe.core.vo.Components;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * Author: Hycel Taylor
 */
public class FooterPhase extends Phase {
  private BufferedWriter bufferedWriter;
  private DelimitedFileDescLoader delimitedFileDescLoader;
  private String eolCharacter;

  /**
   * Sets the reference to the delimitedFileDescriptionLoader.
   * <p/>
   * <ul><li>Injection - required</li></ul>
   *
   * @param delimitedFileDescLoader - contains footer descriptions
   */
  public void setDelimitedFileDescLoader(DelimitedFileDescLoader delimitedFileDescLoader) {
    this.delimitedFileDescLoader = delimitedFileDescLoader;
  }

  public void initialize() {
    bufferedWriter = ((BufferedFeedFileWriter) context.getFeedFileWriter()).getBufferedWriter();
    eolCharacter = delimitedFileDescLoader.getEndOfLineValue();
  }

  public void execute() {
    try {
      final ComponentBuilder builder = new ComponentBuilder(context);
      final List<Components> components = delimitedFileDescLoader.getFooters();

      for (Components component : components) {
        bufferedWriter.write(builder.buildComponent(component.getProperties()));
        FeedFileWriterUtil.writeEndOfLine(bufferedWriter, eolCharacter);
        context.incrementCurrentRowIndex();
        context.incrementProcessedRowCount();
      }
    } catch (IOException e) {
      throw new FeedErrorException(e);
    }
  }

  public void shutdown() {
  }
}