# t\_feed\_direction #

The t\_feed\_direction table defines one of two directions, inbound or outbound.  A feed direction is used to define the direction of a feed in t\_feed. OSFE uses the direction information to determine the directory structure.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_direction\_id | varchar(10) | No       | Uniquely defines the feed direction |
|         | description | varchar(250) | No       | Description of the feed direction |


# Definitions #

| **feed\_direction\_id** | **description** |
|:------------------------|:----------------|
| inbound                 | Defines that the feed file is coming in from the partner |
| outbound                | Defines that the feed file is being sent to the partner |