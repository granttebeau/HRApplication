package users;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUser implements IUser{
    protected final String userID;
    protected String password;
    protected int salary;
    protected List<Integer> pastSalaries;
    protected int vacationBalance;
    protected int annualBonus;
    protected String manager;

    public AbstractUser(String id, String password, int salary, int vacationBalance,
                        int annualBonus) {
        this.userID = id;
        this.password = password;
        this.salary = salary;
        this.vacationBalance = vacationBalance;
        this.annualBonus = annualBonus;
        this.pastSalaries = new ArrayList<Integer>();
    }

    @Override
    public boolean login(String id, String password) {
        return this.userID.equals(id) && this.password.equals(password);
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
    public int viewSalary(IUser user) {
        if (acceptableView(user)) {
            return salary;
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public List<Integer> viewPastSalaries(IUser user) {
        if (acceptableView(user)) {
            return pastSalaries;
        }
        throw new IllegalArgumentException("can't view this user's past salaries");
    }

    @Override
    public int viewVacationBalance(IUser user) {
        if (acceptableView(user)) {
            return vacationBalance;
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public int viewAnnualBonus(IUser user) {
        if (acceptableView(user)) {
            return annualBonus;
        }
        throw new IllegalArgumentException("Can't access this user's information.");
    }

    @Override
    public void addEmployee(IUser admin, IUser employee) {
        throw new IllegalArgumentException("Can't add an employee to an employee");
    }

    @Override
    public void changeManager(IUser admin, String manager) {
        if (admin.isAdmin()) {
            this.manager = manager;
            return;
        }
        throw new IllegalArgumentException("This user can't update another user's information");
    }

    @Override
    public String getManager(IUser admin) {
        if (acceptableView(admin)) {
            return manager;
        }
        throw new IllegalArgumentException("User can't access information");
    }

    @Override
    public void editSalary(IUser user, int salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Can't have a negative salary");
        }
        if (acceptableEdit(user)) {
            pastSalaries.add(this.salary);
            this.salary = salary;
            return;
        }
        throw new IllegalArgumentException("can't edit this user's salary");
    }

    @Override
    public void changeVacationBalance(IUser user, int days) {
        if (vacationBalance + days < 0) {
            throw new IllegalArgumentException("Only " + vacationBalance + " vacation days remaining");
        }
        if (acceptableEdit(user)) {
            this.vacationBalance += days;
            return;
        }
        throw new IllegalArgumentException("Can't change this user's information.");
    }

    @Override
    public void changeAnnualBonus(IUser user, int value) {
        if (annualBonus + value < 0) {
            throw new IllegalArgumentException("Can't have a negative annual bonus");
        }
        if (acceptableEdit(user)) {
            this.annualBonus += value;
            return;
        }
        throw new IllegalArgumentException("Can't change this user's information.");
    }

    @Override
    public String getUserID() {
        return userID;
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
    public boolean isHR() {
        return false;
    }

    protected boolean acceptableView(IUser user) {
        return user.equals(this) || user.getUserID().equals(manager) || user.isAdmin() || user.isHR();
    }

    protected boolean acceptableEdit(IUser user) {
        return user.getUserID().equals(manager) || user.isAdmin();
    }
}
