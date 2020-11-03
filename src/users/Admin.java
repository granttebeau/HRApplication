package users;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AbstractUser {

    public Admin(String id, String password, int salary, int vacationBalance, int annualBonus) {
        super(id, password, salary, vacationBalance, annualBonus);
    }

    @Override
    public boolean isManager() {
        return false;
    }

    @Override
    public void addManager(IUser admin, String manager) {

    }

    @Override
    public void addEmployee(IUser admin, IUser employee) {

    }

    @Override
    public String getManager(IUser admin) {
        return null;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }

    @Override
    public boolean isHR() {
        return false;
    }

    @Override
    public int viewSalary(IUser user) {
        return 0;
    }

    @Override
    public void editSalary(IUser user, int salary) {

    }

    @Override
    public List<Integer> viewPastSalaries(IUser user) {
        return null;
    }

    @Override
    public int viewVacationBalance(IUser user) {
        return 0;
    }

    @Override
    public void changeVacationBalance(IUser user, int days) {

    }

    @Override
    public int viewAnnualBonus(IUser user) {
        return 0;
    }

    @Override
    public void changeAnnualBonus(IUser user, int value) {

    }
}
