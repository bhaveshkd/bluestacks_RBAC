package com.bluestacks;

import com.bluestacks.resources.IResource;
import com.bluestacks.resources.Resource;
import com.bluestacks.roles.IRole;
import com.bluestacks.roles.Role;
import com.bluestacks.roles.actions.Action;
import com.bluestacks.roles.actions.IAction;
import com.bluestacks.users.IUser;
import com.bluestacks.users.User;
import com.bluestacks.utils.UserPasswords;
import com.bluestacks.utils.UtilFunctions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

//This is the manager class for the project, creates init data
public class RBAManager {

    private IUser currentUser;
    //maps having all the data, basically this is to be stored in a DB
    //but used in-memory space here, so using maps
    private HashMap<String, IUser> allUsers;
    private HashMap<String, IRole> allRoles;
    private HashMap<String, IAction> allActions;
    private HashMap<String, IResource> allResources;

    //this is for storing user passwords
    //this is a separate class/package to depict an external password service provider to be used
    private UserPasswords userPasswords;

    private UtilFunctions utilFunctions;

    private Scanner scanner = new Scanner(System.in);

    public RBAManager(){
        //initialising the data
        this.userPasswords = new UserPasswords();
        this.utilFunctions = new UtilFunctions();
        this.allActions = new HashMap<>();
        this.allRoles = new HashMap<>();
        this.allUsers = new HashMap<>();
        this.allResources = new HashMap<>();
        initActions();
        initRoles();
        initUsers();
        initResources();

        IRole adminRole = allRoles.get("admin");
        adminRole.addAction(allActions.get("admin"));

        IUser adminUser = allUsers.get("admin");
        adminUser.addRole(allRoles.get("admin"));

        IResource defaultResource = allResources.get("default");
        defaultResource.addRole(allRoles.get("admin"));

        currentUser = adminUser;
    }

    //some init functions
    private void initActions(){
        //admin action can do any action
        Action adminAction = new Action("admin");
        Action readAction = new Action("read");
        Action writeAction = new Action("write");
        Action deleteAction = new Action("delete");
        this.allActions.put(adminAction.getAccessLevel(), adminAction);
        this.allActions.put(readAction.getAccessLevel(), readAction);
        this.allActions.put(writeAction.getAccessLevel(), writeAction);
        this.allActions.put(deleteAction.getAccessLevel(), deleteAction);

    }

    private void initRoles(){
        Role adminRole = new Role("admin");
        this.allRoles.put(adminRole.getName(), adminRole);
    }

    private void initUsers(){
        User adminUser = new User("admin");
        this.allUsers.put(adminUser.getName(), adminUser);
        userPasswords.addUserPassword("admin", "admin".hashCode());
    }

    private void initResources(){
        //creating a default resource for data
        //this is just for namesake, no use apart from some testing
        Resource defaultResource = new Resource("default");
        this.allResources.put(defaultResource.getName(), defaultResource);
    }

    private void editAction(){
        boolean isAdmin = currentUser.isAdmin();
        if (!isAdmin) System.out.println("Admin access not available!!!");
        else{
            System.out.println("Enter the option number");
            System.out.println("1 add Action");
            System.out.println("2 remove Action");
            System.out.println("3 change Action properties");
            System.out.println("4 View all Actions");
            System.out.println("5 Main Menu");

            String option = scanner.nextLine();
            switch (option) {
                case "1": {
                    System.out.println("Enter action name : ");
                    option = scanner.nextLine();
                    IAction optionAction = allActions.get(option);
                    if (optionAction != null) {
                        System.out.println("Action already available!!!");
                    } else {
                        allActions.put(option, new Action(option));
                        System.out.println("New action added " + option);
                    }
                    break;
                }
                case "2": {
                    System.out.println("Enter action name : ");
                    option = scanner.nextLine();
                    IAction optionAction = allActions.get(option);
                    if (optionAction == null) {
                        System.out.println("Action not available!!!");
                    } else if (option.equals("admin")) {
                        System.out.println("admin can not be altered");
                    } else {
                        for (IRole role : allRoles.values()) {
                            role.removeAction(optionAction);
                        }
                        allActions.remove(option);
                    }
                    break;
                }
                case "3": {
                    System.out.println("Enter action name : ");
                    option = scanner.nextLine();
                    IAction optionAction = allActions.get(option);
                    if (optionAction == null) {
                        System.out.println("Action not available!!!");
                    } else if (option.equals("admin")) {
                        System.out.println("admin can not be altered");
                    } else {
                        System.out.println("What is new accessLevel : ");
                        option = scanner.nextLine();
                        optionAction.setAccessLevel(option);
                        System.out.println("New accessLevel set for action " + option);
                    }
                    break;
                }
                case "4": {
                    System.out.println("All the actions available currently : ");
                    for (String key : allActions.keySet()) {
                        System.out.println(key + " : " + allActions.get(key).getAccessLevelMessage());
                    }
                    break;
                }
                case "5": {
                    break;
                }
                default:
                    System.out.println("Incorrect option selected");
                    break;
            }
        }
    }

