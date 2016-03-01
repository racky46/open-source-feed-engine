> Open Source Feed Engine (OSFE) is a world class open source feed engine application designed to manage and process multiple feed files of varying file formats, with varying behaviors, containing a few rows of data to millions of rows. OSFE processes files of delimited, fixed and single row XML formats, right out of the box.

**[Installation OSFE and run demo](http://code.google.com/p/open-source-feed-engine/wiki/OSFE_INSTALL)**

OSFE provides a common framework and design patterns for three primary types of feed processing engines:

  * Data Retriever Engines – Engines that retrieve data from internal or external sources (e.g., retrieving data via socket, FTP, Web Service). Retrieved files may also need to be prepared for processing (e.g., data may need to be unzipped, unencrypted, concatenated, etc).

  * Inbound Data Processing Engines – A raw data that has been retrieved and prepared for processing is parsed, passed through phases and processed into meaningful data (e.g., filtered, calculated and sent to persistent storage).

  * Outbound Data Creation Engines – Data is pulled from database tables or files, processed and placed into a feed file in a specified format (e.g., sending an enrollment response to complete the contract from a given enrollment request feed).

At it's core,  OSFE is driven by several key concepts:

  * XML driven -  OSFE needs an XML document to instruct it on how to process a given feed file.

  * Service driven – OSFE uses [services](http://code.google.com/p/open-source-feed-engine/wiki/ServiceOverview) to define its core behavior. These core services all are also defined within the XML feed document an can be interchanged to modify OSFE's core behavior. OSFE comes with a set of core services designed to cover most feed processing tasks.

  * Phase driven – OSFE processes feeds by passing data through one of more [phases](http://code.google.com/p/open-source-feed-engine/wiki/PhaseOverview).  Each phase has the responsibility of performing specific operations on the given data. Phase definitions are added to an XML feed document to instruct OSFE on how to process a feed.

  * Database driven – OSFE uses a [database](http://code.google.com/p/open-source-feed-engine/wiki/OSFEDB) to manage the complete life cycle of processing a feed. The databases maintains a description of all feed files, feed types and data sources. It also maintains a permanent history of each feed run.

  * Life Cycle driven – OSFE defines a framework for managing the complete [feed processing life cycle](http://code.google.com/p/open-source-feed-engine/wiki/FeedProcessingLifeCycle) that includes handling exceptions for failing a feed, retrying a failed feed and rejecting a failed feed that can not be repaired.

  * Directory driven – OSFE creates and maintains a specific set of [directories](http://code.google.com/p/open-source-feed-engine/wiki/FeedDirectoryStructure) for managing a feed file's complete life cycle to include where a feed may be downloaded, where is placed when it's being processed, where it is placed if the feed is failed and where it may be permanently archived.
