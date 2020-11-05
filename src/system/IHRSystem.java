package system;

import users.IUser;

import java.util.List;

/**
 * An interface for a Human Resources System.
 */
public interface IHRSystem {
    /**
     * Login with the user credentials
     * @param id user id
     * @param password user's password
     */
    public void login(String id, String password);

    /**
     * Logs the current user out
     */
    public void logout();

    /**
     * Adds a given employee to the system. The current user can only do this if they're an admin.
     * @param employee the given employee
     */
    public void addEmployee(IUser employee);

    /**
     * Checks if there's an existing ID already in the system for the given user.
     * @param user the given user
     * @return whether there's an existing ID already that matches the given user's
     */
    public boolean existingID(IUser user);

    /**
     * Given an ID, this returns an employee. The current user can only do this if they have the right credentials.
     * @param id the id of the requested user
     * @return the requested user
     */
    public IUser getEmployeeByID(String id);

    /**
     * View the salary of a given user by id. The current user can only do this if they have the right credentials.
     * @param userID the given user id
     * @return the salary of the given user
     */
    public int viewSalary(String userID);

    /**
     * Change the salary of a given user by id to the given amount.
     * The current user can only do this if they have the right credentials.
     * @param userID the given user id
     * @param amount the given amount
     */
    public void changeSalary(String userID, int amount);

    /**
     * View the past salaries of a given user by id.
     * The current user can only do this if they have the right credentials.
     * @param userID the given user id
     * @return the past salaries of the given user
     */
    public List<Integer> viewPastSalaries(String userID);

    /**
     * View the vacation balance of a given user by id.
     * The current user can only do this if they have the right credentials.
     * @param userID the given user id
     * @return the salary of the given user
     */
    public int viewVacationBalance(String userID);

    /**
     * Change the vacation balance of a given user by id by the given amount.
     * The current user can only do this if they have the right credentials.
     * @param userID the given user id
     * @param increment the given to change it by
     */
    public void changeVacationBalance(String userID, int increment);

    /**
     * View the annual bonus of a given user by id.
     * The current user can only do this if they have the right credentials.
     * @param userID the given user id
     * @return the annual bonus of the given user
     */
    public int viewAnnualBonus(String userID);

    /**
     * Change the annual bonus of a given user by id by the given amount.
     * The current user can only do this if they have the right credentials.
     * @param userID the given user id
     * @param increment the given to change it by
     */
    public void changeAnnualBonus(String userID, int increment);

    /**
     * View the manager of a given user by id.
     * The current user can only do this if they have the right credentials, and only if the given user has a manager.
     * @param userID the given user id
     * @return the id of the user's manager
     */
    public String viewManager(String userID);

    /**
     * Changes the manager of the given employee. Updates all relevant fields.
     * The current user can only do this if they have the right credentials.
     * @param employee the id of the employee to change to
     * @param manager the manager to change to
     */
    public void changeManager(String employee, String manager);

    /**
     * View the employees of a given user by id.
     * The current user can only do this if they have the right credentials, and only if the given user is a manager.
     * @param userID the given user id
     * @return the employees of a given manager
     */
    public List<String> viewEmployees(String userID);

    /**
     * Removes an employee of a given user by id from the system.
     * The current user can only do this if they're an admin
     * @param employee the given employee
     */
    public void removeEmployee(String employee);
}
