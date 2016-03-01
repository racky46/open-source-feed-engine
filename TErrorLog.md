# t\_error\_log #

The t\_error\_log table should be used to store feed warnings and errors that do not fail a feed but need to be tracked for research and statistical purposes.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | error\_log\_id | int      | No       | Uniquely defines an error (Automatically incremented) |
|         | time\_stamp | timestamp | No       | Time the error\_log was entered |
|         | error\_type\_id | varchar(30) | Yes      | May be used to define specific type of error  It's a good idea to populate the column in order to keep good statistics |
|         | message  | varchar(5000) | Yes      | May be used to specify a error\_log message |
| FK      | feed\_job\_id | int      | Yes      | May be associate the error to a given feed job if the error being logged takes place during the processing of a feed.  It is also possible to enter an error log prior to starting a feed job. |