    private void editRole(){
        boolean isAdmin = currentUser.isAdmin();
        if (!isAdmin) System.out.println("Admin access not available!!!");
        else{
            System.out.println("Enter the option number");
            System.out.println("1 add role");
            System.out.println("2 remove role");
            System.out.println("3 change role properties");
            System.out.println("4 View all Roles");
            System.out.println("5 View all actions for a role");
            System.out.println("6 Main Menu");

            String option = scanner.nextLine();
            switch (option) {
                case "1": {
                    System.out.println("Enter role name : ");
                    option = scanner.nextLine();
                    IRole optionRole = allRoles.get(option);
                    if (optionRole != null) {
                        System.out.println("Role already available!!!");
                    } else {
                        allRoles.put(option, new Role(option));
                        System.out.println("New role added " + option);
                    }
                    break;
                }
                case "2": {
                    System.out.println("Enter role name : ");
                    option = scanner.nextLine();
                    IRole optionRole = allRoles.get(option);
                    if (optionRole == null) {
                        System.out.println("Role not available!!!");
                    } else if (option.equals("admin")) {
                        System.out.println("admin can not be altered");
                    } else {
                        for (IUser user : allUsers.values()) {
                            user.removeRole(optionRole);
                        }
                        for (IResource resource : allResources.values()) {
                            resource.removeRole(optionRole);
                        }
                        allRoles.remove(option);
                    }
                    break;
                }
                case "3": {
                    System.out.println("Enter role name : ");
                    option = scanner.nextLine();
                    IRole optionRole = allRoles.get(option);
                    if (optionRole == null) {
                        System.out.println("Role not available!!!");
                    } else if (option.equals("admin")) {
                        System.out.println("admin can not be altered");
                    } else {
                        System.out.println("What to change : ");
                        System.out.println("1 Add action");
                        System.out.println("2 Remove action");
                        option = scanner.nextLine();
                        if (option.equals("1")) {
                            System.out.println("Enter action Name : ");
                            option = scanner.nextLine();
                            IAction action = allActions.get(option);
                            if (action == null) {
                                System.out.println("Action not available");
                            } else {
                                optionRole.addAction(action);
                                System.out.println("Action added");
                            }
                        } else if (option.equals("2")) {
                            System.out.println("Enter action Name : ");
                            option = scanner.nextLine();
                            IAction action = allActions.get(option);
                            if (action == null) {
                                System.out.println("Action not available");
                            } else {
                                optionRole.removeAction(action);
                                System.out.println("Action removed");
                            }
                        } else {
                            System.out.println("Incorrect option selected");
                        }
                    }
                    break;
                }
                case "4": {
                    System.out.println("All the roles available currently : ");
                    for (String key : allRoles.keySet()) {
                        System.out.println(key);
                    }
                    break;
                }
                case "5": {
                    System.out.println("Enter role name to view it's actions");
                    option = scanner.nextLine();
                    IRole optionRole = allRoles.get(option);
                    if (optionRole != null){
                        System.out.println("All actions for role : " + option);
                        for (IAction action : optionRole.getActionSet()){
                            System.out.println("accessLevel : " + action.getAccessLevel());
                            System.out.println("accessLevelMessage : " + action.getAccessLevelMessage() + "\n");
                        }
                    }
                    else {
                        System.out.println("Role not present");
                    }
                    break;
                }
                case "6": {
                    break;
                }
                default:
                    System.out.println("Incorrect option selected");
                    break;
            }
        }
    }

