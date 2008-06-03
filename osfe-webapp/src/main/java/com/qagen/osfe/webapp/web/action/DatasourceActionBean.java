package com.qagen.osfe.webapp.web.action;

import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.integration.spring.SpringBean;
import com.qagen.osfe.dataAccess.service.FeedDataSourceService;
import com.qagen.osfe.dataAccess.vo.FeedDataSource;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 */
@UrlBinding("/action/datasource")
public class DatasourceActionBean extends BaseActionBean {
  private FeedDataSourceService service;
  private FeedDataSource dataSource;

  @SpringBean(FeedDataSourceService.SERVICE_ID)
  public void setService(FeedDataSourceService service) {
    this.service = service;
  }

  public FeedDataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(FeedDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public List<FeedDataSource> getFeedDataSources() {
    return service.findAll();
  }

  public void save() {
    
  }

  public Resolution display() {
    return null;
  }
}
