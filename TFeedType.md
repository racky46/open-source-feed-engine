# t\_feed\_type #

The t\_feed\_type table defines unique feed types.  A feed type is used when defining a feed in t\_feed.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_type\_id | varchar(25) | No       | Uniquely defines a feed type |
|         | description | varchar(250) | No       | Description the feed type |


# Examples #

| **feed\_type\_id** | **description** |
|:-------------------|:----------------|
| course             | feeds that contain course information |
| student            | feeds that contain student demographic information |
| test               |feeds that contain test scores |