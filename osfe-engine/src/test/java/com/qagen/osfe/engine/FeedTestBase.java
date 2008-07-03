package com.qagen.osfe.engine;

import com.qagen.osfe.core.utils.FeedFileHelper;
import com.qagen.osfe.dataAccess.context.DataAccessContext;
import com.qagen.osfe.dataAccess.service.FeedService;
import com.qagen.osfe.dataAccess.vo.Feed;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class FeedTestBase {
  private static boolean cleanTables = true;

  public Feed getFeed(String feedId) {
    final FeedService service = (FeedService) DataAccessContext.getBean(FeedService.SERVICE_ID);
    return service.findByPrimaryId(feedId);
  }

  public void cleanDataTables() {
    if (!cleanTables) return;

    try {
      final DataSource dataSource = (DataSource) DataAccessContext.getBean("dataSource");
      final Connection con = dataSource.getConnection();
      final Statement statement = con.createStatement();

      statement.execute("truncate t_feed_phase_stats");
      statement.execute("truncate t_feed_checkpoint");
      statement.execute("truncate t_feed_job");
      statement.execute("truncate t_feed_file");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void setup(String feedId) {
    cleanDataTables();
    FeedFileHelper.createFeedDirectories(getFeed(feedId));
  }

  public void setCleanTables(boolean value) {
    cleanTables = value;
  }
}
