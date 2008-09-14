package com.qagen.osfe.core.phases.outbound.delimited;

import com.qagen.osfe.core.BufferedFeedFileWriter;
import com.qagen.osfe.core.FeedErrorException;
import com.qagen.osfe.core.Phase;
import com.qagen.osfe.core.loaders.DelimitedFileDescLoader;
import com.qagen.osfe.core.row.DelimitedRowBuilder;
import com.qagen.osfe.core.utils.FeedFileWriterUtil;
import com.qagen.osfe.core.vo.DelimitedColumn;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * Author: Hycel Taylor
 * <p/>
 * The Phase is a helper phase and simplies the task of writing a line
 * of data to the outbound feed file. <p>
 * It uses the delimited file description defined within the given feed
 * document to attain the delimiter, end of line character and name of each
 * attribute of a row bean in order to output a delimited row to a buffered
 * writer.
 */
public abstract class DetailPhase extends Phase {
  protected BufferedWriter bufferedWriter;
  protected DelimitedFileDescLoader delimitedFileDescLoader;
  protected String eolCharacter;
  protected String delimiter;
  protected List<DelimitedColumn> delimitedColumns;

  /**
   * The delimitedFileDescLoader parses and loads the deilimitedFileDescription
   * elements from the given configuration file.
   * <p/>
   * <ul><li>Injection - required</li></ul>
   *
   * @param delimitedFileDescLoader reference to the delimited file description.
   */
  public void setDelimitedFileDescLoader(DelimitedFileDescLoader delimitedFileDescLoader) {
    this.delimitedFileDescLoader = delimitedFileDescLoader;
  }

  /**
   * Because OSFE uses dependency injection, the initialize method should be
   * used during a second pass of iniitializing objects.
   */
  public void initialize() {
    bufferedWriter = ((BufferedFeedFileWriter) context.getFeedFileWriter()).getBufferedWriter();
    eolCharacter = delimitedFileDescLoader.getEndOfLineValue();
    delimiter = delimitedFileDescLoader.getDelimiterValue();
    delimitedColumns = delimitedFileDescLoader.getDetailColumns();
  }

  /**
   * This method builds a delimited string using the given beand, delimited
   * column descriptions and the delimiter.
   *
   * @param bean string of values referenced from the bean each sepearted by the
   *             given delimiter.
   */
  public void writeBean(Object bean) {
    try {
      final String row = DelimitedRowBuilder.rowBuilder(bean, delimitedColumns, delimiter);
      bufferedWriter.write(row);
      FeedFileWriterUtil.writeEndOfLine(bufferedWriter, eolCharacter);
      context.incrementCurrentRowIndex();
      context.incrementProcessedRowCount();
    } catch (IOException e) {
      throw new FeedErrorException(e);
    }
  }
}
