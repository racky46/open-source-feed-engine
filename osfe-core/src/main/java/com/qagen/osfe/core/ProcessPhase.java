package com.qagen.osfe.core;

import com.qagen.osfe.core.row.Row;

import java.util.List;

/**
 * Author: Hycel Taylor
 * <p>
 * ProcessPhase contains methods that handle common business logic around
 * processing rows while keeping track of the row index counter and processed
 * row counter. Header phases and batch phases will most often extend
 * this class.
 */
public abstract class ProcessPhase extends Phase {

  /**
   * Constructor
   *
   * @param context reference to the engine context.
   * @param name uniquely identifies the given phase.
   */
  protected ProcessPhase(EngineContext context, String name) {
    super(context, name);
  }

  /**
   * This method should contain the custom business logic for
   * processing a given row.  This method is called from within
   * the loop processing of methods, singlePhaseLoop() and
   * multiPhaseLoop().
   *
   * @param row the current Row object to process.
   */
  public abstract void processRow(Row row);

  /**
   * This method will iterate over the current list of Row objects by calling
   * method, processRow() while updating the currentRowIndex counter and the
   * processedRow counter.  This should be used by only one phase of a multi
   * phase batch process because it also keeps track of the number of processed
   * rows. The method, multiPhaseLoop() should be used by the rest of the phases
   * of a multi phases batch process.
   */
  @SuppressWarnings("unchecked")
  public void singlePhaseLoop() {
    final List<Row> rows = (List<Row>) context.getRows();

    context.resetCurrentRowIndex();
    for (Row row : rows) {
      context.incrementCurrentRowIndex();
      processRow(row);
      context.incrementProcessedRowCount();
    }
  }

  /**
   * This method will iterate over the current list of Row objects by calling
   * method, processRow() while updating the currentRowIndex counter. This method will
   * not update the processedRow counter. The method, singlePhaseLoop, updates
   * both the currentRowIndex and the processRowIndex. In a multi phase batch
   * process, only a single phase should call the method, singlePhaseLoop(). The
   * rest of the phases should call this method.
   */
  @SuppressWarnings("unchecked")
  public void multiPhaseLoop() {
    final List<Row> rows = (List<Row>) context.getRows();

    context.resetCurrentRowIndex();
    for (Row row : rows) {
      context.incrementCurrentRowIndex();
      processRow(row);
    }
  }
}
