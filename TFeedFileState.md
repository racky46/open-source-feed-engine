# t\_feed\_file\_state #

The t\_feed\_file\_state table defines the finite set of feed file states and contains the definitions that determine the different states of a given row in t\_feed\_file.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_file\_state\_id | varchar(10) | No       | Uniquely defines the feed file state |
|         | description | varchar(250) | No       | Description of the feed file state |

# Definitions #

| **feed\_file\_state\_id** | **description** |
|:--------------------------|:----------------|
| completed                 | The feed file life cycle was completed successfully |
| failed                    | The feed file life cycle was not completed successfully |
| processing                | The feed file is currently being processed |
| rejected                  | A feed that has failed cannot be repaired and is permanently rejected |
| retry                     | The feed file is waiting to be reprocessed because of a previous failure |