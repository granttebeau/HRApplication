package users;

import java.util.List;

/**
 * An interface for a User of a Human Resources System.
 */
public interface IUser {
    /**
     * Checks that the user credentials are correct.
     * @param id user id
     * @param password user's password
     * @return if the credentials are correct.
     */
    public boolean login(String id, String password);

    /**
     * Gets the user's id.
     * @return the user's id
     */
    public String getUserID();

    /**
     * Checks whether the user is an Admin.
     * @return whether the user is an Admin
     */
    public boolean isAdmin();

    /**
     * Checks whether the user is an HREmployee.
     * @return whether the user is an HREmployee
     */
    public boolean isHR();

    /**
     * Checks whether the user is a Manager.
     * @return whether the user is a Manager
     */
    public boolean isManager();

    /**
     * Sets the user's manager.
     * @param admin checking to see whether the user making the change has the right credentials
     * @param manager the given manager
     */
    public void addManager(IUser admin, String manager);

    /**
     * Adds an employee to the user. Throws an exception if the user is not a manager.
     * @param admin checking to see whether the user making the change has the right credentials
     * @param employee the given employee to be added
     */
    public void addEmployee(IUser admin, IUser employee);

    /**
     * Views the manager of a User.
     * @param admin checking to see whether the user requesting the info has the right credentials
     * @return the manager's id
     */
    public String viewManager(IUser admin);

    /**
     * Removes an employee from the user. Throws an exception if the user isn't a manager.
     * @param admin checking to see whether the user making the change has the right credentials
     * @param user the user to be removed
     */
    public void removeEmployee(IUser admin, IUser user);

    /**
     * Views the employees of a user. Throws an exception if the user isn't a manager.
     * @param admin checking to see whether the user requesting the info has the right credentials
     * @return a list of user ids
     */
    public List<String> viewEmployees(IUser admin);

    /**
     * Changes the manager of a user.
     * @param admin checking to see whether the user making the change has the right credentials
     * @param manager the given manager id
     */
    public void changeManager(IUser admin, String manager);

    /**
     * View the salary of a user.
     * @param user checking to see whether the user requesting the info has the right credentials
     * @return the salary of a user
     */
    public int viewSalary(IUser user);

    /**
     * Change the salary of a user to a given amount.
     * @param user checking to see whether the user making the change has the right credentials
     * @param salary the new salary
     */
    public void editSalary(IUser user, int salary);

    /**
     * View the past salaries of a user.
     * @param user checking to see whether the user requesting the info has the right credentials
     * @return a list of past salaries
     */
    public List<Integer> viewPastSalaries(IUser user);

    /**
     * View the vacation balance of a user.
     * @param user checking to see whether the user requesting the info has the right credentials
     * @return the vacation balance of a user
     */
    public int viewVacationBalance(IUser user);

    /**
     * Change the vacation balance of a user by a given amount.
     * @param user checking to see whether the user making the change has the right credentials
     * @param days the increment to change by
     */
    public void changeVacationBalance(IUser user, int days);

    /**
     * View the annual bonus of a user.
     * @param user checking to see whether the user requesting the info has the right credentials
     * @return the annual bonus of a user
     */
    public int viewAnnualBonus(IUser user);

    /**
     * Change the annual bonus of a user by a given amount.
     * @param user checking to see whether the user making the change has the right credentials
     * @param value the increment to change by
     */
    public void changeAnnualBonus(IUser user, int value);
}
