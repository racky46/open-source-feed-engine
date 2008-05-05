package com.qagen.osfe.dataAccess.param;

/**
 * Created by IntelliJ IDEA.
 * User: htaylor
 * Date: Apr 28, 2008
 * Time: 3:05:23 PM
 */
public class MonitorQueueParam {
  private final Integer monitorId;
  private final String queueId;

  public MonitorQueueParam(Integer monitorId, String queueId) {
    this.monitorId = monitorId;
    this.queueId = queueId;
  }

  public Integer getMonitorId() {
    return monitorId;
  }

  public String getQueueId() {
    return queueId;
  }
}
