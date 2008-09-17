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

    readerWriter = new FeedTypeRW();
    readerWriter.readData();

    readerWriter = new FeedGroupRW();
    readerWriter.readData();

    readerWriter = new FeedRW();
    readerWriter.readData();
  }

  public static void main(String[] args) {
    ProgramDataReader.readProgramData();
  }
}
