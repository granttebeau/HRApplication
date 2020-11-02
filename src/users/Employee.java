package users;

import java.util.List;

public class Employee extends AbstractUser {

    private IUser manager;

    public Employee(String id, String password, int salary, int vacationBalance, int annualBonus, IUser manager) {
        super(id, password, salary, vacationBalance, annualBonus);
        this.manager = manager;
    }

    @Override
    public boolean isAdmin() {
        return false;
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
    public int viewSalary(IUser user) {
        if (acceptableUser(user)) {
            return salary;
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public void editSalary(IUser user, int salary) {
        if (user.equals(manager)) {
            pastSalaries.add(this.salary);
            this.salary = salary;
        }
        throw new IllegalArgumentException("Can't edit another user's salary.");
    }

    @Override
    public List<Integer> viewPastSalaries(IUser user) {
        if (acceptableUser(user)) {
            return pastSalaries;
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public int viewVacationBalance(IUser user) {
        if (acceptableUser(user)) {
            return vacationBalance;
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public void changeVacationBalance(IUser user, int days) {
        if (user.equals(manager) || user.isAdmin()) {
            vacationBalance += days;
        }
        throw new IllegalArgumentException("Can't change another user's vacation balance.");
    }

    @Override
    public int viewAnnualBonus(IUser user) {
        if (acceptableUser(user)) {
            return salary;
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public void changeAnnualBonus(IUser user, int value) {
        if (user.equals(manager)) {
            annualBonus += value;
        }
        throw new IllegalArgumentException("Can't change another user's annual bonus.");
    }

    @Override
    public boolean isHR() {
        return false;
    }

    private boolean acceptableUser(IUser user) {
        return user.equals(this) || user.equals(manager) || user.isAdmin() || user.isHR();
    }
}
