# Phase #

  * OSFE processes feeds by passing its data through one or more phases.
  * OSFE processes phases through four specific [events](http://code.google.com/p/open-source-feed-engine/wiki/PhaseEvents) of a feed file lifecycle.
  * Each phase performs a specific set of operations on the given data.
  * Engineers implement phases and define phases in XML feed config documents.
  * All phase classes extend from abstract class Phase.
  * Phases can be defined and used in hundreds of feed configuration documents.

# Example Phases Extended From Phase #

![http://www.qagen.com/images/osfe/phase.jpg](http://www.qagen.com/images/osfe/phase.jpg)