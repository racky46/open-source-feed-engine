# t\_feed\_job\_state #

The t\_feed\_job\_state table defines the finite set of feed job states.  Feed job states are used when defining the state of a feed job associated with a given feed file.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_job\_state\_id | varchar(10) | No       | Uniquely defines the feed job state |
|         | description | varchar(250) | No       | Description of the feed job state |

# Definitions #

| **feed\_job\_state\_id** | **description** |
|:-------------------------|:----------------|
| active                   | The active state is the initial stat of a feed job |
| completed                | The feed processing life cycle completed successfully |
| failed                   | The feed processing life cycle did not complete successfully |
| resolved                 | A feed job is in a failed state and has been resolved |