    private void editUser(){
        boolean isAdmin = currentUser.isAdmin();
        if (!isAdmin) System.out.println("Admin access not available!!!");
        else{

            System.out.println("Enter the option number");
            System.out.println("1 add user");
            System.out.println("2 remove user");
            System.out.println("3 change user properties");
            System.out.println("4 view all Users");
            System.out.println("5 view all roles for a User");
            System.out.println("6 Main Menu");

            String option = scanner.nextLine();
            switch (option) {
                case "1": {
                    System.out.println("Enter User Name : ");
                    option = scanner.nextLine();
                    IUser user = allUsers.get(option);
                    if (user != null) {
                        System.out.println("User already present");
                    } else {
                        System.out.println("Enter password for user : ");
                        String password = scanner.nextLine();
                        user = new User(option);
                        userPasswords.addUserPassword(option, password.hashCode());
                        allUsers.put(option, user);
                        System.out.println("New User added");
                    }
                    break;
                }
                case "2": {
                    System.out.println("Enter User Name : ");
                    option = scanner.nextLine();
                    IUser user = allUsers.get(option);
                    if (user == null) {
                        System.out.println("User not present");
                    } else if (option.equals("admin")) {
                        System.out.println("admin cannot be altered");
                    } else {
                        allUsers.remove(option);
                        System.out.println("User removed");
                    }
                    break;
                }
                case "3": {
                    System.out.println("Enter user name : ");
                    option = scanner.nextLine();
                    IUser optionUser = allUsers.get(option);
                    if (optionUser == null) {
                        System.out.println("User not available!!!");
                    } else if (option.equals("admin")) {
                        System.out.println("admin can not be altered");
                    } else {
                        System.out.println("What to change : ");
                        System.out.println("1 Add Role");
                        System.out.println("2 Remove Role");
                        option = scanner.nextLine();
                        if (option.equals("1")) {
                            System.out.println("Enter role Name : ");
                            option = scanner.nextLine();
                            IRole role = allRoles.get(option);
                            if (role == null) {
                                System.out.println("Role not available");
                            } else {
                                optionUser.addRole(role);
                                System.out.println("Role added");
                            }
                        } else if (option.equals("2")) {
                            System.out.println("Enter role Name : ");
                            option = scanner.nextLine();
                            IRole role = allRoles.get(option);
                            if (role == null) {
                                System.out.println("Role not available");
                            } else {
                                optionUser.removeRole(role);
                                System.out.println("Role removed");
                            }
                        } else {
                            System.out.println("Incorrect option selected");
                        }
                    }
                    break;
                }
                case "4": {
                    System.out.println("All the Users available currently : ");
                    for (String key : allUsers.keySet()) {
                        System.out.println(key);
                    }
                    break;
                }
                case "5": {
                    System.out.println("Enter User Name to check it's roles : ");
                    option = scanner.nextLine();
                    IUser optionUser = allUsers.get(option);
                    if (optionUser != null){
                        System.out.println("All the roles for user " + option + " :");
                        for (IRole role : optionUser.getRoleSet()){
                            System.out.println(role.getName());
                        }
                    }
                    else {
                        System.out.println("User not present");
                    }
                    break;
                }
                case "6": {
                    break;
                }
                default:
                    System.out.println("Incorrect option selected");
                    break;
            }
        }
    }

    private void editResource(){
        boolean isAdmin = currentUser.isAdmin();
        if (!isAdmin) System.out.println("Admin access not available!!!");
        else{
            System.out.println("Enter the option number");
            System.out.println("1 add resource");
            System.out.println("2 remove resource");
            System.out.println("3 change resource properties");
            System.out.println("4 view all Resources");
            System.out.println("5 view all roles for a User");
            System.out.println("6 Main Menu");

            String option = scanner.nextLine();
            switch (option) {
                case "1": {
                    System.out.println("Enter Resource Name : ");
                    option = scanner.nextLine();
                    IResource resource = allResources.get(option);
                    if (resource != null) {
                        System.out.println("Resource already present");
                    } else {
                        resource = new Resource(option);
                        allResources.put(option, resource);
                        System.out.println("New Resource added");
                    }
                    break;
                }
                case "2": {
                    System.out.println("Enter User Name : ");
                    option = scanner.nextLine();
                    IResource resource = allResources.get(option);
                    if (resource == null) {
                        System.out.println("Resource not present");
                    } else if (option.equals("default")) {
                        System.out.println("default cannot be altered");
                    } else {
                        allResources.remove(option);
                        System.out.println("Resource removed");
                    }
                    break;
                }
                case "3": {
                    System.out.println("Enter resource name : ");
                    option = scanner.nextLine();
                    IResource optionResource = allResources.get(option);
                    if (optionResource == null) {
                        System.out.println("resource not available!!!");
                    } else if (option.equals("default")) {
                        System.out.println("default can not be altered");
                    } else {
                        System.out.println("What to change : ");
                        System.out.println("1 Add Role");
                        System.out.println("2 Remove Role");
                        option = scanner.nextLine();
                        if (option.equals("1")) {
                            System.out.println("Enter role Name : ");
                            option = scanner.nextLine();
                            IRole role = allRoles.get(option);
                            if (role == null) {
                                System.out.println("Role not available");
                            } else {
                                optionResource.addRole(role);
                                System.out.println("Role added");
                            }
                        } else if (option.equals("2")) {
                            System.out.println("Enter role Name : ");
                            option = scanner.nextLine();
                            IRole role = allRoles.get(option);
                            if (role == null) {
                                System.out.println("Role not available");
                            } else {
                                optionResource.removeRole(role);
                                System.out.println("Role removed");
                            }
                        } else {
                            System.out.println("Incorrect option selected");
                        }
                    }
                    break;
                }
                case "4": {
                    System.out.println("All the Resources available currently : ");
                    for (String key : allResources.keySet()) {
                        System.out.println(key);
                    }
                    break;
                }
                case "5": {
                    System.out.println("Enter Resource Name to check it's roles : ");
                    option = scanner.nextLine();
                    IResource optionResource = allResources.get(option);
                    if (optionResource != null){
                        System.out.println("All the roles for resource " + option + " :");
                        for (IRole role : optionResource.getRoleSet()){
                            System.out.println(role.getName());
                        }
                    }
                    else {
                        System.out.println("Resource not present");
                    }
                    break;
                }
                case "6": {
                    break;
                }
                default:
                    System.out.println("Incorrect option selected");
                    break;
            }
        }
    }

