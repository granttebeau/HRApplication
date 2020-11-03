package users;

import java.util.List;

public class Employee extends AbstractUser {

    private String managerID;

    public Employee(String id, String password, int salary, int vacationBalance, int annualBonus, String managerID) {
        super(id, password, salary, vacationBalance, annualBonus);
        this.managerID = managerID;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public boolean isManager() {
        return false;
    }

    @Override
    public void addManager(IUser admin, String manager) {
        if (admin.isAdmin()) {
            this.managerID = manager;
            return;
        }
        throw new IllegalArgumentException("This user can't add a manager");
    }

    @Override
    public void addEmployee(IUser admin, IUser employee) {
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
        if (user.getUserID().equals(managerID) || user.isAdmin()) {
            pastSalaries.add(this.salary);
            this.salary = salary;
            return;
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
        if (user.getUserID().equals(managerID) || user.isAdmin()) {
            vacationBalance += days;
            return;
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
        if (user.getUserID().equals(managerID) || user.isAdmin()) {
            annualBonus += value;
            return;
        }
        throw new IllegalArgumentException("Can't change another user's annual bonus.");
    }

    @Override
    public String getManager(IUser admin) {
        if (admin.isAdmin() || admin.getUserID().equals(this.managerID) || admin.isHR()) {
            return managerID;
        }
        throw new IllegalArgumentException("User can't access information");
    }

    @Override
    public boolean isHR() {
        return false;
    }

    private boolean acceptableUser(IUser user) {
        return user.equals(this) || user.getUserID().equals(managerID) || user.isAdmin() || user.isHR();
    }
}
