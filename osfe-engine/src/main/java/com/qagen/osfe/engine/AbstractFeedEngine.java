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

import static com.qagen.osfe.common.FeedConstants.*;
import com.qagen.osfe.common.utils.Log;
import com.qagen.osfe.core.*;
import com.qagen.osfe.core.loaders.ConfigLoaderFactory;
import com.qagen.osfe.core.loaders.PhaseConfigLoader;
import com.qagen.osfe.core.loaders.PropertiesConfigLoader;
import com.qagen.osfe.core.loaders.ServiceConfigLoader;
import com.qagen.osfe.core.utils.FeedFileHelper;
import com.qagen.osfe.core.vo.Clazz;
import com.qagen.osfe.core.vo.PhaseSetInfo;
import com.qagen.osfe.core.vo.Property;
import com.qagen.osfe.dataAccess.vo.Feed;
import com.qagen.osfe.dataAccess.vo.FeedFile;
import com.qagen.osfe.dataAccess.vo.FeedGroup;
import com.qagen.osfe.dataAccess.vo.FeedJob;
import org.dom4j.Document;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Hycel Taylor
 * <p/>
 * AbstractFeedEngine provides the base behavior and contract for all
 * concrete feed engines.  AbstractFeedEngine provides a common framework
 * and design patterns for three primary types of feed processing:
 * <ul>
 * <li>Data Retriever Engines - Engines that retrieve data from internal or
 * external sources (e.g., retrieving data via socket, FTP, Web Service).
 * Retrieved files may also need to be prepared for processing (e.g., data
 * may need to be unzipped, unencrypted, concatenated, etc).
 * <li>Inbound Data Processing Engines - A raw data that has been retrieved
 * and prepared for processing is parsed, passed through phases and
 * processed into meaningful data (e.g., filtered, calculated and sent to
 * persistent storage).
 * <li>Outbound Data Creation Engines - Data is pull from database tables or
 * files, processed and placed into a feed file in a specified format
 * (e.g., sending an enrollment response to complete the contract from a given
 * enrollment request feed).
 * </ul>
 * <p/>
 * AbstractFeedEngine will prepare a feed for processing by performing the
 * following initialization sequence:
 * <ol>
 * <li>initContext(feedId, feedFileName) - store the feedId and filename as
 * instance variables, place the filename in the context and instantiate the 
 * feedJobManager.
 * <li> initFeedRecord() - Find the Feed record for the given feedId. Verify
 * that the feedId is valid and check it's validation rules to verify that
 * the feed can be processed.
 * <li>initFullPathAndFeedFileName() - Define the complete location of the
 * feed file by concatenating the directory path defined in the Feed record
 * with the given feed file name.
 * <li>initConfigFile() - Load the feed configuration document defined in the
 * Feed record and place a reference to the document in the engine context.
 * <li>initConfigProperties() - Load any global properties defined within the
 * given feed file configuration document into the engine context.
 * <li>initServices() - Uses the servicesConfigLoader and reads from the
 * &lt;services&gt; element to load and instantiate services defined within 
 * the given configuration file.
 * <li>initPhases() - Uses the phaseConfigLoader and PhaseLoaderService to load
 * and instantiate phases defined in the given configuration file.
 * <li>initialize() - Allows a concrete implementation of the AbstractFeedEngine
 * to perform its own specific initialization operations.
 * <li>runPreFeedFilePhases() Execute the set of preEventPhases defined
 * within the given feed file configuration document.
 * <li>initFeedFileAndFeedJob() - Update or create a new FeedFile record and
 * create a new FeedJob record for managing the feed life cycle for the given
 * feed file.
 * </ol>
 */
public abstract class AbstractFeedEngine {
  private enum LOADERS {
    propertyLoader("propertyLoader"),
    phaseLoader("phaseLoader"),
    serviceLoader("serviceLoader");

    private String value;

