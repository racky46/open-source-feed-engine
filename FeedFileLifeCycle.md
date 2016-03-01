# Feed File Life Cycle #

The feed engine defines a complete life cycle for processing a feed file from its' beginning to either its' successful completion, failure or rejection from the feed process.

# Feed File Life Cycle State Diagram #

> ![http://www.qagen.com/images/osfe/feedFileLifeCycle.jpg](http://www.qagen.com/images/osfe/feedFileLifeCycle.jpg)

# Feed File Life Cycle States #
During the feed file life cycle, a feed file may transition between one of five states:

  * processing
  * completed
  * failed
  * retry
  * rejected

# processing #
The 'processing' state specifies that the feed engine is currently processing the feed.  The 'processing' state is the initial state of a given feed file life cycle.

# completed #
The 'completed' state specifies that the feed engine has successfully completed processing the feed.

# failed #
The 'failed' state specifies that the feed engine failed to successfully complete processing the feed.

# retry #
The 'retry' state specifies that the feed, which was in a failed state, has been repaired and another attempt should be may to process the feed.

# rejected #
The 'rejected' state specifies that feed, which was in a failed state, cannot be successfully processed and has been permanently rejected.