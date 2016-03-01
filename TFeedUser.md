# t\_feed\_user #

The t\_feed\_user tables contains information for each user allowed access to the OSFE administration console.

| **Key** | **Name** | **Type** | **Null** | **Description** |
|:--------|:---------|:---------|:---------|:----------------|
| PK      | feed\_user\_id | varchar(60) | No       | Uniquely defines a user row (auto incremented) |
|         | username | varchar(30) | No       | Uniquely identifies a user |
|         | password | varchar(32) | No       |User password hash value |
|         | first\_name | varchar(30) | No       | Specifies the user's first name |
|         | last\_name | varchar(30) | No       | Specifies the user's last name |
|         | email\_address | varchar(50) | Yes      | Specifies the user's email address |
|         | date\_created | timestamp | No       | Specifies the time the user row was created |
|         | date\_modified | timestamp | No       | Specifies the time the user row was last modified |
|         | data\_last\_login | timestamp | No       | Specifies the last time the user logged in |
| FK      | feed\_role\_id | int      | No       | Defines the user access and capabilities |