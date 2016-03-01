# t\_feed\_job #

The t\_feed\_job table contains information the feed engine will use to manage a given feed job associated with a given feed file during the feed processing life cycle.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_file\_id | int      | No       | Uniquely defines a feed file row. Automatically incremented |
|         | processing\_start | timestamp | No       | Specifies date job was created |
|         | processing\_end | timestamp | Yes      | Specifies time job was created |
|         | failed\_row\_number | int      | Yes      | Specifies the row number where the feed failed |
|         | failure\_code | varchar(40) | Yes      | Specifies a failure code id |
|         | failure\_reason | varchar(5000) | No       | Specifies an explanation for the failure |
| FK      | feed\_file\_id | int      | No       | Reference to associated feed file |
| FK      | feed\_job\_state\_id | varchar(10) | No       | Specifies the current state of the feed job |