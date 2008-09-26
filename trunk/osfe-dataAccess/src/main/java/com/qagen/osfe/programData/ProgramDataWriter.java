package com.qagen.osfe.programData;

import com.qagen.osfe.programData.readerWriter.*;

/**
 * Created by IntelliJ IDEA.
 * User: htaylor
 * Date: Sep 17, 2008
 * Time: 10:00:32 AM
 */
public class ProgramDataWriter {

  public static void writeProgramData() {
    ReaderWriter readerWriter;

    readerWriter = new DataSourceRW();
    readerWriter.writeData();
    System.err.println("*** Writing of Datasources complete.");

    readerWriter = new FeedTypeRW();
    readerWriter.writeData();
    System.err.println("*** Writing of FeedTypes complete.");

    readerWriter = new FeedGroupRW();
    readerWriter.writeData();
    System.err.println("*** Writing of FeedGroups complete.");


    readerWriter = new FeedRW();
    readerWriter.writeData();
    System.err.println("*** Writing of Feeds complete.");

  }

  public static void main(String[] args) {
    ProgramDataWriter.writeProgramData();
  }
}
