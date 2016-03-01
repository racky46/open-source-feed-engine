# batchEventPhase #

  * A batchEventPhase is defined as a phase that is executed after the FeedFile and FeedJob  objects are created and persisted in t\_feed\_file and t\_feed\_job but **after** preEventPhases are executed.
  * batchEventPhases are executed in batches.
  * A batch is a finite set of row objects containing raw data parsed into a specific row object type.
  * The number of rows loaded and referenced to each batch is defined in the configuration document as an attribute of the 

&lt;phases&gt;

 element.
  * Because data is processed through batches, feed files containing millions of rows of data can be processed by OSFE taking up only enough memory to process the current batch of rows. Thus, multiple OSFE instances can run on the same server within a single JVM.
  * For example, with a configured batch size of 10,000, OSFE will process the batchEventPhases 1,000 times for a feed file containing 10,000,000 rows of data.

# Defining Batch Size #
```
<phases batchSize="10000">
```

# Basic Code Logic #
The logic for a batchEventPhase is more complex than other phase processing events. The code snippet below is used in the class, StandardFeedLifeCycleService.java.
```
  private void doBatchEventPhases() {
    final Splitter splitter = context.getDetailSplitter();
    final List<Phase> phases = context.getBatchEventPhases();

    // perform batch phase initialization.
    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      feedJobManager.startPhaseStats(phase, context);
      phase.initialize();
      feedJobManager.endPhaseStats(phase, context);
    }

    // Get checkpoint from context after checkpoint phase.initialize().
    final Checkpoint checkpoint = context.getCheckpoint();

    // Must be called after initialization and before anything else.
    splitter.prePhaseExecute();

    // Move to checkpoint if splitter is instance of checkpoint
    if (splitter instanceof CheckpointHandler) {
      if (checkpoint != null) {
        ((CheckpointHandler) splitter).moveToCheckPoint(checkpoint);
      }
    }

    // Determine if a checkpoint has been created.
    Boolean movingToCheckpoint = false;
    if (checkpoint != null && !checkpoint.getPhaseId().equals(CheckpointPhase.NO_PHASE_ID)) {
      movingToCheckpoint = true;
    }

    while (splitter.hasNextRow()) {
      for (Phase phase : phases) {
        // Bypass phases processing when restart from a checkpoint.
        if (!movingToCheckpoint) {
          context.setCurrentPhaseId(phase.getName());
          feedJobManager.startPhaseStats(phase, context);
          phase.execute();
          feedJobManager.endPhaseStats(phase, context);
        }

        // Checkpoint restart has be reached.
        if (movingToCheckpoint && checkpoint.getPhaseId().equals(phase.getName())) {
          movingToCheckpoint = false;
        }
      }
    }
  }  
```