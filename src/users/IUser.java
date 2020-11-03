package users;

import java.util.List;

public interface IUser {
    public boolean login(String id, String password);

    public String getUserID();

    public void addManager(IUser admin, String manager);

    public void addEmployee(IUser admin, IUser employee);

    public String getManager(IUser admin);

    public void changeManager(IUser admin, String manager);

    public boolean isAdmin();

    public boolean isHR();

    public boolean isManager();

    public int viewSalary(IUser user);

    public void editSalary(IUser user, int salary);

    public List<Integer> viewPastSalaries(IUser user);

    public int viewVacationBalance(IUser user);

    public void changeVacationBalance(IUser user, int days);

    public int viewAnnualBonus(IUser user);

    public void changeAnnualBonus(IUser user, int value);
}
