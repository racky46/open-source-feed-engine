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

import com.qagen.osfe.core.row.RowDescriptionLoader;
import com.qagen.osfe.dataAccess.vo.FeedCheckpoint;
import com.qagen.osfe.dataAccess.vo.Feed;
import com.qagen.osfe.dataAccess.vo.FeedJob;
import com.qagen.osfe.dataAccess.vo.FeedPhaseStats;
import org.dom4j.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Hycel Taylor
 * <p/>
 * The EngineContext is a container class for common global resources that
 * are shared and referenced by many components of the feed engine.  The
 * EngineContext is automatically created by the AbstractFeedEngine which
 * provides the base behavior for all concrete feed engines.  Thus, each
 * instance of a given  concrete feed engine will always contain it's own
 * EngineContext.  Phases are one component of the feed engine that apply
 * heavy use of the EngineContext.  As each phase is traversed during a
 * given feed life cycle, they are constantly accessing and modifying
 * resources from the context.  Each phase then acts on data modified from
 * phases preceding it.  For example, a filter phase may disable other rows
 * that are of no importance to phases following it.
 */
public class EngineContext {
  private String feedFileName;
  private String fullFeedFileName;
  private String currentPhaseId;
  private Integer feedRowCount;
  private Integer previousSplitterIndex = 0;
  private Integer currentSplitterIndex = 0;
  private Integer currentRowIndex = 0;
  private Integer batchSize;

  private Integer processedRowCount = 0;
  private Integer rejectedRowCount = 0;
  private Integer rejectedRowNumber;
  private String errorCode;
  private String errorMessage;

  private Document feedDocument;
  private RowDescriptionLoader rowDescriptionLoader;
  private Object feedFileReader;
  private Feed feed;
  private FeedJob feedJob;
  private FeedCheckpoint checkpoint;

  private Splitter headerSplitter;
  private Splitter footerSplitter;
  private Splitter detailSplitter;

  protected List<Phase> preFeedFilePhases;
  protected List<Phase> preEventPhases;
  protected List<Phase> batchEventPhases;
  protected List<Phase> postEventPhases;
  protected List rows;

  private Map<String, Object> map;
  private Map<String, Loader> loaderMap;
  private Map<String, EngineService> serviceMap;
  private Map<String, FeedPhaseStats> phaseStatsMap;

  /**
   * Constructor
   */
  public EngineContext() {
    map = new HashMap<String, Object>();
  }

  /**
   * Retrieves an object from the internal context map.
   *
   * @param name id's the object to retrieve from the map.
   * @return null if object not found in map from the given name.
   */
  public Object get(String name) {
    return map.get(name);
  }

  /**
   * Places an object referce in the internal context map.
   *
   * @param name   uniquely identifes the object.
   * @param object the object to be referenced.
   */
  public void put(String name, Object object) {
    map.put(name, object);
  }

  /**
   * Retrives the map of Loader objects. A Loader object will parse and load data
   * from a given type of XML element into Java objects
   *
   * @return map of Loader objects.
   */
  public Map<String, Loader> getLoaderMap() {
    return loaderMap;
  }

  /**
   * Stores the reference to a given Loader map in the context.
   *
   * @param loaderMap the map of Loader objects to reference.
   */
  public void setLoaderMap(Map<String, Loader> loaderMap) {
    this.loaderMap = loaderMap;
  }

  /**
   * Retrives the batch size as specified within a given feed configuration file.
   *
   * @return null if batchSize was never initialized.
   */
  public Integer getBatchSize() {
    return batchSize;
  }

  /**
   * Sets the batchSize which is normally used by splitter objects in order
   * to determine how many rows of data to read into memory at one time for
   * the purposes of feed processing.
   *
   * @param batchSize defines the size of the batch.
   */
  public void setBatchSize(Integer batchSize) {
    this.batchSize = batchSize;
  }

  /**
   * Retrieves the name of the feed file currently being processed.
   *
   * @return name of the feed file currently being processed
   */
  public String getFeedFileName() {
    return feedFileName;
  }

  /**
   * Stores the name of the feed file currently being process in the context.
   *
   * @param feedFileName name of the physical feed file.
   */
  public void setFeedFileName(String feedFileName) {
    this.feedFileName = feedFileName;
  }

