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
package com.qagen.osfe.core.utils;

import static com.qagen.osfe.common.FeedConstants.*;
import com.qagen.osfe.common.utils.DirectoryHelper;
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.dataAccess.context.DataAccessContext;
import com.qagen.osfe.dataAccess.service.FeedService;
import com.qagen.osfe.dataAccess.vo.Feed;

import java.io.File;

public class FeedFileHelper {

  public static String getFeedDirFullPath(Feed feed) {
    return DirectoryHelper.getHomeDirectory() + feed.getFeedDirectory();
  }

  public static void createFeedDirectories(Feed feed) {
    String[] directories;

    if (feed.getFeedDirection().getFeedDirectionId().equals(FEED_DIRECTION.inbound.getValue())) {
      directories = INBOUND_DIRECTORIES;
    } else {
      directories = OUTBOUND_DIRECTORIES;
    }

    final String fullPath = getFeedDirFullPath(feed);

    for (String directory : directories) {
      final String fileName = fullPath + SLASH + directory;
      final File file = new File(fileName);

      if (!file.exists()) {
        boolean success = (file).mkdirs();
        if (!success) {
          final String message = "Unable to create directory structure: " + file.getAbsolutePath();
          throw new FeedErrorException(message);
        }
      }
    }
  }

  public static void createFeedDirectories(String feedId) {
    final FeedService service = (FeedService) DataAccessContext.getBean(FeedService.SERVICE_ID);
    final Feed feed = service.findByPrimaryId(feedId);

    if (feed == null) {
      final String message = "A feed object matchin feedFileId, " + feedId + ", was not found.";
      throw new FeedErrorException(message);
    }

    createFeedDirectories(feed);
  }
}
