# t\_feed\_group #

The t\_feed\_group table defines groups that feed definitions can attach to in order to share common behavior as it concerns allowing concurrent runs, allowing failed state runs and check pointing.  Feed groups are used when defining the group for a given row in t\_feed\_file.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_group\_id | varchar(60) | No       | Uniquely defines a feed group |
|         | allow\_concurrent\_runs | tinyint(1) | No       | Defines if more than one feed matching the given feed\_group\_id can be run concurrently. |
|         | allow\_failed\_state\_runs | tinyint(1) | No       | Defines if more than feed matching the given feed\_id can be run with at least one other feed  matching the feed\_id is in a failed state. |
|         | collect\_phase\_stats | tinyint(1) | No       | Determines is OSFE will collect phase statistics during a feed run. |