  /**
   * Retrieves the full directory path and feed file name originating from
   * the OSFE home directory.
   *
   * @return full directory path and fileName.
   */
  public String getFullFeedFileName() {
    return fullFeedFileName;
  }

  /**
   * Stores the full directory path and feed file name, originating from
   * the OSFE home directory, in the context.
   *
   * @param fullFeedFileName full directory path and feed file name.
   */
  public void setFullFeedFileName(String fullFeedFileName) {
    this.fullFeedFileName = fullFeedFileName;
  }

  /**
   * Retrieves the id of the current phase operating on data.
   *
   * @return the id of the current phase operating on data.
   */
  public String getCurrentPhaseId() {
    return currentPhaseId;
  }

  /**
   * Stores the id of the current phase acting on data in the context.
   *
   * @param currentPhaseId identifies the active phase.
   */
  public void setCurrentPhaseId(String currentPhaseId) {
    this.currentPhaseId = currentPhaseId;
  }

  /**
   * Retrieves the count of the numbers of rows contained within the feed file
   * currently being operated on.
   *
   * @return count of the numbers of rows contained within the feed file.
   */
  public Integer getFeedRowCount() {
    return feedRowCount;
  }

  /**
   * Sets the count of the numbers of rows contained within the feed file
   * currently being operated on.
   *
   * @param feedRowCount count of the numbers of rows contained within the feed
   *                     file.
   */
  public void setFeedRowCount(Integer feedRowCount) {
    this.feedRowCount = feedRowCount;
  }

  /**
   * Spitter classes may need to know the previous splitter index for further
   * splitter calculation. This method retrieves the previous splitter index.
   *
   * @return the index where that last split took place.
   */
  public Integer getPreviousSplitterIndex() {
    return previousSplitterIndex;
  }

  /**
   * Spitter classes may need to know the previous splitter index for further
   * splitter calculation. This method ets the previous splitter index.
   *
   * @param previousSplitterIndex the index where that last split took place.
   */
  public void setPreviousSplitterIndex(Integer previousSplitterIndex) {
    this.previousSplitterIndex = previousSplitterIndex;
  }

  /**
   * Spitter classes may need to know the current splitter index for further
   * splitter calculation. This method retrieves the current splitter index.
   *
   * @return the index where that current split took place.
   */
  public Integer getCurrentSplitterIndex() {
    return currentSplitterIndex;
  }

  /**
   * Spitter classes may need to know the current splitter index for further
   * splitter calculation. This method sets the current splitter index.
   *
   * @param currentSplitterIndex the index where the current split took place.
   */
  public void setCurrentSplitterIndex(Integer currentSplitterIndex) {
    this.currentSplitterIndex = currentSplitterIndex;
  }

  /**
   * Retrives a reference to the feed document.
   *
   * @return reference to the feed document.
   */
  public Document getFeedDocument() {
    return feedDocument;
  }

  /**
   * Stores a reference to the feed document in the context.
   *
   * @param feedDocument reference to the feed document.
   */
  public void setFeedDocument(Document feedDocument) {
    this.feedDocument = feedDocument;
  }

  /**
   * The row description is used by Splitter's to retrieve information that
   * describes the elements of a row.  This method retrieves the reference
   * to the RowDescriptionLoader object.
   *
   * @return reference to the RowDescriptionLoader object.
   */
  public RowDescriptionLoader getRowDescriptionLoader() {
    return rowDescriptionLoader;
  }

  /**
   * The row description is used by Splitter's to retrieve information that
   * describes the elements of a row.  This method sets the reference
   * to the RowDescriptionLoader object.
   *
   * @param rowDescriptionLoader reference to the RowDescriptionLoader object.
   */
  public void setRowDescriptionLoader(RowDescriptionLoader rowDescriptionLoader) {
    this.rowDescriptionLoader = rowDescriptionLoader;
  }

  /**
   * Splitters will use this object and cast it to the appriate type for
   * accessing data from the physical file.  For example, deilmited splitters
   * will uses a BufferedReader to access data from the physiacl feed file.
   *
   * @return reference to object used to access the physical feed file.
   */
  public Object getFeedFileReader() {
    return feedFileReader;
  }

