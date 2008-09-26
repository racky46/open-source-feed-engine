package com.qagen.osfe.programData;

import com.qagen.osfe.programData.readerWriter.*;

/**
 * Created by IntelliJ IDEA.
 * User: htaylor
 * Date: Sep 17, 2008
 * Time: 10:00:32 AM
 */
public class ProgramDataReader {

  public static void readProgramData() {
    ReaderWriter readerWriter;

    readerWriter = new DataSourceRW();
    readerWriter.readData();
    System.err.println("*** Reading of Datasources complete.");

    readerWriter = new FeedTypeRW();
    readerWriter.readData();
    System.err.println("*** Reading of FeedTypes complete.");

    readerWriter = new FeedGroupRW();
    readerWriter.readData();
    System.err.println("*** Reading of FeedGroups complete.");

    readerWriter = new FeedRW();
    readerWriter.readData();
    System.err.println("*** Reading of Feeds complete.");
  }

  public static void main(String[] args) {
    ProgramDataReader.readProgramData();
  }
}
