# preEventPhase #

A preEventPhase is defined as a phase that is executed after the FeedFile and FeedJob
objects are created and persisted in t\_feed\_file and t\_feed\_job but **before** batchEventPhases are executed.

# Phase Event Processing Activity Diagram #
The activity diagram below describes the basic process of a phase event life cycle.

![http://www.qagen.com/images/osfe/preAndPostEventPhaseProcessing.jpg](http://www.qagen.com/images/osfe/preAndPostEventPhaseProcessing.jpg)

# Basic Code Logic #
The basic logic for preEventPhase processing is defined below.
```
  private void doPreEventPhases() {
    final List<Phase> phases = context.getPreEventPhases();

    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      feedJobManager.startPhaseStats(phase, context);
      phase.initialize();
      phase.execute();
      feedJobManager.endPhaseStats(phase,context);
    }
  }
```