  /**
   * Splitters will use this object and cast it to the appriate type for
   * accessing data from the physical file.  For example, deilmited splitters
   * will uses a BufferedReader to access data from the physiacl feed file.
   *
   * @param feedFileReader reference to the object used to access the physical
   *                       feed file.
   */
  public void setFeedFileReader(Object feedFileReader) {
    this.feedFileReader = feedFileReader;
  }

  /**
   * Retrieves the reference to the feed description object.
   *
   * @return reference to the feed description object.
   */
  public Feed getFeed() {
    return feed;
  }

  /**
   * Stores a reference to the feed description object in the context.
   *
   * @param feed reference to the feed description object.
   */
  public void setFeed(Feed feed) {
    this.feed = feed;
  }

  /**
   * Retrieves a reference to the current feedJob object.
   *
   * @return reference to the current feedJob object.
   */
  public FeedJob getFeedJob() {
    return feedJob;
  }

  /**
   * Stores a reference to the feed job object in the context.
   *
   * @param feedJob reference to the feed job object.
   */
  public void setFeedJob(FeedJob feedJob) {
    this.feedJob = feedJob;
  }

  /**
   * Retrieves a reference to the current checkpoint object.
   *
   * @return null if checpoint is not being used.
   */
  public FeedCheckpoint getCheckpoint() {
    return checkpoint;
  }

  /**
   * Stores a reference to the current checkpoint object.
   *
   * @param checkpoint reference to the current checkpoint object.
   */
  public void setCheckpoint(FeedCheckpoint checkpoint) {
    this.checkpoint = checkpoint;
  }

  /**
   * Retrieves a map of user defined objects. These are objects that are non
   * standard to OSFE and have no accessor or mutator methods.  Objects
   * stored here are usually accessed and manipulated by phases and services
   * created by engineers and defined within a feed configuration document.
   *
   * @return map user defined objects.
   */
  public Map<String, Object> getMap() {
    return map;
  }

  /**
   * Stores  a map of user defined objects. These are objects that are non
   * standard to OSFE and have no accessor or mutator methods.  Objects
   * stored here are usually accessed and manipulated by phases and services
   * created by engineers and defined within a feed configuration document.
   *
   * @param map user defined objects.
   */
  public void setMap(Map<String, Object> map) {
    this.map = map;
  }

  /**
   * Retrieves the splitter that operates on the header rows of a feed file.
   *
   * @return header splitter.
   */
  public Splitter getHeaderSplitter() {
    return headerSplitter;
  }

  /**
   * Stores a reference to the splitter that operates on the header rows
   * of a feed file.
   *
   * @param headerSplitter reference to the header splitter.
   */
  public void setHeaderSplitter(Splitter headerSplitter) {
    this.headerSplitter = headerSplitter;
  }

  /**
   * Retrieves the splitter that operates on the footer rows of a feed file.
   *
   * @return footer splitter.
   */
  public Splitter getFooterSplitter() {
    return footerSplitter;
  }

  /**
   * Stores a reference to the splitter that operates on the footer rows
   * of a feed file.
   *
   * @param footerSplitter reference to the footer splitter.
   */
  public void setFooterSplitter(Splitter footerSplitter) {
    this.footerSplitter = footerSplitter;
  }

  /**
   * Retrieves the splitter that operates on the detail rows of a feed file.
   *
   * @return detail splitter.
   */
  public Splitter getDetailSplitter() {
    return detailSplitter;
  }

  /**
   * Stores a reference to the splitter that operates on the detail rows
   * of a feed file.
   *
   * @param detailSplitter reference to the detail splitter.
   */
  public void setDetailSplitter(Splitter detailSplitter) {
    this.detailSplitter = detailSplitter;
  }

  /**
   * Retrieves the list of preFeedPhases.  PreFeedPhases are called before
   * the feedFile and feedJob are created in the feed engine life cycle.
   * For example, a preFeedPhase may be created to check that the feed
   * file name meets a specified naming convention.
   *
   * @return list of phases.
   */
  public List<Phase> getPreFeedFilePhases() {
    return preFeedFilePhases;
  }

