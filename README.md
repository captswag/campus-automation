Campus Automation
=================
Campus Automation is a GUI based application, which can be used to store student records. It is entirely written using Java, database used is MySql. Student records include 
- Firstname
- Lastname
- Admission number
- Mobile number
- Sex
- Branch
- Sex
- Date of Birth

![Home](/screenshots/home.png)

The application also provides option to save marks of students in four internal examinations.

![Marks](/screenshots/marks.png)

Campus Automation uses JTable to represent student data in a tabular form. Editing and deleting of records is done directly from the table by selecting one row, and then pressing E or DEL on keyboard. 

![Delete](/screenshots/delete.png)

Database needed to be created manually. Database name is "campusautomation", username is "username" and password is "password".

Table student description

| Field           | Type        | Null | Key | Default | Extra |
|-----------------|-------------|------|-----|---------|-------|
| Firstname       | varchar(20) | NO   |     | NULL    |       |
| Lastname        | varchar(20) | NO   |     | NULL    |       |
| Admissionnumber | int(4)      | NO   | PRI | NULL    |       |
| Mobile          | varchar(10) | NO   |     | NULL    |       |
| Sex             | varchar(7)  | NO   |     | NULL    |       |
| Branch          | varchar(3)  | NO   |     | NULL    |       |
| Sem             | int(1)      | NO   |     | NULL    |       |
| dob             | date        | NO   |     | NULL    |       |

Table Marks1 description

| Field           | Type        | Null | Key | Default | Extra |
|-----------------|-------------|------|-----|---------|-------|
| k601            | int(3)      | YES  |     | NULL    |       |
| k602            | int(3)      | YES  |     | NULL    |       |
| k603            | int(3)      | YES  |     | NULL    |       |
| k604            | int(3)      | YES  |     | NULL    |       |
| k605            | int(3)      | YES  |     | NULL    |       |
| k606            | int(3)      | YES  |     | NULL    |       |
| k607            | int(3)      | YES  |     | NULL    |       |
| k608            | int(3)      | YES  |     | NULL    |       |
| Admissionnumber | int(4)      | NO   | PRI | NULL    |       |

Similar tables for Marks2, Marks3 & Marks4
