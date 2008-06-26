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
package com.qagen.osfe.dataAccess.service;

import com.qagen.osfe.dataAccess.param.MonitorQueueParam;
import com.qagen.osfe.dataAccess.vo.FeedQueue;

import java.util.List;


public interface FeedQueueService extends Service<FeedQueue> {
  public static final String SERVICE_ID = "feedMappedQueueService";

  public FeedQueue findByPrimaryId(Integer primaryId);

  public void deleteAll(String monitorId);

  public void deleteByMonitorIdQueueId(MonitorQueueParam param);

  public List<FeedQueue> findAll(String monitorId);

  public List<FeedQueue> findByMonitorIdQueueId(MonitorQueueParam param);


}