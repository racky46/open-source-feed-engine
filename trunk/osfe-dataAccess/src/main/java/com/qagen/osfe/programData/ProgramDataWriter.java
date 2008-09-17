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

    readerWriter = new FeedTypeRW();
    readerWriter.writeData();

    readerWriter = new FeedGroupRW();
    readerWriter.writeData();

    readerWriter = new FeedRW();
    readerWriter.writeData();
  }

  public static void main(String[] args) {
    ProgramDataWriter.writeProgramData();
  }
}
