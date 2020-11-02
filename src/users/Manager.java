package users;

import java.util.ArrayList;
import java.util.List;

public class Manager extends AbstractUser {

    private List<IUser> employees;
    private IUser manager;

    public Manager(String id, String password, int salary, int vacationBalance, int annualBonus, IUser manager) {
        super(id, password, salary, vacationBalance, annualBonus);
        this.employees = new ArrayList<IUser>();
    }

    @Override
    public boolean isHR() {
        return false;
    }

    @Override
    public void addManager(IUser manager) {
        this.manager = manager;
    }

    @Override
    public void addEmployee(IUser employee) {
        employees.add(employee);
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public int viewSalary(IUser user) {
        if (user.equals(manager) || user.isAdmin() || user.isHR()) {
            return salary;
        }
        else if (employees.contains(user)) {
            return user.viewSalary(this);
        }
        throw new IllegalArgumentException("Can't view this user's information");
    }

    @Override
    public void editSalary(IUser user, int salary) {
        if (user.equals(manager) || user.isAdmin()) {
            pastSalaries.add(this.salary);
            this.salary = salary;
        }
        else if (employees.contains(user)) {
            user.editSalary(this, salary);
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public List<Integer> viewPastSalaries(IUser user) {
        if (user.equals(manager) || user.isAdmin() || user.isHR()) {
            return pastSalaries;
        }
        else if (employees.contains(user)) {
            return user.viewPastSalaries(this);
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public int viewVacationBalance(IUser user) {
        if (user.equals(manager) || user.isAdmin() || user.isHR()) {
            return vacationBalance;
        }
        else if (employees.contains(user)) {
            return user.viewVacationBalance(this);
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public void changeVacationBalance(IUser user, int days) {
        if (employees.contains(user) || user.isAdmin()) {
            user.changeVacationBalance(this, days);
        }
        throw new IllegalArgumentException("Can't change this user's information.");
    }

    @Override
    public int viewAnnualBonus(IUser user) {
        if (user.equals(manager) || user.isAdmin() || user.isHR()) {
            return annualBonus;
        }
        else if (employees.contains(user)) {
            return user.viewAnnualBonus(this);
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public void changeAnnualBonus(IUser user, int value) {
        if (employees.contains(user) || user.isAdmin()) {
            user.changeVacationBalance(this, value);
        }
        throw new IllegalArgumentException("Can't change this user's information.");
    }

}
