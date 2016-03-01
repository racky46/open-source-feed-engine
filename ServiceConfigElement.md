# Service Configuration Element #

The 

&lt;service&gt;

 element must be defined within a feed configuration document and is used to define a set elements OSFE will use for the processing a given feed file.

# Example #
```
<services>
  <mainService>com.qagen.osfe.core.services.StandardFeedLifeCycleService</mainService>
  <phaseService>com.qagen.osfe.core.services.PhaseLoaderService</phaseService>
  <exceptionService>com.qagen.osfe.core.services.ExceptionHandlerPlaceHolder</exceptionService>
</services>
```