package users;

import java.util.ArrayList;
import java.util.List;

public class Manager extends AbstractUser {

    private List<IUser> employees;

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

}
