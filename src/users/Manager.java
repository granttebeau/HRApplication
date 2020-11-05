package users;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Manager. Contains a list of employees
 */
public class Manager extends AbstractUser {

    private List<IUser> employees;

    public Manager(String id, String password, int salary, int vacationBalance, int annualBonus,
                   String manager, boolean isAdmin) {
        super(id, password, salary, vacationBalance, annualBonus, isAdmin);
        this.employees = new ArrayList<IUser>();
        if (manager.equals("")) {
            this.manager = null;
        } else {
            this.manager = manager;
        }
    }

    @Override
    public boolean isManager() {
        return true;
    }

    @Override
    public void addEmployee(IUser admin, IUser employee) {
        if (admin.isAdmin()) {
            this.employees.add(employee);
            return;
        }
        throw new IllegalArgumentException("This user can't add an employee");
    }

    @Override
    public void removeEmployee(IUser admin, IUser user) {
        if (admin.isAdmin()) {
            if (employees.contains(user)) {
                employees.remove(user);
                return;
            }
            throw new IllegalArgumentException("Employee doesn't report to manager");
        }
        else {
            throw new IllegalArgumentException("Not admin");
        }
    }

    @Override
    public List<String> viewEmployees(IUser user) {
        if (acceptableView(user)) {
            List<String> employeeList = new ArrayList<String>();
            for (IUser employee : employees) {
                employeeList.add(employee.getUserID());
            }
            return employeeList;
        }
        throw new IllegalArgumentException("Can't view this user's information");
    }
}