  /**
   * Stores the list of preFeedPhases.  PreFeedPhases are called before
   * the feedFile and feedJob are created in the feed engine life cycle.
   * For example, a preFeedPhase may be created to check that the feed
   * file name meets a specified naming convention.
   *
   * @param preFeedFilePhases list of phases.
   */
  public void setPreFeedFilePhases(List<Phase> preFeedFilePhases) {
    this.preFeedFilePhases = preFeedFilePhases;
  }


  /**
   * Retrieves a list of preEventPhases. PreEventPhases are a called once
   * each before the batchEventPhases.  An example of preEventPhase is
   * a footer phase the calculates the number of rows in the given feed.
   *
   * @return list of phases.
   */
  public List<Phase> getPreEventPhases() {
    return preEventPhases;
  }

  /**
   * Stores a list of preEventPhase in the context.  PreEventPhases are a
   * called once each before the batchEventPhases.  An example of preEventPhase
   * is a footer phase the calculates the number of rows in the given feed.
   *
   * @param preEventPhases list of phases.
   */
  public void setPreEventPhases(List<Phase> preEventPhases) {
    this.preEventPhases = preEventPhases;
  }

  /**
   * Retrieves a list of batchEventPhases. BatchEventPhases are a called once
   * each during each batch iteration.  An example of batchEventPhase is
   * a phase calculates a students test score and inserts the row into a
   * database table.
   *
   * @return list of phases.
   */
  public List<Phase> getBatchEventPhases() {
    return batchEventPhases;
  }

  /**
   * Stores a list of batchEventPhases in the context. BatchEventPhases
   * are a called once each during each batch iteration.  An example of
   * batchEventPhase is a phase calculates a students test score and inserts
   * the row into a database table.
   *
   * @param batchEventPhases list of phases.
   */
  public void setBatchEventPhases(List<Phase> batchEventPhases) {
    this.batchEventPhases = batchEventPhases;
  }

  /**
   * Retrieves a list of postEventPhases. PostEventPhases are a called once
   * each after the batchEventPhases.  An example of postEventPhase is
   * a phase that calls a stored procedure to perform reconcilation on data
   * populated into the database from phases executed during the
   * batchEventPhase.
   *
   * @return list of phases.
   */
  public List<Phase> getPostEventPhases() {
    return postEventPhases;
  }

  /**
   * Stores a list of postEventPhases in the context. PostEventPhases are a
   * called once each after the batchEventPhases.  An example of postEventPhase
   * is a phase that calls a stored procedure to perform reconcilation on data
   * populated into the database from phases executed during the batchEventPhase.
   *
   * @param postEventPhases list of phases.
   */
  public void setPostEventPhases(List<Phase> postEventPhases) {
    this.postEventPhases = postEventPhases;
  }

  /**
   * Retrieves the reference to the current list of Row objects.
   *
   * @return current list of Row objects.
   */
  public List getRows() {
    return rows;
  }

  /**
   * Stores a reference to the current list of Row objects to the context.
   *
   * @param rows list of Row objects.
   */
  public void setRows(List rows) {
    this.rows = rows;
  }

  /**
   * Retrives a given service.  Services are plugable java classes that
   * allow OSFE to be flexiable and open to new functionality.
   *
   * @param name identifies the service to recieve.
   * @return null if the service is not found.
   */
  public EngineService getServiceMap(String name) {
    return serviceMap.get(name);
  }

  /**
   * Stores a map of services in the context.  Services are plugable java
   * classes that allow OSFE to be flexiable and open to new functionality.
   *
   * @param serviceMap map of service objects.
   */
  public void setServiceMap(Map<String, EngineService> serviceMap) {
    this.serviceMap = serviceMap;
  }

  /**
   * Retrieves the current row index within the list of rows currently being
   * operated on via a given phase.
   *
   * @return >= 0 & <= batchSize
   */
  public Integer getCurrentRowIndex() {
    return currentRowIndex;
  }

