package system;

import users.IUser;

import java.util.List;

public interface IHRSystem {
    public void login(String id, String password);

    public void logout();

    public void addEmployee(IUser employee);

    public boolean existingID(IUser user);

    public IUser getEmployeeByID(String id);

    public int viewSalary(String userID);

    public void changeSalary(String userID, int amount);

    public List<Integer> viewPastSalaries(String userID);

    public int viewVacationBalance(String userID);

    public void changeVacationBalance(String userID, int increment);

    public int viewAnnualBalance(String userID);

    public void changeAnnualBalance(String userID, int increment);
}
