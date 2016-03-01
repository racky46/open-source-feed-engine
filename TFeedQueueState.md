# t\_feed\_queue\_state #

The t\_feed\_queue\_state table defines the finite set of feed queue states.  Feed queue states are used when defining the state of a feedFile associated to a queue and managed by a feed monitor.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_queue\_state\_id | varchar(10) | No       | Uniquely defines the feed queue state |
|         | description | varchar(250) | No       | Description of the feed queue state |

# Definitions #

| **feed\_queue\_state\_id** | **description** |
|:---------------------------|:----------------|
| waiting                    | The initial state.  The feed file is waiting to be processed |
| processing                 | The feed file is current being processed |
| completed                  | The feed processing life cycle completed successfully |
| failed                     | The feed processing life cycle did not complete successfully |