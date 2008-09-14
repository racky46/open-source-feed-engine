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

import com.qagen.osfe.dataAccess.vo.Feed;
import com.qagen.osfe.dataAccess.vo.FeedJob;
import com.qagen.osfe.dataAccess.vo.FeedPhaseStats;
import org.dom4j.Document;

import java.sql.Timestamp;
import java.util.ArrayList;
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
  private Timestamp timestamp;

  private Document feedDocument;
  private String feedFileName;
  private String fullFeedFileName;
  private String currentPhaseId;
  private String delimiterValue;
  private String endOfLineValue;

  private Long previousSplitterIndex = 0L;
  private Long currentSplitterIndex = 0L;

  private Integer feedRowCount = 0;
  private Integer currentRowIndex = 0;
  private Integer batchSize;
  private Integer sequenceNumber;

  private Integer processedRowCount = 0;
  private Integer rejectedRowCount = 0;
  private Integer rejectedRowNumber;
  private String errorCode;
  private String errorMessage;

  private FeedFileReader feedFileReader;
  private FeedFileWriter feedFileWriter;
  private Feed feed;
  private FeedJob feedJob;

  private List<Phase> preFeedFilePhases;

  protected List rows;

  private Map<String, Object> userMap;
  private Map<String, FeedPhaseStats> phaseStatsMap;
  private Map<String, Object> beanMap;

  /**
   * Constructor
   */
  public EngineContext() {
    userMap = new HashMap<String, Object>();
    timestamp = new Timestamp(System.currentTimeMillis());
    beanMap = new HashMap<String, Object>();
  }

  /**
   * A timestamp is created upon the instantiation of an EngineContext.
   * This timestamp can be used by different components that need to
   * represent the same time stamp.  For example, the file name may use
   * the timestamp, but the file header and footer may also need to have
   * the same timestamp to match the one in the file name.
   *
   * @return the fixed timestamp calculated only once.
   */
  public Timestamp getTimestamp() {
    return timestamp;
  }

  public String getDelimiterValue() {
    return delimiterValue;
  }

  public void setDelimiterValue(String delimiterValue) {
    this.delimiterValue = delimiterValue;
  }

  public String getEndOfLineValue() {
    return endOfLineValue;
  }

  public void setEndOfLineValue(String endOfLineValue) {
    this.endOfLineValue = endOfLineValue;
  }

  /**
   * Retrieves an object from the internal context map.
   *
   * @param name id's the object to retrieve from the map.
   * @return null if object not found in map from the given name.
   */
  public Object get(String name) {
    return userMap.get(name);
  }

  /**
   * Places an object referce in the internal context map.
   *
   * @param name   uniquely identifes the object.
   * @param object the object to be referenced.
   */
  public void put(String name, Object object) {
    userMap.put(name, object);
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
   * Stores a sequence number.  The same sequence number should be used
   * during the processing of a feed file lifecycle.
   *
   * @return
   */
  public Integer getSequenceNumber() {
    return sequenceNumber;
  }

  public void setSequenceNumber(Integer sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
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
   * currently being operated on. This should be used for inbound feeds.
   *
   * @return count of the numbers of rows contained within the feed file.
   */
  public Integer getFeedRowCount() {
    return feedRowCount;
  }

  /**
   * Sets the count of the numbers of rows contained within the feed file
   * currently being operated on. This should be used for inbound feeds.
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
  public Long getPreviousSplitterIndex() {
    return previousSplitterIndex;
  }

  /**
   * Spitter classes may need to know the previous splitter index for further
   * splitter calculation. This method ets the previous splitter index.
   *
   * @param previousSplitterIndex the index where that last split took place.
   */
  public void setPreviousSplitterIndex(Long previousSplitterIndex) {
    this.previousSplitterIndex = previousSplitterIndex;
  }

  /**
   * Spitter classes may need to know the current splitter index for further
   * splitter calculation. This method retrieves the current splitter index.
   *
   * @return the index where that current split took place.
   */
  public Long getCurrentSplitterIndex() {
    return currentSplitterIndex;
  }

  /**
   * Spitter classes may need to know the current splitter index for further
   * splitter calculation. This method sets the current splitter index.
   *
   * @param currentSplitterIndex the index where the current split took place.
   */
  public void setCurrentSplitterIndex(Long currentSplitterIndex) {
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
   * Splitters will use this object and cast it to the appriate type for
   * accessing data from the physical file.  For example, deilmited splitters
   * will uses a BufferedReader to access data from the physiacl feed file.
   *
   * @return reference to object used to access the physical feed file.
   */
  public FeedFileReader getFeedFileReader() {
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
  public void setFeedFileReader(FeedFileReader feedFileReader) {
    this.feedFileReader = feedFileReader;
  }

  /**
   * This interface presents a common place to retrieve a filewriter in the
   * context.
   *
   * @return reference to object used to write data to an output file.
   */
  public FeedFileWriter getFeedFileWriter() {
    return feedFileWriter;
  }

  /**
   * This interface presents a common place to store a filewriter in the
   * context.
   *
   * @param feedFileWriter reference to object used to write data to an output file.
   */
  public void setFeedFileWriter(FeedFileWriter feedFileWriter) {
    this.feedFileWriter = feedFileWriter;
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
   * Retrieves a map of user defined objects. These are objects that are non
   * standard to OSFE and have no accessor or mutator methods.  Objects
   * stored here are usually accessed and manipulated by phases and services
   * created by engineers and defined within a feed configuration document.
   *
   * @return map user defined objects.
   */
  public Map<String, Object> getMap() {
    return userMap;
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
    this.userMap = map;
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
    rejectedRowNumber = (int) (getPreviousSplitterIndex() + getCurrentRowIndex());
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
   * @param phaseStatsMap map of FeedPhaseStats objects
   */
  public void setPhaseStatsMap(Map<String, FeedPhaseStats> phaseStatsMap) {
    this.phaseStatsMap = phaseStatsMap;
  }

  /**
   * Retrieve the reference to the Map of beans.
   *
   * @return reference to the Map of beans.
   */
  public Map<String, Object> getBeanMap() {
    return beanMap;
  }

  /**
   * Retrieve the reference to a specific bean.
   *
   * @param name identifies the bean to reference.
   * @return null if bean was not found by the value in name.
   */
  public Object getBean(String name) {
    return beanMap.get(name);
  }

  /**
   * Retrieve the reference to a specific bean and throws an exception
   * if no match was found.
   *
   * @param name identifies the bean to reference.
   * @return reference to a specific bean.
   * @thows FeedErrorException if by was not found by the value in name.
   */
  public Object getRequiredBean(String name) {
    final Object bean = beanMap.get(name);

    if (bean == null) {
      throw new FeedErrorException("The bean, " + name + ", was not found.");
    }

    return bean;
  }

  /**
   * Adds a bean to the bean map in the engine context.
   *
   * @param name will uniquely identify the bean.
   * @param bean the bean to add to the map.
   */
  public void putBean(String name, Object bean) {
    beanMap.put(name, bean);
  }

  /**
   * Traverses the map of beans and returns only the beans that are and
   * instance of Phase.
   *
   * @return list of Phases.
   */
  public List<Phase> getPhases() {
    final List<Phase> list = new ArrayList<Phase>();

    for (Object bean : beanMap.values()) {
      if (bean instanceof Phase) {
        list.add((Phase) bean);
      }
    }

    return list;
  }

  /**
   * Traverses the map of beans until it finds a bean that is an instance
   * of CheckPointService.  Note: There should only be one check point service
   * bean in the map.  If there is more than one, it will only return the
   * first one it lands on.
   *
   * @return null if no bean matches instance of CheckpointService.
   */
  public CheckpointService getCheckPointService() {

    for (Object bean : beanMap.values()) {
      if (bean instanceof CheckpointService) {
        return (CheckpointService) bean;
      }
    }

    return null;
  }

  /**
   * Traverses the map of beans until it finds a bean that is an instance
   * of ExceptionService.  Note: There should only be one check point service
   * bean in the map.  If there is more than one, it will only return the
   * first one it lands on.
   *
   * @return null if no bean matches instance of ExceptionService.
   */
  public ExceptionService getExceptionService() {

    for (Object bean : beanMap.values()) {
      if (bean instanceof ExceptionService) {
        return (ExceptionService) bean;
      }
    }

    return null;
  }

  /**
   * Traverses the map of beans until it finds a bean that is an instance
   * of FeedFileLifeCycleService.  Note: There should only be one check point service
   * bean in the map.  If there is more than one, it will only return the
   * first one it lands on.
   *
   * @return null if no bean matches instance of FeedFileLifeCycleService.
   */
  public MainLifeCycleService getFeedLifeCycleService() {

    for (Object bean : beanMap.values()) {
      if (bean instanceof MainLifeCycleService) {
        return (MainLifeCycleService) bean;
      }
    }

    return null;
  }
}
