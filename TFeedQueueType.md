# t\_feed\_queue\_type #

The t\_feed\_queue\_type table defines unique queues that are managed by one or more feed monitors. Feed definitions in t\_feed can reference a given feed\_queue.  A given feed monitor will only manage a given feed on the it references.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_type\_id | varchar(25) | No       | Uniquely defines a feed type |
|         | max\_concurrent\_runs | int      | No       | Defines the maximum number of feeds that can run concurrently from this queue |
|         | description | varchar(250) | No       | Description the feed queue type |

# Examples #

| **feed\_queue\_type\_id** | **description** |
|:--------------------------|:----------------|
| singular\_queue           | feeds that cannot not be run concurrently  |
| small\_multi\_queue       | feeds under 500,000 rows that can run concurrently |
| huge\_multi\_queue        |feeds over 500,000 rows that run concurrently |