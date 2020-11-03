package system;

import users.Admin;
import users.Employee;
import users.IUser;
import users.Manager;

import java.util.ArrayList;
import java.util.List;

public class HRSystem implements IHRSystem {

    List<IUser> managers;
    List<IUser> employees;
    List<IUser> admin;
    IUser currentUser;
    boolean loggedIn;

    public HRSystem(IUser admin) {
        if (!admin.isAdmin()) {
            throw new IllegalArgumentException("Have to start a system with an admin");
        }
        this.loggedIn = false;
        this.admin = new ArrayList<IUser>();
        this.admin.add(admin);
        this.managers = new ArrayList<IUser>();
        this.employees = new ArrayList<IUser>();
        this.currentUser = null;
    }

    @Override
    public void login(String id, String password) {
        if (loggedIn) {
            throw new IllegalStateException("Already logged in");
        }
        List<IUser> users = new ArrayList<IUser>();
        users.addAll(admin);
        users.addAll(managers);
        users.addAll(employees);
        for (IUser user : users) {
            if (user.login(id, password)) {
                currentUser = user;
                loggedIn = true;
                return;
            }
        }
        throw new IllegalArgumentException("Invalid credentials");
    }

    @Override
    public void logout() {
        if (loggedIn) {
            loggedIn = false;
            currentUser = null;
        }
        else {
            throw new IllegalStateException("Not logged in");
        }
    }

    @Override
    public void addEmployee(IUser employee) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        if (currentUser.isAdmin()) {
            if (employee.isManager()) {
                managers.add(employee);
                if (employee.getManager(currentUser) != null) {
                    getEmployeeByID(employee.getManager(currentUser)).addEmployee(currentUser, employee);
                }
            }
            else if (employee.isAdmin()) {
                admin.add(employee);
            }
            else {
                employees.add(employee);
                IUser manager = getEmployeeByID(employee.getManager(currentUser));
                employee.addManager(currentUser, manager.getUserID());
                manager.addEmployee(currentUser, employee);
            }
        }
    }

    @Override
    public boolean existingID(IUser employee) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        List<IUser> users = new ArrayList<IUser>();
        users.addAll(admin);
        users.addAll(managers);
        users.addAll(employees);
        for (IUser user : users) {
            if (employee.getUserID().equals(user.getUserID())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public IUser getEmployeeByID(String id) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        List<IUser> users = new ArrayList<IUser>();
        users.addAll(admin);
        users.addAll(managers);
        users.addAll(employees);
        for (IUser user : users) {
            if (id.equals(user.getUserID())) {
                return user;
            }
        }
        throw new IllegalArgumentException("No such user");
    }

    @Override
    public int viewSalary(String userID) {
        IUser user = getEmployeeByID(userID);
        try {
            return user.viewSalary(currentUser);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException(ie.getMessage());
        }
    }

    @Override
    public void changeSalary(String userID, int amount) {
        IUser user = getEmployeeByID(userID);
        try {
            user.editSalary(currentUser, amount);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException(ie.getMessage());
        }
    }

    @Override
    public List<Integer> viewPastSalaries(String userID) {
        IUser user = getEmployeeByID(userID);
        try {
            return user.viewPastSalaries(currentUser);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException(ie.getMessage());
        }
    }

    @Override
    public int viewVacationBalance(String userID) {
        IUser user = getEmployeeByID(userID);
        try {
            return user.viewVacationBalance(currentUser);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException(ie.getMessage());
        }
    }

    @Override
    public void changeVacationBalance(String userID, int increment) {
        IUser user = getEmployeeByID(userID);
        try {
            user.changeVacationBalance(currentUser, increment);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException(ie.getMessage());
        }
    }

    @Override
    public int viewAnnualBalance(String userID) {
        IUser user = getEmployeeByID(userID);
        try {
            return user.viewAnnualBonus(currentUser);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException(ie.getMessage());
        }
    }

    @Override
    public void changeAnnualBalance(String userID, int increment) {
        IUser user = getEmployeeByID(userID);
        try {
            user.changeAnnualBonus(currentUser, increment);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException(ie.getMessage());
        }
    }

    public static void main(String[] args) {
        IUser admin = new Admin("admin", "qwerty", 20000, 35, 1000);
        IHRSystem hr = new HRSystem(admin);
        hr.login("admin", "qwerty");
        hr.addEmployee(new Manager("top-huncho", "grant",
                100000, 20, 10000, ""));
        hr.addEmployee(new Manager("second-place", "whatsup",
                80000, 17, 800, "top-huncho"));
        hr.addEmployee(new Employee("senior-engineer", "programmer",
              39520, 21, 1050, "second-place"));
        hr.logout();

        hr.login("senior-engineer", "programmer");
        System.out.println(hr.viewPastSalaries("senior-engineer"));

//        System.out.println(hr.viewPastSalaries("second-place"));

        System.out.println(hr.viewSalary("senior-engineer"));
//        hr.changeSalary("senior-engineer", 100000);
//        System.out.println(hr.viewSalary("second-place"));
//
//        System.out.println(hr.viewPastSalaries("second-place"));
//
        System.out.println(hr.viewVacationBalance("senior-engineer"));
//        hr.changeVacationBalance("second-place", -1);
//        System.out.println(hr.viewVacationBalance("second-place"));
//
        System.out.println(hr.viewAnnualBalance("senior-engineer"));
//        hr.changeAnnualBalance("second-place", -100);
//        System.out.println(hr.viewAnnualBalance("second-place"));
    }

}
