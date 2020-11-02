package system;

import users.IUser;

import java.util.List;

public class HRSystem implements IHRSystem {

    List<IUser> managers;
    List<IUser> employees;
    IUser currentUser;
    boolean loggedIn;

    public HRSystem() {
        this.loggedIn = false;
    }

    @Override
    public void login(String id, String password) {

    }

    @Override
    public void addManager(IUser manager) {

    }

    @Override
    public void addEmployee(IUser employee) {

    }
}