    LOADERS(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  public enum SERVICES {
    mainService("mainService"),
    phaseService("phaseService");

    private String value;

    SERVICES(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  protected String feedId;
  protected String feedFileName;
  protected EngineContext context;
  protected Document feedDocument;
  protected Feed feed;
  protected FeedJob feedJob;

  protected FeedJobManager feedJobManager;

  private static Log logger = Log.getInstance(AbstractFeedEngine.class);

  /**
   * Constructor
   * <p/>
   * Normally used to process a feed for that has not been processed before.
   * This constructor can also be used to process a feed that has alread been
   * processed; however, it's easier to use the constructor which only requires
   * the feedFileId.
   *
   * @param feedId       the feedId is used to attian information about the feed for
   *                     processing.
   * @param feedFileName specifies the unique file to process.
   */
  public AbstractFeedEngine(String feedId, String feedFileName) {
    try {
      initializeEngine(feedId, feedFileName);
    } catch (Exception e) {
      handleError(feedId, feedFileName, e);
    }
  }

  /**
   * Constructor
   * <p/>
   * This constructor should be used to rerun a feedFile that is in a retry state.
   *
   * @param feedFileId identifies a file that have been previously processed.
   */
  public AbstractFeedEngine(Integer feedFileId) {
    try {
      final FeedFile feedFile = feedJobManager.getFeedFile(feedFileId);
      final String feedId = feedFile.getFeed().getFeedId();
      final String feedFileName = feedFile.getFeedFileName();

      initializeEngine(feedId, feedFileName);
    } catch (Exception e) {
      handleError(feedId, feedFileName, e);
    }
  }

  /**
   * Common error handler for the logging errors for the FeedErrorException is
   * thrown.
   *
   * @param feedId       identifies the feedId associated with the exception.
   * @param feedFileName identifies the name of the feed file associated with
   *                     the exception.
   * @param exception    contains the exception message.
   */
  protected void handleError(String feedId, String feedFileName, Exception exception) {
    final String message =
      "*** Unable to execute feed for feedId, " + feedId + ", feedFileName " + feedFileName + "." + "\n*** Exception: " + exception.getMessage();
    logger.error(message);
    throw new FeedErrorException(exception);
  }

  /**
   * Allows a concrete implementation of the AbstractFeedEngine to perform
   * its own specific initialization operations.
   */
  protected abstract void initialize();

  /**
   * A concrete implementation of the AbstractFeedEngine places it's core
   * lifecycle operations here.
   */
  public abstract void execute();

  /**
   * Perform the initialization sequence to prepare a feed for processing.
   *
   * @param feedId       identifies the feedId associated with the exception.
   * @param feedFileName identifies the name of the feed file associated with
   *                     the exception.
   */
  protected void initializeEngine(String feedId, String feedFileName) {
    initContext(feedId, feedFileName);
    initFeedRecord();
    initFullPathAndFeedFileName();
    initConfigFile();
    initConfigProperties();
    initServices();
    initPhases();
    initialize();
    runPreFeedFilePhases();
    initFeedFileAndFeedJob();
  }

  /**
   * Store the feedId and filename as instance variables, place the filename
   * in the context and instantiate the feedJobManager.
   *
   * @param feedId       the feedId is used to attian information about the feed for
   *                     processing.
   * @param feedFileName specifies the unique file to process.
   */
  protected void initContext(String feedId, String feedFileName) {
    this.feedId = feedId;
    this.feedFileName = feedFileName;

    context = new EngineContext();
    context.setFeedFileName(feedFileName);

    feedJobManager = new FeedJobManager();
  }

  /**
   * Find the Feed record for the given feedId. Verify that the feedId is valid
   * and check it's validation rules to verify that the feed can be processed.
   */
  protected void initFeedRecord() {
    feed = feedJobManager.getFeed(feedId);

    checkAllowConcurrentRunState(feed);
    checkAllowFailedRunState(feed);
    checkActivationDate(feed);
    checkTermainationDate(feed);

    context.setFeed(feed);

    FeedFileHelper.createFeedDirectories(feed);
  }

  /**
   * Checks whether the given feed can run if another feed of its type is
   * currently in a processing state.
   *
   * @param feed contains configuration information about the given feed.
   */
  private void checkAllowConcurrentRunState(Feed feed) {
    final FeedGroup feedGroup = feed.getFeedGroup();
    final Boolean allowConcurrentRuns = feedGroup != null ? feedGroup.getAllowConcurrentRuns() : feed.getAllowConcurrentRuns();

    if (!allowConcurrentRuns) {
      if (feedJobManager.anyFeedsInProcessingState(feed)) {
        final List<FeedFile> list = feedJobManager.findAllProcessingFeedsForFeedId(feedId);
        final FeedFile feedFile = list.get(0);
        final Integer feedFileId = feedFile.getFeedFileId();
        final String message =
          "Cannot run current feed for feed file, " + feedFileName + ", while feedFileId, " + feedFileId + " is in a processing state.";
        throw new FeedErrorException(message);
      }
    }
  }

  /**
   * Checks whether the given feed can run if another feed of its type is
   * currently in a failed state.
   *
   * @param feed contains configuration information about the given feed.
   */
  private void checkAllowFailedRunState(Feed feed) {
    final FeedGroup feedGroup = feed.getFeedGroup();
    final Boolean allowFailedStateRuns = feedGroup != null ? feedGroup.getAllowFailedStateRuns() : feed.getAllowFailedStateRuns();

    if (!allowFailedStateRuns) {
      if (feedJobManager.anyFeedsInFailedState(feed)) {
        final List<FeedFile> list = feedJobManager.findAllFailedFeedsForFeedId(feedId);
        final FeedFile feedFile = list.get(0);
        final Integer feedFileId = feedFile.getFeedFileId();
        final String message =
          "Cannot run feed for feed file, " + feedFileName + ", while feedFileId, " + feedFileId + " is in a failed state.";
        throw new FeedErrorException(message);
      }
    }
  }

  /**
   * Checks to see if the given feed to run is befor its activation date.
   * If it is before its activation date an exception will be thrown.
   *
   * @param feed contains configuration information about the given feed.
   */
  private void checkActivationDate(Feed feed) {
    if (!checkDate(feed.getActivationDate())) {
      final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
      final String message =
        "Feeds for feedId, " + feedId + ", cannot be activated for processing until " + sdf.format(feed.getActivationDate()) + ".";
      throw new FeedErrorException(message);
    }
  }

  /**
   * Checks to see if the given feed to run is after is given termination date.
   * If it is after its termination date an exception will be thrown.
   *
   * @param feed contains configuration information about the given feed.
   */
  private void checkTermainationDate(Feed feed) {
    if (!checkDate(feed.getActivationDate())) {
      final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
      final String message =
        "Feeds for feedId, " + feedId + ", cannot be activated for processing after " + sdf.format(feed.getActivationDate()) + ".";
      throw new FeedErrorException(message);
    }
  }

  /**
   * Used to compare a given timestamp against the system date.
   *
   * @param timestamp the timestam to check.
   * @return true if the system date is greater than or equal to the given date.
   */
  private boolean checkDate(Timestamp timestamp) {
    final GregorianCalendar calendar1 = new GregorianCalendar();
    calendar1.setTime(new Timestamp(System.currentTimeMillis()));

    final GregorianCalendar calendar2 = new GregorianCalendar();
    calendar2.setTime(timestamp);

    final Date date1 = calendar1.getTime();
    final Date date2 = calendar2.getTime();
    final long time1 = date1.getTime();
    final long time2 = date2.getTime();

    return time1 >= time2;
  }

  /**
   * Define the complete location of the feed file by concatenating the
   * directory path defined in the Feed record with the given feed file
   * name.
   */
  protected void initFullPathAndFeedFileName() {
    String directory = FEED_DIR.incoming.getValue();

    if (feed.getFeedDirection().getFeedDirectionId().equals(FEED_DIRECTION.outbound.getValue())) {
      directory = FEED_DIR.preprocess.getValue();
    }

    final String fullPath = FeedFileHelper.getFeedDirFullPath(context.getFeed()) + SLASH + directory + SLASH + feedFileName;
    context.setFullFeedFileName(fullPath);
  }

  /**
   * Load the feed configuration document defined in the Feed record and place
   * a reference to the document in the engine context.
   */
  protected void initConfigFile() {
    final String configFileName = context.getFeed().getFeedDocument() + SLASH + CONFIG_FILE;
    final ConfigLoaderFactory factory = new ConfigLoaderFactory(configFileName);

    feedDocument = factory.getDocument();
    context.setFeedDocument(feedDocument);
    context.setLoaderMap(factory.getLoaderMap());
  }

  /**
   * Load any global properties defined within the given feed file
   * configuration document into the engine context.
   */
  protected void initConfigProperties() {
    final Loader loader = context.getLoaderMap().get(LOADERS.propertyLoader.getValue());
    final List<Property> properties = ((PropertiesConfigLoader) loader).getPropertyList();

    for (Property property : properties) {
      context.put(property.getName(), property.getValue());
    }
  }

  /**
   * Execute the set of preEventPhases defined within the given
   * feed file configuration document.
   */
  protected void runPreFeedFilePhases() {
    final List<Phase> phases = context.getPreFeedFilePhases();

    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      phase.initialize();
      phase.execute();
    }
  }

  /**
   * Update or create a new FeedFile record and create a new FeedJob record
   * for managing the feed life cycle for the given feed file.
   */
  protected void initFeedFileAndFeedJob() {
    feedJob = feedJobManager.createFeedJob(feedId, context.getFeedFileName());
    context.setFeedJob(feedJob);
  }

  /**
   * Uses the phaseConfigLoader and PhaseLoaderService to load and instantiate
   * phases defined in the given configuration file.
   */
  protected void initPhases() {
    final Loader loader = context.getLoaderMap().get(LOADERS.phaseLoader.getValue());
    final PhaseSetInfo phaseSetInfo = ((PhaseConfigLoader) loader).getPhaseSetInfo();
    final PhaseHandler phaseHandler = (PhaseHandler) context.getServiceMap(SERVICES.phaseService.getValue());

    if (phaseHandler == null) {
      final String configFile = feed.getFeedDocument();
      final String message =
        "The <phaseService> element has not been define within the <services> element in the configuration file: " + configFile + ".";
      throw new FeedErrorException(message);
    }

    context.setBatchSize(phaseSetInfo.getBatchSize());

    List<Phase> phaseList = phaseHandler.loadPhases(phaseSetInfo.getPreFeedFilePhases());
    context.setPreFeedFilePhases(phaseList);

    phaseList = phaseHandler.loadPhases((phaseSetInfo.getPreEventPhaseList()));
    context.setPreEventPhases(phaseList);

    phaseList = phaseHandler.loadPhases((phaseSetInfo.getBatchPhaseList()));
    context.setBatchEventPhases(phaseList);

    phaseList = phaseHandler.loadPhases((phaseSetInfo.getPostEventPhaseList()));
    context.setPostEventPhases(phaseList);
  }

  /**
   * Uses the servicesConfigLoader and reads from the &lt;services&gt; element
   * to load and instantiate services defined within the given configuration
   * file.
   */
  protected void initServices() {
    final Loader loader = context.getLoaderMap().get(LOADERS.serviceLoader.getValue());
    final List<Clazz> serviceList = ((ServiceConfigLoader) loader).getList();
    final Map<String, EngineService> map = new HashMap<String, EngineService>();
    final List<EngineService> engineServices = new ArrayList<EngineService>();

    try {
      // First pass instantiates all services.
      for (Clazz clazzInfo : serviceList) {
        final Class clazz = Class.forName(clazzInfo.getClassName());
        final Class argTypes[] = new Class[]{EngineContext.class};
        final Constructor constructor = clazz.getConstructor(argTypes);
        final EngineService service = (EngineService) constructor.newInstance(context);

        map.put(clazzInfo.getName(), service);
        engineServices.add(service);
      }

      // Add to service map in context so services can see each duringn second pass.
      context.setServiceMap(map);

      // Second pass initializes all services.
      for (EngineService service : engineServices) {
        service.initialize();
      }

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

  /**
   * The main service is used to process a feed file through its defined phases.
   * The main service will also manage feed failures and shutdown the feed process.
   * The feed engine expects to find a main service defined within the feed file
   * configuration document.  If one is not found, the feed engine will throw
   * a FeedErrorException.  The main service that comes with the OSFE source code
   * is the StandardFeedLifeCycleService.
   */
  public void runMainService() {
    final ExecutionService service = (ExecutionService) context.getServiceMap(SERVICES.mainService.getValue());

    if (service == null) {
      final String message =
        "The service, \"mainService\", was not found in the services element of the configuration file.";
      throw new FeedErrorException(message);
    }

    service.execute();
    service.shutdown();
  }
}
