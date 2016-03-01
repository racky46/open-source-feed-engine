# t\_feed\_checkpoint #

The t\_feed\_checkpoint table contains the base information for a check point.  A checkpoint is directly associated to a row in t\_feed\_file.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_checkpoint\_id | int      | No       | Uniquely defines a row (Automatically incremented) |
|         | phase\_id | varchar(60) | No       | Uniquely defines the checkpoint phase within the given set |
|         | current\_file\_index | int      | No       | Specifies the position in feed file of the most recent checkpoint. |
| FK      | feed\_file\_id | int      | No       | Reference to associated feed file. |
