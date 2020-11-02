package system;

import users.IUser;

public interface IHRSystem {
    public void login(String id, String password);

    public void addManager(IUser manager);

    public void addEmployee(IUser employee);
}
