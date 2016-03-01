# Feed Engine Database Table Definitions #

The sections defines each database table in detail.

  * [t\_error\_log](http://code.google.com/p/open-source-feed-engine/wiki/TErrorLog) - accessed by phases to store feed warnings and errors.
  * [t\_feed\_type](http://code.google.com/p/open-source-feed-engine/wiki/TFeedType) - defines unique feed types.
  * [t\_feed\_check\_point](http://code.google.com/p/open-source-feed-engine/wiki/TCheckPoint) - contains the base information to check point a feed file run.
  * [t\_feed\_data\_source](http://code.google.com/p/open-source-feed-engine/wiki/TDataSource) - defines unique data sources.
  * [t\_feed\_direction](http://code.google.com/p/open-source-feed-engine/wiki/TFeedDirection) - defines the two directions, inbound & outbound.
  * [t\_feed](http://code.google.com/p/open-source-feed-engine/wiki/TFeed) - contains feed unique definitions for each unique feed file.
  * [t\_feed\_file\_state](http://code.google.com/p/open-source-feed-engine/wiki/TFeedFileState) - defines the different states of a given row in t\_feed\_file.
  * [t\_feed\_file](http://code.google.com/p/open-source-feed-engine/wiki/TFeedFile) - contains the information to manage the life cycle of a given feed file.
  * [t\_feed\_group](http://code.google.com/p/open-source-feed-engine/wiki/TFeedGroup) - defines definitions for a group of t\_feed definitions.
  * [t\_feed\_job](http://code.google.com/p/open-source-feed-engine/wiki/TFeedJob) - contains the information to manage the life cycle of a given feed job.
  * [t\_feed\_job\_state](http://code.google.com/p/open-source-feed-engine/wiki/TFeedJobState) - defines the different states of a given row in t\_feed\_job.
  * [t\_feed\_monitor](http://code.google.com/p/open-source-feed-engine/wiki/TFeedMonitor) - defines unique monitor id's and used to establish unique feed monitors.
  * [t\_feed\_phase\_stats](http://code.google.com/p/open-source-feed-engine/wiki/TFeedPhaseStats) - contains statistics on each phase of a feed file run.
  * [t\_feed\_protocol](http://code.google.com/p/open-source-feed-engine/wiki/TFeedProtocol) - defines the two protocols,  request & response.
  * [t\_feed\_queue](http://code.google.com/p/open-source-feed-engine/wiki/TFeedQueue) - contains information for monitoring a queue of feeds.
  * [t\_feed\_queue\_type](http://code.google.com/p/open-source-feed-engine/wiki/TFeedQueueType) - defines unique queues that can be managed by one or more feed monitors.
  * [t\_feed\_queue\_state](http://code.google.com/p/open-source-feed-engine/wiki/TFeedQueueState) - defines the different state of a feed queue object.
  * [t\_feed\_role](http://code.google.com/p/open-source-feed-engine/wiki/TFeedRole) - defines user roles which determine the level of access of a given user.
  * [t\_feed\_user](http://code.google.com/p/open-source-feed-engine/wiki/TFeedUser) -  contains information for each user allowed access to the OSFE administration console.