# t\_feed\_data\_source #

The t\_feed\_data\_source table defines unique data source. Data sources are used in t\_feed to define where data originates from and where data is being sent to.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_data\_source\_id | varchar(10) | No       | Uniquely defines the data source |
|         | description | varchar(250) | No       | Description of the data source |

# Examples #

| **feed\_data\_source\_id** | **description** |
|:---------------------------|:----------------|
| acme                       | Acme student information data source |
| qagen                      | Worlds #1 selling test generation software |