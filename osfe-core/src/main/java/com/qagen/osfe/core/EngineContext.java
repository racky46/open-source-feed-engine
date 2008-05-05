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
import com.qagen.osfe.dataAccess.vo.Checkpoint;
import com.qagen.osfe.dataAccess.vo.Feed;
import com.qagen.osfe.dataAccess.vo.FeedJob;
import org.dom4j.Document;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class EngineContext {
  private String feedFileName;
  private String fullFeedFileName;
  private String currentPhaseId;
  private String restartCheckPointId;
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
  private BufferedReader bufferedReader;
  private Feed feed;
  private FeedJob feedJob;
  private Checkpoint checkpoint;

  private Splitter headerSplitter;
  private Splitter footerSplitter;
  private Splitter detailSplitter;

  protected List<Phase> preFeedFilePhases;
  protected List<Phase> preEventPhases;
  protected List<Phase> batchEventPhases;
  protected List<Phase> postEventPhases;

  private Map<String, Object> map;
  private Map<String, Loader> loaderMap;
  private Map<String, EngineService> serviceMap;

  /**
   * Constructor
   */
  public EngineContext() {
    map = new HashMap<String, Object>();
  }

  public Object get(String name) {
    return map.get(name);
  }

  public void put(String name, Object object) {
    map.put(name, object);
  }

  public Map<String, Loader> getLoaderMap() {
    return loaderMap;
  }

  public void setLoaderMap(Map<String, Loader> loaderMap) {
    this.loaderMap = loaderMap;
  }

  public Integer getBatchSize() {
    return batchSize;
  }

  public void setBatchSize(Integer batchSize) {
    this.batchSize = batchSize;
  }

  public String getFeedFileName() {
    return feedFileName;
  }

  public void setFeedFileName(String feedFileName) {
    this.feedFileName = feedFileName;
  }

  public String getFullFeedFileName() {
    return fullFeedFileName;
  }

  public void setFullFeedFileName(String fullFeedFileName) {
    this.fullFeedFileName = fullFeedFileName;
  }

  public String getCurrentPhaseId() {
    return currentPhaseId;
  }

  public void setCurrentPhaseId(String currentPhaseId) {
    this.currentPhaseId = currentPhaseId;
  }

  public String getRestartCheckPointId() {
    return restartCheckPointId;
  }

  public void setRestartCheckPointId(String restartCheckPointId) {
    this.restartCheckPointId = restartCheckPointId;
  }

  public Integer getFeedRowCount() {
    return feedRowCount;
  }

  public void setFeedRowCount(Integer feedRowCount) {
    this.feedRowCount = feedRowCount;
  }

  public Integer getPreviousSplitterIndex() {
    return previousSplitterIndex;
  }

  public void setPreviousSplitterIndex(Integer previousSplitterIndex) {
    this.previousSplitterIndex = previousSplitterIndex;
  }

  public Integer getCurrentSplitterIndex() {
    return currentSplitterIndex;
  }

  public void setCurrentSplitterIndex(Integer currentSplitterIndex) {
    this.currentSplitterIndex = currentSplitterIndex;
  }

  public Document getFeedDocument() {
    return feedDocument;
  }

  public void setFeedDocument(Document feedDocument) {
    this.feedDocument = feedDocument;
  }

  public RowDescriptionLoader getRowDescriptionLoader() {
    return rowDescriptionLoader;
  }

  public void setRowDescriptionLoader(RowDescriptionLoader rowDescriptionLoader) {
    this.rowDescriptionLoader = rowDescriptionLoader;
  }

  public BufferedReader getBufferedReader() {
    return bufferedReader;
  }

  public void setBufferedReader(BufferedReader bufferedReader) {
    this.bufferedReader = bufferedReader;
  }

  public Feed getFeed() {
    return feed;
  }

  public void setFeed(Feed feed) {
    this.feed = feed;
  }

  public FeedJob getFeedJob() {
    return feedJob;
  }

  public void setFeedJob(FeedJob feedJob) {
    this.feedJob = feedJob;
  }

  public Checkpoint getCheckpoint() {
    return checkpoint;
  }

  public void setCheckpoint(Checkpoint checkpoint) {
    this.checkpoint = checkpoint;
  }

  public Map<String, Object> getMap() {
    return map;
  }

  public void setMap(Map<String, Object> map) {
    this.map = map;
  }

  public Splitter getHeaderSplitter() {
    return headerSplitter;
  }

  public void setHeaderSplitter(Splitter headerSplitter) {
    this.headerSplitter = headerSplitter;
  }

  public Splitter getFooterSplitter() {
    return footerSplitter;
  }

  public void setFooterSplitter(Splitter footerSplitter) {
    this.footerSplitter = footerSplitter;
  }

  public Splitter getDetailSplitter() {
    return detailSplitter;
  }

  public void setDetailSplitter(Splitter detailSplitter) {
    this.detailSplitter = detailSplitter;
  }

  public List<Phase> getPreFeedFilePhases() {
    return preFeedFilePhases;
  }

  public void setPreFeedFilePhases(List<Phase> preFeedFilePhases) {
    this.preFeedFilePhases = preFeedFilePhases;
  }

  public List<Phase> getPreEventPhases() {
    return preEventPhases;
  }

  public void setPreEventPhases(List<Phase> preEventPhases) {
    this.preEventPhases = preEventPhases;
  }

  public List<Phase> getBatchEventPhases() {
    return batchEventPhases;
  }

  public void setBatchEventPhases(List<Phase> batchEventPhases) {
    this.batchEventPhases = batchEventPhases;
  }

  public List<Phase> getPostEventPhases() {
    return postEventPhases;
  }

  public void setPostEventPhases(List<Phase> postEventPhases) {
    this.postEventPhases = postEventPhases;
  }

  public EngineService getServiceMap(String name) {
    return serviceMap.get(name);
  }

  public void setServiceMap(Map<String, EngineService> serviceMap) {
    this.serviceMap = serviceMap;
  }

  public Integer getCurrentRowIndex() {
    return currentRowIndex;
  }

  public void resentCurrentRowIndex() {
    currentRowIndex = 0;
  }

  public void incrementCurrentRowIndex() {
    currentRowIndex++;
  }

  public Integer getProcessedRowCount() {
    return processedRowCount;
  }

  public void addToProcessedRowCount(Integer count) {
    processedRowCount += count;
  }

  public void incrementProcessedRowCount() {
    processedRowCount++;
  }

  public Integer getRejectedRowCount() {
    return rejectedRowCount;
  }

  public void addToRejectedRowCount(Integer count) {
    rejectedRowCount += count;
  }

  public void incrementRejectedRowCount() {
    rejectedRowCount++;
  }

  public Integer getRejectedRowNumber() {
    return rejectedRowNumber;
  }

  public void setRejectedRowNumber() {
    rejectedRowNumber = getPreviousSplitterIndex() + getCurrentRowIndex();
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
