# RBAC project
This project is creating a role based access controller emulation at a very basic command line level.

## Classes

| ClassType | Name | Description |
| ------ | ------ | ------ |
| Interface | Action | Interface having Actions functions |
| Class | Action | Interface having Actions functions |
| Interface | Role | Class having Roles functions |
| Class | Role | Interface having Roles functions |
| Interface | User | Interafce having Users functions |
| Class | User | Class having Users functions |
| Interface | Resource | Interface having Resource functions |
| Class | Resource | Class having Resource functions |
| Class | RBAManager | Entry Class having core functionality |
| Class | UtilFunctions | Class having a util function, use for adding extra util finctions |
| Class | UserPasswords | Class storing user password data, emulating a third party authentication service |

### Action
Package path : com.bluestacks.roles.actions.Action
Implements : IAction

| Member | Member/Return Type | Name | Description |
| ------ | ------ | ------ | ------ |
| variable | private String | name | stores name for Action |
| variable | private String | accessLevel | stores accessLevel for Action |
| function | public String | getAccessLevel | returns access level of action |
| function | public void | setAccessLevel | sets access level |
| function | public String | getAccessLevel | returns access level message |

### Role
Package path : com.bluestacks.roles.Role
Implements : IRole

| Member | Member/Return Type | Name | Description |
| ------ | ------ | ------ | ------ |
| variable | private String | name | stores name for Action |
| variable | private HashSet<String> | actionSet | stores all actions for this Role |
| constructor | public | Role(String name) | makes role and instantiate member variables this.name as name |
| function | public String | getName | returns name |
| function | public void | setName(String name) | sets name |
| function | public Set<IAction> | getActionSet | returns action set |
| function | public void | setActionSet(Set<IAction> set) | sets action set |
| function | public void | addAction(IActioon action) | adds action to action set |
| function | public void | removeAction(IActioon action) | removes action from action set |

### User
Package path : com.bluestacks.users.User
Implements : IUser

| Member | Member/Return Type | Name | Description |
| ------ | ------ | ------ | ------ |
| variable | private String | name | stores name for user |
| variable | private HashSet<String> | RoleSet | stores all roles for this User |
| constructor | public | User(String name) | makes user and instantiate member variables this.name as name |
| function | public String | getName | returns name |
| function | public void | setName(String name) | sets name |
| function | public Set<IRole> | getRoleSet | returns role set |
| function | public void | setRoleSet(Set<IRole> set) | sets role set |
| function | public void | addRole(IRole action) | adds role to role set |
| function | public void | removeRole(IRole action) | removes role from role set |

### Resource
Package path : com.bluestacks.resources.Resource
Implements : IResource

| Member | Member/Return Type | Name | Description |
| ------ | ------ | ------ | ------ |
| variable | private String | name | stores name for user |
| variable | private HashSet<String> | RoleSet | stores all roles for this Resource |
| constructor | public | User(String name) | makes resource and instantiate member variables this.name as name |
| function | public String | getName | returns name |
| function | public void | setName(String name) | sets name |
| function | public Set<IRole> | getRoleSet | returns role set |
| function | public void | setRoleSet(Set<IRole> set) | sets role set |
| function | public void | addRole(IRole action) | adds role to role set |
| function | public void | removeRole(IRole action) | removes role from role set |

### UtilPassword
Package path : com.bluestacks.utils.UserPasswords

| Member | Member/Return Type | Name | Description |
| ------ | ------ | ------ | ------ |
| variable | private HashMap<String, Integer> | userPasswordsData | stores user name and their hashed passwords |
| constructor | public | UserPasswords() | instantiate userPasswordData to set |
| function | public void | addUserPassword(String userName, int passwordHash) | adds user name and password to hashmap |
| function | public boolean | verifyUser(String userName) | verify user with correct password hashcode |

### UtilFunctions
Package path : com.bluestacks.utils.UtilFunctions

| Member | Member/Return Type | Name | Description |
| ------ | ------ | ------ | ------ |
| function | public void | clearConsole | clears console |

### RBAManager
Package path : com.bluestacks.RBAManager

| Member | Member/Return Type | Name | Description |
| ------ | ------ | ------ | ------ |
| variable | private Iuser | currentUser | monitors current logged in user |
| variable | private HashMap<String, IUser> | allUsers | stores all the users in a hash map |
| variable | private HashMap<String, Role> | allRoles |  stores all the Roles in a hash map |
| variable | private HashMap<String, IAction> | allActions |  stores all the Actions in a hash map |
| variable | private HashMap<String, IResource> | allResource |  stores all the Resources in a hash map |
| variable | private UserPasswords | userPasswords |  util class object for password database |
| variable | private UtilFunctions | utilFunctions |  util class object for clearing console |
| variable | private Scanner | scanner |  Scanner object for command line input |
| function | public constructor | RBAManager() | instantiates class with some default data |
| function | public void | initActions() | init function for default actions |
| function | public void | initRoles() | init function for default roles |
| function | public void | initUsers() | init function for default users |
| function | public void | initResources() | init function for default resources |
| function | public void | editAction() | function for editing actions in project |
| function | public void | editRole() | function for editing roles in project |
| function | public void | editUser() | function for editing users in project |
| function | public void | editResource() | function for editing resources in project |
| function | public void | changeUser() | changes current logged in user, sets new value to member variable currentUser |
| function | public void | checkUserResourceActionStatus() | prints the actions available for a user on a particular role. Gives actions for current log in user only if it doesn't has admin access, can check for each user if current user having admin access  |
| function | public boolean | entryFunction() | entry function of Main class. returns true to keep main function running as it returns true to isRunning variable value in main function, and returns false to isRunning and stops while loop in main function, closing the program.  |

###To Run from command line

follow these steps in command line

```sh
rm -rf build
rm -rf sources.txt
mkdir build
find -name "*.java" > source.txt
javac -d build @source.txt
java -cp .:build:**/*.class com.bluestacks.Main
```
