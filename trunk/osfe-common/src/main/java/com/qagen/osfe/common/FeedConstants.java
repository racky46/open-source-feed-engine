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
package com.qagen.osfe.common;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FeedConstants extends CommonConstants {
  public final static String CONFIG_FILE = "config.xml";

  // Feed Directories
  public enum FEED_DIR {
    incoming("incoming"),
    download("download"),
    original("original"),
    outgoing("outgoing"),
    workarea("workarea"),
    preprocess("preprocess"),
    postprocess("postprocess"),
    rejected("rejected"),
    archive("archive"),
    failed("failed"),
    temp("temp");

    private String value;

    FEED_DIR(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  // Timestamp Constants
  public static final Timestamp DEFAULT_PROCESSING_END_TIMESTAMP =
    new Timestamp(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTimeInMillis());

  // Inbouond Directories
  public static final String[] INBOUND_DIRECTORIES =
    {
      FEED_DIR.incoming.getValue(),
      FEED_DIR.download.getValue(),
      FEED_DIR.preprocess.getValue(),
      FEED_DIR.original.getValue(),
      FEED_DIR.workarea.getValue(),
      FEED_DIR.rejected.getValue(),
      FEED_DIR.archive.getValue(),
      FEED_DIR.failed.getValue(),
      FEED_DIR.temp.getValue()
    };

  // Outbound Directoriess
  public static final String[] OUTBOUND_DIRECTORIES =
    {
      FEED_DIR.outgoing.getValue(),
      FEED_DIR.workarea.getValue(),
      FEED_DIR.postprocess.getValue(),
      FEED_DIR.rejected.getValue(),
      FEED_DIR.archive.getValue(),
      FEED_DIR.failed.getValue(),
      FEED_DIR.temp.getValue()
    };

  // Feed File States
  public enum FEED_FILE_STATE {
    processing("processing"),
    failed("failed"),
    completed("completed"),
    rejected("rejected"),
    retry("retry");

    private String value;

    FEED_FILE_STATE(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  // Feed Job States
  public enum FEED_JOB_STATE {
    active("active"),
    failed("failed"),
    resolved("resolved"),
    completed("completed");

    private String value;

    FEED_JOB_STATE(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  public enum FEED_QUEUE_STATE {
    waiting("waiting"),
    processing("processing"),
    failed("failed"),
    completed("completed");

    private String value;

    FEED_QUEUE_STATE(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  // Feed Protocols
  public enum FEED_PROTOCOL {
    request("request"),
    response("response");

    private String value;

    FEED_PROTOCOL(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  // Feed Direction
  public enum FEED_DIRECTION {
    inbound("inbound"),
    outbound("outboune");

    private String value;

    FEED_DIRECTION(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  public enum FEED_RETRIEVE_STATE {
    processing("processing"),
    failed("failed"),
    completed("completed");

    private String value;

    FEED_RETRIEVE_STATE(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }
}
