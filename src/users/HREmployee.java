package users;

/**
 * Represents a Human Resources Employee.
 */
public class HREmployee extends AbstractUser {

    public HREmployee(String id, String password, int salary, int vacationBalance, int annualBonus,
                             String manager, boolean isAdmin) {
        super(id, password, salary, vacationBalance, annualBonus, isAdmin);
        this.manager = manager;
    }

    @Override
    public boolean isHR() {
        return true;
    }

    @Override
    protected boolean acceptableView(IUser user) {
        return user.equals(this) || user.getUserID().equals(manager) || user.isAdmin();
    }
}
