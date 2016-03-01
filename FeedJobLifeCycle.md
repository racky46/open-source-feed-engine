# Feed Job Life Cycle #

The processing of a feed file within the feed file life cycle is defined as a feed job.  The processing of a feed file must take place within a feed job.  It is possible that a feed file may not be successfully completed during the processing of a given feed job.  Thus, a given feed file may be associated to more than one feed job during a given feed file life cycle.

# Feed Job Life Cycle State Diagram #

> ![http://www.qagen.com/images/osfe/feedJobLifeCycle.jpg](http://www.qagen.com/images/osfe/feedJobLifeCycle.jpg)

# Feed Job Life Cycle States #

During a feed job life cycle, a feed job may transition between one of four states:

  * active
  * failed
  * resolved
  * completed

# active #

The 'active' specifies that the given feed file is being processed within the given feed job.  All feed jobs must, by default, start in an 'active' state.


# completed #

The 'completed' state specifies that the feed job completed successfully.  A feed job can only transition to the 'completed' from the 'active' state.

# failed #

The 'failed' state specifies that the feed job did not complete successfully.  If during any step in the feed processing life cycle the feed fails, the feed job must transition to a 'failed' state.  A feed job that is in a failed state can only transition to a 'resolved' state.

# resolved #

The 'resolved' state specifies that a feed job, which was previously in a 'failed' state has been researched and resolved.