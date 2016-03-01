# Feed Processing Life Cycle #

The feed processing life cycle is the defined as the set of one or more [feed job life cycles](http://code.google.com/p/open-source-feed-engine/wiki/FeedJobLifeCycle) associated to  a given [feed file life cycle](http://code.google.com/p/open-source-feed-engine/wiki/FeedFileLifeCycle) use to process a given feed file to either a successful completion or rejection of the feed file as permanently failed.

# Example Processing Life Cycle Scenarios #
The examples scenarios below show the state transitions of the feedFile, feedJob and movement of the feed file to the appropriate directory.

  1. [Scenario I](http://code.google.com/p/open-source-feed-engine/wiki/FeedEngineLifeCycleExampleI)  - a feed file is failed twice and then rejected.
  1. [Scenario II](http://code.google.com/p/open-source-feed-engine/wiki/FeedEngineLifeCycleExampleII) - a feed file is successfully completed.

# Feed Processing Life Cycle State Diagram #

The diagram defines the combined states of the feed file and feed a given feed job during the feed file life cycle.

> ![http://www.qagen.com/images/osfe/feedProcessingLifeCycle.jpg](http://www.qagen.com/images/osfe/feedProcessingLifeCycle.jpg)