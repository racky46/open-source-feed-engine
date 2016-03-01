# t\_feed\_queue #

The t\_feed\_queue is use by the monitor classes that manage the number of OSFE engines and feeds that are being processed simultaneously.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_queue\_id | int      | No       | Uniquely defines a row (Automatically incremented) |
|         | queue\_id | varchar(60) | No       | Specifies which  queue will manage the feed |
|         | monitor\_id | int      | No       | Specifies which monitor is managing the queue |
|         | entry\_time | timestamp| No       | Specifies the time the file was added to the queue |
|         | feed\_file\_name | varchar(255) | No       | Specifies the name of the feed file |
| FK      | feed\_file\_id | int      | No       | Reference to associated feed file |
