# t\_feed\_protocol #

The t\_feed\_protocol table defines the two protocols, request & response.  A feed protocol is used to define the protocol of a feed in t\_feed.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_protocol\_id | varchar(10) | No       | Uniquely defines the feed protocol |
|         | description | varchar(250) | No       | Description of the feed protocol |


# Definitions #

| **feed\_protcol\_id** | **description** |
|:----------------------|:----------------|
| request               | Defines the feed as a request to be processed |
| response              | Defines a response feed to a previously processed request feed |