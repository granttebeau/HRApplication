package users;

import java.util.List;

public class HREmployee extends AbstractUser {

    IUser manager;

    public HREmployee(String id, String password, int salary, int vacationBalance, int annualBonus, IUser manager) {
        super(id, password, salary, vacationBalance, annualBonus);
        this.manager = manager;
    }

    @Override
    public boolean isHR() {
        return true;
    }

    @Override
    public void addManager(IUser manager) {
        this.manager = manager;
    }

    @Override
    public void addEmployee(IUser employee) {
        throw new IllegalArgumentException("Can't add an employee to an employee");
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public int viewSalary(IUser user) {
        if (user.equals(this) || user.equals(manager) || user.isAdmin()) {
            return salary;
        }
        else if (!user.isHR()) {
            return user.viewSalary(this);
        }
        throw new IllegalArgumentException("can't view this user's salary");
    }

    @Override
    public void editSalary(IUser user, int salary) {
        if (user.equals(manager) || user.isAdmin()) {
            pastSalaries.add(this.salary);
            this.salary = salary;
        }
        throw new IllegalArgumentException("can't edit this user's salary");
    }

    @Override
    public List<Integer> viewPastSalaries(IUser user) {
        if (user.equals(manager) || user.isAdmin()) {
            return pastSalaries;
        }
        else if (!user.isHR()) {
            return user.viewPastSalaries(this);
        }
        throw new IllegalArgumentException("can't view this user's past salaries");
    }

    @Override
    public int viewVacationBalance(IUser user) {
        if (user.equals(manager) || user.isAdmin()) {
            return vacationBalance;
        }
        else if (!user.isHR()) {
            return user.viewVacationBalance(this);
        }
        throw new IllegalArgumentException("can't view this user's vacation balance");
    }

    @Override
    public void changeVacationBalance(IUser user, int days) {
        if (user.equals(manager) || user.isAdmin()) {
            this.vacationBalance += days;
        }
        throw new IllegalArgumentException("can't change this user's vacation balance");
    }

    @Override
    public int viewAnnualBonus(IUser user) {
        if (user.equals(manager) || user.isAdmin()) {
            return annualBonus;
        }
        else if (!user.isHR()) {
            return user.viewAnnualBonus(this);
        }
        throw new IllegalArgumentException("can't view this user's annual bonus");
    }

    @Override
    public void changeAnnualBonus(IUser user, int value) {
        if (user.equals(manager) || user.isAdmin()) {
            this.annualBonus +=  value;
        }
        throw new IllegalArgumentException("can't change this user's annual bonus");
    }
}
