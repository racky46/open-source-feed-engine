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
package com.qagen.osfe.examples.acme;

import com.qagen.osfe.dataAccess.context.DataAccessContext;
import com.qagen.osfe.engine.FeedEngine;

import javax.sql.DataSource;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class RunDemo {
  private static final String OSFE_HOME = "OSFE_HOME";
  private static final String SLASH = "/";

  private static final String FIXED_FEED_ID = "acme_qagen_testf_request";
  private static final String FIXED_FEED_DIR = "/feed/acme/qagen/testf/request";

  private static final String DELIMITED_FEED_ID = "acme_qagen_testd_request";
  private static final String DELIMITED_FEED_DIR = "/feed/acme/qagen/testd/request";

  private static final String ARCHIVE_DIR = "/archive";
  private static final String INCOMING_DIR = "/incoming";

  /**
   * Constructor
   */
  public RunDemo() {
    cleanDataTables();

    moveFileFromArchive(DELIMITED_FEED_DIR + ARCHIVE_DIR, DELIMITED_FEED_DIR + INCOMING_DIR);
    moveFileFromArchive(FIXED_FEED_DIR + ARCHIVE_DIR, FIXED_FEED_DIR + INCOMING_DIR);

    processFiles(DELIMITED_FEED_ID, DELIMITED_FEED_DIR + INCOMING_DIR);
    processFiles(FIXED_FEED_ID, FIXED_FEED_DIR + INCOMING_DIR);
  }

  /**
   * Retrieves the path from the OSFE_HOME environment variable.
   *
   * @return OSFE home directory path.
   * @throws RuntimeException is path is null.
   */
  private String getHomeDirectory() {
    final String path = System.getenv(OSFE_HOME);

    if (path == null) {
      throw new RuntimeException("The OSFE_HOME environment variable has not been defined.");
    }

    return path;
  }

  /**
   * Truncates the tables that OSFE uses to manage the lifecycle of feed files.
   */
  public void cleanDataTables() {
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

  /**
   * Moves any feed files from in the archive directory to the incoming
   * directory.
   *
   * @param fromDir defines the source directory.
   * @param toDir defines the destination diretory.
   */
  private void moveFileFromArchive(String fromDir, String toDir) {
    final String homeDir = getHomeDirectory();
    final File file = new File(homeDir + fromDir);
    final File[] files = file.listFiles();

    for (File fileObject : files) {
      final String newDir = homeDir + toDir;
      final File newFile = new File(newDir, fileObject.getName());

      fileObject.renameTo(newFile);
    }
  }

  /**
   * Retrieves the list of filenames from the given directory.
   *
   * @param directory the path to retrieve the list of file names from.
   * @return list of filenames from the given directory.
   */
  private List<String> getFileNames(String directory) {
    final String homeDir = getHomeDirectory();
    final File[] files = new File(homeDir + directory).listFiles();
    final List<String> fileNames = new ArrayList<String>();

    for (File file : files) {
      fileNames.add(file.getName());
    }

    return fileNames;
  }

  /**
   * Traverse the list of feed files and launch each one in it's own
   * FeedEngine thread.
   *
   * @param feedId    identifies the feed definition OSFE should look up in t_feed.
   * @param directory defines the directory to attain the list of feed files.
   */
  private void processFiles(String feedId, String directory) {
    final List<String> feedFiles = getFileNames(directory);

    for (String feedFile : feedFiles) {
      final Thread thread = new Thread(new FeedEngine(feedId, feedFile));
      thread.start();
    }
  }

  /**
   * Used to launch class from command line.
   *
   * @param args no arguments are required.
   */
  public static void main(String[] args) {
    new RunDemo();
  }
}
