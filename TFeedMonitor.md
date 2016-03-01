# t\_feed\_monitor #

The t\_feed\_monitor table defines monitor identifiers.  An OSFE feed monitor can manage the processing of multiple feeds simultaneously.  When a monitor is launched, it must be associated with a defined monitor identifier in t\_feed\_monitor. Multiple feed monitors can be launched, but each in a different JVM or on different machines.  Each feed monitor must be assigned a unique monitor id defined in t\_feed\_monitor.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_monitor\_id | varchar(30) | No       | Uniquely defines the feed monitor |
|         | description | varchar(250) | No       | Description of the feed monitor |
