# Services #

  * OSFE uses services to define its core behavior.
  * Services are also defined within an XML [configuration](http://code.google.com/p/open-source-feed-engine/wiki/ServiceConfigElement) document.
  * Services may be interchanged to modify OSFE's core behavior.
  * OSFE comes with a set of core services.
    * **StandardFeedLifeCycleService** - Defines a standard feed processing life cycle.
    * **PhaseLoaderService** - Defines a standard phase loader.
    * **ExceptionHandlerPlaceHolder** - Defines a place holder for custom exception handling.
  * New services may be added to OSFE at any time, expanding its capabilities.