    private void changeUser(){
        //changes the cuurent user logged in
        System.out.println("Enter new user name : ");
        String option = scanner.nextLine();
        IUser user = allUsers.get(option);
        if (user != null){
            //emulating the use of external authentication service
            if (userPasswords.verifyUser(option)) {
                currentUser = user;
            }
            else {
                System.out.println("Incorrect Password");
            }
        }
        else{
            System.out.println("User not present");
        }
    }

    private void checkUserResourceActionStatus(){
        //prints the available actions between a resource and user
        boolean isAdmin = currentUser.isAdmin();
        IUser userOption;
        if (isAdmin){
            System.out.println("Current user is admin. So any user's actions can be checked!");
            System.out.println("Enter the name of User you want to check status : ");
            String option = scanner.nextLine();
            userOption = allUsers.get(option);
            if (userOption == null) {
                System.out.println("Resource not present!!!");
                return;
            }
        }
        else{
            System.out.println("Current user is not admin. So only it's actions can be checked!");
            userOption = currentUser;
        }
        System.out.println("Enter resource name to check status with current user : ");
        String option = scanner.nextLine();
        IResource resourceOption = allResources.get(option);
        if (resourceOption == null) {
            System.out.println("Resource not present!!!");
        } else if (userOption.isAdmin()) {
            System.out.println("Current User has admin access, can do all the actions!");
        } else {
            Set<IRole> resourceRoles = resourceOption.getRoleSet();
            Set<IRole> userRoles = userOption.getRoleSet();
            Set<IRole> intersectionSet; // use the copy constructor
            intersectionSet = new HashSet<>(resourceRoles);
            intersectionSet.retainAll(userRoles); //intersection of two sets

            //Since many roles can have common actions, this set stores them only once
            HashSet<IAction> actionsAvailable = new HashSet<>();
            for (IRole role : intersectionSet) {
                actionsAvailable.addAll(role.getActionSet());
            }
            System.out.println("Actions available for this user on this resource are : ");
            for (IAction action : actionsAvailable) {
                System.out.println("AccessLevel : " + action.getAccessLevel());
                System.out.println("AccessMessage : " + action.getAccessLevelMessage() + "\n");
            }
        }
    }

    public boolean entryFunction(){
        //entry function for the whole project, called from main
        System.out.println("Hi! currentUser is : " + currentUser.getName());
        System.out.println("Enter the option number to select : ");
        System.out.println("1 Action Functionality");
        System.out.println("2 Role Functionality");
        System.out.println("3 User Functionality");
        System.out.println("4 Resource Functionality");
        System.out.println("5 Change User");
        System.out.println("6 Check User-Resource-Action status");
        System.out.println("7 Exit");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                utilFunctions.clearConsole();
                editAction();
                System.out.println("Enter any value to continue : ");
                scanner.nextLine();
                utilFunctions.clearConsole();
                return true;
            case "2":
                utilFunctions.clearConsole();
                editRole();
                System.out.println("Enter any value to continue : ");
                scanner.nextLine();
                utilFunctions.clearConsole();
                return true;
            case "3":
                utilFunctions.clearConsole();
                editUser();
                System.out.println("Enter any value to continue : ");
                scanner.nextLine();
                utilFunctions.clearConsole();
                return true;
            case "4":
                utilFunctions.clearConsole();
                editResource();
                System.out.println("Enter any value to continue : ");
                scanner.nextLine();
                utilFunctions.clearConsole();
                return true;
            case "5":
                utilFunctions.clearConsole();
                changeUser();
                System.out.println("Enter any value to continue : ");
                scanner.nextLine();
                utilFunctions.clearConsole();
                return true;
            case "6":
                utilFunctions.clearConsole();
                checkUserResourceActionStatus();
                System.out.println("Enter any value to continue : ");
                scanner.nextLine();
                utilFunctions.clearConsole();
                return true;
            case "7":
                utilFunctions.clearConsole();
                System.out.println("Exiting...");
                return false;
            default:
                utilFunctions.clearConsole();
                System.out.println("Incorrect option selected");
                System.out.println("\n");
                return true;
        }
    }

}
