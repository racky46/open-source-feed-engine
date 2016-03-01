# t\_feed\_phase\_stats #

The t\_feed\_phase\_stats table contains statistics on each phase of a given feed file life cycle.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_phase\_stats\_id | int      | No       | Uniquely defines a feed phase stats row. Automatically incremented |
|         | phase\_id | varchar(60) | No       | Uniquely identifies the phase within the set of phases |
|         | avg\_processing\_time | real     | Yes      | Contains the average processing time of the given phase  |
|         | total\_time\_in\_ms | int      | Yes      | Contains the total processing time in milliseconds of the given phase  |
|         | iteration\_count | int      | Yes      | Contains the total number of times the given phase was called |
| FK      | feed\_file\_id | int      | No       | Reference to associated feed file |