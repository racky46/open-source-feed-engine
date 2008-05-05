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
package com.qagen.osfe.core.services;

import com.qagen.osfe.common.utils.Log;
import com.qagen.osfe.core.*;
import com.qagen.osfe.core.loaders.SplitterConfigLoader;
import com.qagen.osfe.core.row.RowDescriptionLoader;
import com.qagen.osfe.dataAccess.vo.Checkpoint;
import com.qagen.osfe.dataAccess.vo.FeedJob;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Author: Hycel Taylor
 * <p/>
 * The StandardFeedLifeCycle is the most common feed life cycle service.  It
 * can process most feed file configurations that use that standard phase
 * configuration where data records are represented within the feed file as
 * single rows.
 * <p/>
 * The standard phase configuration requires the following elements be defined
 * within a given feed file configuration document.
 * <ul>
 * <li>preEventPhases
 * <li>batchEventPhases
 * <li>postEventPhases
 * </ul>
 * Only the batchEventPhase must have at least one phase element definition.
 * <p/>
 * The format of the feed file doesn't matter as long as the resulting data
 * record is represented as a single row of data.  This means it can process
 * feeds formated as, delimited, fixed, XML and custom binary.
 */
public class StandardFeedLifeCycle extends FeedLifeCycleService {
  private enum LOADERS {
    splitterLoader,
    rowDescriptionLoader
  }

  public static enum SPLITTERS {
    header,
    detail,
    footer
  }

  private static Log logger = Log.getInstance(StandardFeedLifeCycle.class);

  /**
   * Constructor
   *
   * @param context references the engine context.
   */
  public StandardFeedLifeCycle(EngineContext context) {
    super(context);
  }

  public String name() {
    return this.getClass().getSimpleName();
  }

  public void initialize() {
    loadFeedFile();
    initRowLoader();
    initSplitters();
  }

  public void execute() throws FeedErrorException {
    final FeedJob feedJob = context.getFeedJob();

    try {
      doPreEventPhases();
      doBatchEventPhases();
      doPostEventPhases();

      feedJobManager.moveToCompleted(feedJob, context);
    } catch (Exception e) {
      handleFailure(feedJob, e);
    } finally {
      shutdown();
    }
  }

  public void shutdown() {
    try {
      doPhaseShutdown();
      BufferedReader reader = context.getBufferedReader();
      reader.close();
    } catch (IOException e) {
      logger.warn("*** Unable to close feed file, " + context.getFeedFileName());
    }
  }

  protected void doPhaseShutdown() {
    shutdownPhases(context.getPostEventPhases());
    shutdownPhases(context.getBatchEventPhases());
    shutdownPhases(context.getPreEventPhases());
    shutdownPhases(context.getPreFeedFilePhases());
  }

  protected void loadFeedFile() {
    try {
      final String fullFeedFileName = context.getFullFeedFileName();
      final FileReader fileReader = new FileReader(fullFeedFileName);
      final BufferedReader bufferedReader = new BufferedReader(fileReader);

      context.setBufferedReader(bufferedReader);

    } catch (FileNotFoundException e) {
      throw new FeedErrorException(e);
    }
  }

  protected void initRowLoader() {
    final Loader loader = context.getLoaderMap().get(LOADERS.rowDescriptionLoader.name());
    final RowDescriptionLoader rowDescriptionLoader = ((RowDescriptionLoader) loader);

    context.setRowDescriptionLoader(rowDescriptionLoader);
  }

  protected void initSplitters() {
    final Loader loader = context.getLoaderMap().get(LOADERS.splitterLoader.name());
    final Map<String, String> map = ((SplitterConfigLoader) loader).getMap();

    Splitter splitter = loadSplitter(SPLITTERS.header.name(), map.get(SPLITTERS.header.name()));
    context.setHeaderSplitter(splitter);

    splitter = loadSplitter(SPLITTERS.detail.name(), map.get(SPLITTERS.detail.name()));
    context.setDetailSplitter(splitter);

    splitter = loadSplitter(SPLITTERS.footer.name(), map.get(SPLITTERS.footer.name()));
    context.setFooterSplitter(splitter);
  }

  private Splitter loadSplitter(String splitterName, String className) {
    try {
      final Class clazz = Class.forName(className);
      final Class argTypes[] = new Class[]{EngineContext.class, String.class};
      final Constructor constructor = clazz.getConstructor(argTypes);
      return (Splitter) constructor.newInstance(context, splitterName);

    } catch (ClassNotFoundException e) {
      throw new FeedErrorException(e);
    } catch (NoSuchMethodException e) {
      throw new FeedErrorException(e);
    } catch (InstantiationException e) {
      throw new FeedErrorException(e);
    } catch (IllegalAccessException e) {
      throw new FeedErrorException(e);
    } catch (InvocationTargetException e) {
      throw new FeedErrorException(e);
    }
  }

  private void doPreEventPhases() {
    final List<Phase> phases = context.getPreEventPhases();

    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      phase.initialize();
      phase.execute();
    }
  }

  private void doBatchEventPhases() {
    final Splitter splitter = context.getDetailSplitter();
    final List<Phase> phases = context.getBatchEventPhases();

    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      phase.initialize();
    }

    // Must be called after initialization and before anything else.
    splitter.prePhaseExecute();
    
    if (splitter instanceof CheckpointHandler) {
      final Checkpoint checkpoint = context.getCheckpoint();
      if (checkpoint != null) {
        
        ((CheckpointHandler) splitter).moveToCheckPoint(checkpoint);
      }
    }

    while (splitter.hasNextRow()) {
      for (Phase phase : phases) {
        context.setCurrentPhaseId(phase.getName());
        phase.execute();
      }
    }
  }

  private void doPostEventPhases() {
    final List<Phase> phases = context.getPostEventPhases();

    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      phase.initialize();
      phase.execute();
    }
  }
}
