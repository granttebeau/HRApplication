## Ellevation Coding Assessment

#### Design explanation:

An HR System is represented by the IHRSystem interface, and 
my implementation is in the HRSystem class. The HR System contains
a list of employees and managers, as well as information about the
current user of the system. A user can login with their given credentials,
and based on their level, they can view/edit other users' information.
The permissions are the same as laid out in the project description.

A User is represented by the IUser interface. Each user has a 
userID, password, salary, vacation bonus, annual bonus, and a manager
(represented as a string). I implemented an AbstractUser class which
helped to put together common functionalities, and then this
abstract class is extended by the Manager, Employee, and 
HREmployee classes. Each instance will have a manager, with the exception of
certain managers who report to no one. 

#### Assumptions made
- A Manager is also an employee in the system, so they have a manager as well.
Hence the extension of the AbstractUser class. As previously mentioned,
some users might not have a manager. If this is the case, the manager 
field is represented as an empty string. 
- HREmployees are able to view the information of their managers. The reasoning
for this is because "have access to information about all Employees."
- "Administrative users" are also employees. In the constructor of each class, 
there's an isAdmin field. Therefore, any user can be given administrative access. 