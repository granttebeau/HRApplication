package users;

import java.util.ArrayList;
import java.util.List;

public class Manager extends AbstractUser {

    private List<IUser> employees;
    private String manager;

    public Manager(String id, String password, int salary, int vacationBalance, int annualBonus, String manager) {
        super(id, password, salary, vacationBalance, annualBonus);
        this.employees = new ArrayList<IUser>();
        if (manager.equals("")) {
            this.manager = null;
        } else {
            this.manager = manager;
        }
    }

    @Override
    public boolean isHR() {
        return false;
    }

    @Override
    public boolean isManager() {
        return true;
    }

    @Override
    public void addManager(IUser admin, String manager) {
        if (admin.isAdmin()) {
            this.manager = manager;
            return;
        }
        throw new IllegalArgumentException("This user can't add a manager");
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
    public String getManager(IUser admin) {
        if (admin.isAdmin() || admin.getUserID().equals(this.manager)) {
            return manager;
        }
        throw new IllegalArgumentException("User can't access information");
    }

    @Override
    public boolean isAdmin() {
        return false;
    }


    @Override
    public int viewSalary(IUser user) {
        if (user.equals(this) || user.getUserID().equals(manager) || user.isAdmin() || user.isHR()) {
            return salary;
        }
        throw new IllegalArgumentException("Can't view this user's information");
    }

    @Override
    public void editSalary(IUser user, int salary) {
        if (user.getUserID().equals(manager) || user.isAdmin()) {
            pastSalaries.add(this.salary);
            this.salary = salary;
            return;
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public List<Integer> viewPastSalaries(IUser user) {
        if (user.equals(this) || user.getUserID().equals(manager) || user.isAdmin() || user.isHR()) {
            return pastSalaries;
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public int viewVacationBalance(IUser user) {
        if (user.equals(this) || user.getUserID().equals(manager) || user.isAdmin() || user.isHR()) {
            return vacationBalance;
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public void changeVacationBalance(IUser user, int days) {
        if (vacationBalance + days < 0) {
            throw new IllegalArgumentException("Only " + vacationBalance + " vacation days remaining");
        }
        if (user.getUserID().equals(manager) || user.isAdmin()) {
            this.vacationBalance += days;
            return;
        }
        throw new IllegalArgumentException("Can't change this user's information.");
    }

    @Override
    public int viewAnnualBonus(IUser user) {
        if (user.equals(this) || user.getUserID().equals(manager) || user.isAdmin() || user.isHR()) {
            return annualBonus;
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public void changeAnnualBonus(IUser user, int value) {
        if (annualBonus + value < 0) {
            throw new IllegalArgumentException("Can't have a negative annual bonus");
        }
        if (user.getUserID().equals(manager) || user.isAdmin()) {
            this.annualBonus += value;
            return;
        }
        throw new IllegalArgumentException("Can't change this user's information.");
    }

}
