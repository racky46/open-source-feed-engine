# preFeedFilePhase #

A preFeedFilePhase is defined as phases that are executed before the FeedFile and FeedJob
objects are created and persisted in t\_feed\_file and t\_feed\_job.

# Phase Event Processing Activity Diagram #
The activity diagram below describes the basic process of a phase event life cycle.

![http://www.qagen.com/images/osfe/preFilePhaseProcessing.jpg](http://www.qagen.com/images/osfe/preFilePhaseProcessing.jpg)

# Basic Code Logic #
The basic logic for preFeedFilePhase processing is defined below.
```
  protected void runPreFeedFilePhases() {
    final List<Phase> phases = context.getPreFeedFilePhases();

    for (Phase phase : phases) {
      context.setCurrentPhaseId(phase.getName());
      phase.initialize();
      phase.execute();
    }
  }
```