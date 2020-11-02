package users;

import java.util.List;

public interface IUser {
    public boolean login(String id, String password);

    public String getUserID();

    public void addManager(IUser manager);

    public void addEmployee(IUser employee);

    public boolean isAdmin();

    public boolean isHR();

    public int viewSalary(IUser user);

    public void editSalary(IUser user, int salary);

    public List<Integer> viewPastSalaries(IUser user);

    public int viewVacationBalance(IUser user);

    public void changeVacationBalance(IUser user, int days);

    public int viewAnnualBonus(IUser user);

    public void changeAnnualBonus(IUser user, int value);
}
