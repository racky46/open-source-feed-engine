package com.qagen.osfe.engine;

import com.qagen.osfe.common.FeedConstants;
import com.qagen.osfe.common.utils.DirectoryHelper;
import com.qagen.osfe.common.utils.Log;
import com.qagen.osfe.dataAccess.service.FeedService;
import com.qagen.osfe.dataAccess.vo.Feed;
import com.qagen.osfe.dataAccess.context.DataAccessContext;
import com.qagen.osfe.core.FeedErrorException;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: htaylor
 * Date: Oct 15, 2008
 * Time: 3:24:19 PM
 */
public class BatchInboundEngine implements FeedConstants {
  final String feedId;
  final FeedService feedService;
  final Feed feed;
  final String directory;

  private static Log logger = Log.getInstance(BatchInboundEngine.class);

  public BatchInboundEngine(String feedId) {
    this.feedId = feedId;
    feedService = (FeedService) DataAccessContext.getBean(FeedService.SERVICE_ID);

    feed = feedService.findByPrimaryId(feedId);

    if (feed == null) {
      throw new FeedErrorException("The Feed Id, " + feedId + ", is not defined in t_feed.");
    }

    directory = feed.getFeedDirectory();
  }

  public void processFeeds() {
    final List<String> fileNames = getListOfFileNamesFromIncoming();

    for (String fileName : fileNames) {
      logger.info("\nBatch processing feed file, " + fileName + ", for feedId, " + feedId + "...\n");
      final InboundFeedEngine engine = new InboundFeedEngine(feedId, fileName);
      engine.execute();
    }

  }

  @SuppressWarnings("unchecked")
  private List<String> getListOfFileNamesFromIncoming() {
    final String homeDir = DirectoryHelper.getHomeDirectory();
    final File file = new File(homeDir + FILE_SEPARATOR + directory + FILE_SEPARATOR + FEED_DIR.incoming.getValue());
    final String[] list = file.list();
    final List<String> fileNames = new ArrayList<String>();

    fileNames.addAll(Arrays.asList(list));

    return fileNames;
  }

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: BatchInboundFeedEngine feedId");
      System.err.println("Example: BatchInboundFeedEngine UPI_MN_ListingContent_Request");
      System.exit(-1);
    }

    final BatchInboundEngine engine = new BatchInboundEngine(args[0]);
    engine.processFeeds();
  }

}
