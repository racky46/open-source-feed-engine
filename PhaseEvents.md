# Phase Events #

OSFE processes phases in what are called Phase Events.  Phase events can be loaded and executed in four configurations:
  * [preFeedFilePhase](http://code.google.com/p/open-source-feed-engine/wiki/PreFeedFilePhase)
  * [preEventPhase](http://code.google.com/p/open-source-feed-engine/wiki/preEventPhase)
  * [batchEventPhase](http://code.google.com/p/open-source-feed-engine/wiki/batchEventPhase)
  * [postEventPhase](http://code.google.com/p/open-source-feed-engine/wiki/postEvenPhase)


# 

&lt;phases&gt;

 configuration element #
Within a feed configuration document, phases are defined within the sub elements of the 

&lt;phases&gt;

 element. The 

&lt;phases&gt;

 element below is an example configuration:
```
  <phases batchSize="10000">
    <preFeedFilePhases>
       <phase name="fileNameValidationPhase" enable="true" className="com.qagen.osfe.examples.acme.phases.FileNameValidationPhase"/>
    </preFeedFilePhases>

    <preEventPhases>
      <phase name="footerPhase" enable="true" className="com.qagen.osfe.examples.acme.phases.FooterPhase"/>
      <phase name="headerPhase" enable="true" className="com.qagen.osfe.examples.acme.phases.HeaderPhase"/>
    </preEventPhases>

    <batchEventPhases>
      <phase name="beanPopulatorPhase" enable="true" className="com.qagen.osfe.examples.acme.phases.BeanPopulatorPhase"/>
      <phase name="gradingPhase" enable="true" className="com.qagen.osfe.examples.acme.phases.GradingPhase">
        <properties>
          <property name="echoDetail" value="false"/>
        </properties>
      </phase>
      <phase name="checkpoint" enable="true" className="com.qagen.osfe.core.phases.CheckPointPhase"/>
    </batchEventPhases>

    <postEventPhases>
      <phase name="statsPhase" enable="true" className="com.qagen.osfe.examples.acme.phases.StatsPhase"/>
    </postEventPhases>
  </phases>

```