  /**
   * Resets the row index to zero. This method should normally be the first
   * instruction called within a phase execute() method and before iterating
   * over the list of Row objects.
   * <p>Example:
   * <p><hr><blockquote><pre>
   *     final List<DetailRow> rows = (List<DetailRow>) context.getRows();
   * --> context.resetCurrentRowIndex();
   *     for (DetailRow row : rows) {
   *       context.incrementCurrentRowIndex();
   *       final Integer score = row.getScore();
   *       determineGrade(row);
   *       context.incrementProcessedRowCount();
   *     }
   * </pre></blockquote><hr>
   * <p/>
   */
  public void resetCurrentRowIndex() {
    currentRowIndex = 0;
  }

  /**
   * Increments the row index by one. This method should be called each time a row
   * is iterated over for processing.
   * <p>Example:
   * <p><hr><blockquote><pre>
   *     final List<DetailRow> rows = (List<DetailRow>) context.getRows();
   *     context.resetCurrentRowIndex();
   *     for (DetailRow row : rows) {
   * -->   context.incrementCurrentRowIndex();
   *       final Integer score = row.getScore();
   *       determineGrade(row);
   *       context.incrementProcessedRowCount();
   *     }
   * </pre></blockquote><hr>
   * <p/>
   */
  public void incrementCurrentRowIndex() {
    currentRowIndex++;
  }

  /**
   * Retrieves the total number of rows that have been successfully processed.
   * The total applies to the entire feed run.
   *
   * @return total number of rows successfully processed.
   */
  public Integer getProcessedRowCount() {
    return processedRowCount;
  }

  /**
   * Increments the number of rows processed by the count.
   *
   * @param count the amount to increment.
   */
  public void addToProcessedRowCount(Integer count) {
    processedRowCount += count;
  }

  /**
   * This method is called to keep track of the total number of rows
   * successfully processed. This method increments the processed row index by
   * one. This method should be used if there's only one phase that handles the
   * processing of rows. Otherwise, use the method, addToProcessedRowCount().
   * This method should be called each time a row is iterated over for
   * processing and processing completes successfully.
   * <p>Example:
   * <p><hr><blockquote><pre>
   *     final List<DetailRow> rows = (List<DetailRow>) context.getRows();
   *     context.resetCurrentRowIndex();
   *     for (DetailRow row : rows) {
   *       context.incrementCurrentRowIndex();
   *       final Integer score = row.getScore();
   *       determineGrade(row);
   * -->   context.incrementProcessedRowCount();
   *     }
   * </pre></blockquote><hr>
   * <p/>
   */
  public void incrementProcessedRowCount() {
    processedRowCount++;
  }

  /**
   * Retrieves the accumulated count of rejected rows.
   *
   * @return accumulated count of rejected rows.
   */
  public Integer getRejectedRowCount() {
    return rejectedRowCount;
  }

  /**
   * Increments the accumlated number of rejected rows by the count.
   *
   * @param count the amount to increment.
   */
  public void addToRejectedRowCount(Integer count) {
    rejectedRowCount += count;
  }

  /**
   * Increments the accumulated count of rejected rows by one.
   */
  public void incrementRejectedRowCount() {
    rejectedRowCount++;
  }

  /**
   * Retrives the index of the rejected row in the context.
   *
   * @return index of the rejected row.
   */
  public Integer getRejectedRowNumber() {
    return rejectedRowNumber;
  }

  /**
   * Internally sets the index of the current rejected row in the context.
   */
  public void setRejectedRowNumber() {
    rejectedRowNumber = getPreviousSplitterIndex() + getCurrentRowIndex();
  }

  /**
   * Retrievers the current error code stored in the context.
   *
   * @return current error code stored in the context.
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * Stores an error code in the context.
   *
   * @param errorCode identifies a type of error.
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * Retrieves the current error message stored in the context.
   *
   * @return string cotaining an explanation of an error.
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * Stores an error message in the context.
   *
   * @param errorMessage the error message to store.
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * Stores the reference to the map of FeedPhaseStats objects.
   *
   * @return map of FeedPhaseStats objects.
   */
  public Map<String, FeedPhaseStats> getPhaseStatsMap() {
    return phaseStatsMap;
  }

  /**
   * Retrieves the reference to the map of FeedPhaseStats objects.
   *
   * @param phaseStatsMap
   */
  public void setPhaseStatsMap(Map<String, FeedPhaseStats> phaseStatsMap) {
    this.phaseStatsMap = phaseStatsMap;
  }
}
