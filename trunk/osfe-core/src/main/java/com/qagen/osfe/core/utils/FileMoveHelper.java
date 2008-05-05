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


import static com.qagen.osfe.common.CommonConstants.SLASH;
import com.qagen.osfe.core.FeedErrorException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileMoveHelper {

  private static void moveFromTo(String sourceName, String destName, String sourceDir, String destDir) throws FeedErrorException {
    // File (or directory) to be moved
    final File sourceFile = new File(sourceDir + SLASH + sourceName);

    // Destination directory
    final File destFile = new File(destDir + SLASH + destName);

    // Move sourceFile to new directory
    final boolean success = sourceFile.renameTo(destFile);
    if (!success) {
      final String message =
        "File, " + sourceName + ", was not successfully moved from " + sourceDir + " to " + destDir + ".";
      throw new FeedErrorException(message);
    }
  }

  private static void copyFromTo(String fileName, String sourceDir, String destDir) throws IOException {
    // Create channel on the source
    final FileChannel srcChannel = new FileInputStream(sourceDir + SLASH + fileName).getChannel();

    // Create channel on the destination
    final FileChannel dstChannel = new FileOutputStream(destDir + SLASH + fileName).getChannel();

    // Copy file contents from source to destination
    dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

    // Close the channels
    srcChannel.close();
    dstChannel.close();
  }

  public static void removeFile(String fileName, String sourceDir) throws FeedErrorException {
    final File file = new File(sourceDir + SLASH + fileName);
    final boolean success = file.delete();
    if (!success) {
      final String message = "File, " + fileName + ", was not successfully deleted from " + sourceDir + ".";
      throw new FeedErrorException(message);
    }
  }

  public static void copyFromDirToDir(String fileName, String sourceDirName, String destDirName) throws IOException {
    FileMoveHelper.copyFromTo(fileName, sourceDirName, destDirName);
  }

  public static void copyFromDirToDir(String directory, String fileName, String sourceDirName, String destDirName) throws IOException {
    final String sourceDir = directory + SLASH + sourceDirName;
    final String destDir = directory + SLASH + destDirName;
    FileMoveHelper.copyFromTo(fileName, sourceDir, destDir);
  }

  public static void moveFromDirToDir(String directory, String sourceName, String destName, String sourceDirName, String destDirName) throws FeedErrorException {
    final String sourceDir = directory + SLASH + sourceDirName;
    final String destDir = directory + SLASH + destDirName;
    FileMoveHelper.moveFromTo(sourceName, destName, sourceDir, destDir);
  }

}
