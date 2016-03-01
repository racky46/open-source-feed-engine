# t\_feed\_file #

t\_feed\_file
The t\_feed\_file table contains the information the feed engine will use to manage the life cycle of a given feed file.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_file\_id | int      | No       | Uniquely defines a feed file row. Automatically incremented |
|         | feed\_file\_date | date     | No       | Defines the time the feed file was created |
|         | feed\_file\_time | time     | No       | Defines the time the feed file was created |
|         | records\_processed | int      | No       | Number of rows successfully processed |
|         | records\_rejected | int      | No       | Number of rows rejected during feed processing |
|         | feed\_file\_name | varchar(255) | No       | Name of the feed file being processed  **(File name must be unique)** |
|         | feed\_document | varchar(255) | No       | Name of the feed document used to configure the feed engine to process the feed |
| FK      | feed\_id | varchar(60) | No       | Reference to feed definition in t\_feed |
| FK      | feed\_file\_state\_id | varchar(10) | No       | Specifies the current state the feed file. |