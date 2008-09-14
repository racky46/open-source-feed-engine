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

import com.qagen.osfe.core.FeedErrorException;

import java.io.IOException;
import java.io.Writer;

/**
 * Author: Hycel Taylor
 * <p/>
 */
public class FeedFileWriterUtil {
  public static enum EOL_VALUE {
    Mac,
    Unix,
    Windows
  }

  public static void writeEndOfLine(Writer writer, String eolValue) {
    try {
      switch (EOL_VALUE.valueOf(eolValue)) {
        case Mac:
          writer.write("\r");
          break;
        case Unix:
          writer.write("\n");
          break;
        case Windows:
          writer.write("\r\n");
          break;
      }
    } catch (IOException e) {
      throw new FeedErrorException(e);
    }
  }

}
