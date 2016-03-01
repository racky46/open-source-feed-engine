# t\_feed\_role #

The t\_feed\_role table defines user roles which determine the level of access of a given user.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_role\_id | int      | No       | Uniquely defines the role id |
|         | role\_name | varcher(30) | No       | Uniquely names the role |
|         | description | varchar(250) | No       | Description the level of access |


# Definitions #

| feed\_role\_id | **feed\_direction\_id** | **description** |
|:---------------|:------------------------|:----------------|
| 500            | Administrator           | Ability to do everything |
| 400            | Feed Manager            | Ability to manage the processing of feeds |
| 300            | Data Manager            | Ability to manage user managed tables |
| 200            | User                    | Ability to query data and run reports |