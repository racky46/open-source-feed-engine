# t\_feed #

The t\_feed tables contains feed unique definitions for each unique feed file that will be processed by the feed engine.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_id | varchar(60) | No       | Uniquely defines a feed |
|         | activation\_date | timestamp | No       | Defines the date the feed may be processed |
|         | termination\_date | timestamp | No       | Defines the date the feed may not be processed |
|         | allow\_concurrent\_runs | tinyint  | No       | Defines if more than one feed matching the given feed\_id can be run concurrently |
|         | allow\_failed\_feed\_runs | tinyint  | No       | Defines if more than feed matching the given feed\_id can be run with at least one other feed  matching the feed\_id is in a failed state |
|         | restart\_at\_checkpoint | tinyint  | No       | Defines if checkpoint is active |
|         | collect\_phase\_stats | tinyint  | No       | Determines if phase statistics should be collected |
|         | feed\_directory | varchar(255) | No       | Defines the incoming direction of the feed from the base feed engine directory structure |
|         | feed\_document | varchar(255) | No       | Defines the directory location of the configuration file to be used with the given feed |
|         | queue\_id | varchar(60) | Yes      | Defines the queue the feed should run on |
|         | max\_concurrent\_runs | int      | No       | Defines the number of feed files that can be processed concurrently (Default = 1) |
|         | last\_sequence\_number | int      | Yes      | Used when tracking feed file names with sequence numbers and the sequence in which a feed is run is important |
| FK      | from\_data\_source\_id | varchar(20) | No       | Defines the source the data is coming from |
| FK      | to\_data\_source\_id | varchar(20) | No       | Defines the source the data is going to|
| FK      | feed\_type\_id | varchar(25) | No       | Defines the type of feed to process |
| FK      | feed\_protocol\_id | varchar(10) | No       | Defines the feed protocol (request/response) |
| FK      | feed\_direction\_id | varchar(10) | No       | Defines the direction of the feed file (incoming/outgoing).  Determines the feed directories that will be created |
| FK      | feed\_group\_id | varchar(60) | Yes      | Defines the group the feed is associated with |