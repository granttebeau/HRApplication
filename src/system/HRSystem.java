package system;

import users.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a Human Resources System.
 */
public class HRSystem implements IHRSystem {
    List<IUser> managers;
    List<IUser> employees;
    IUser currentUser;
    boolean loggedIn;

    public HRSystem(IUser admin) {
        if (!admin.isAdmin()) {
            throw new IllegalArgumentException("Have to start a system with an admin");
        }
        this.loggedIn = false;
        this.managers = new ArrayList<IUser>();
        this.employees = new ArrayList<IUser>();
        if (admin.isManager()) {
            this.managers.add(admin);
        }
        else {
            this.employees.add(admin);
        }
        this.currentUser = null;
    }

    @Override
    public void login(String id, String password) {
        if (loggedIn) {
            throw new IllegalStateException("Already logged in");
        }
        List<IUser> users = new ArrayList<IUser>();
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
        else if (existingID(employee)) {
            throw new IllegalArgumentException("User ID already exists");
        }
        if (currentUser.isAdmin()) {
            if (employee.isManager()) {
                managers.add(employee);
                if (employee.viewManager(currentUser) != null) {
                    getEmployeeByID(employee.viewManager(currentUser)).addEmployee(currentUser, employee);
                }
            }
            else {
                employees.add(employee);
                IUser manager = getEmployeeByID(employee.viewManager(currentUser));
                employee.changeManager(currentUser, manager.getUserID());
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
        users.addAll(managers);
        users.addAll(employees);
        for (IUser user : users) {
            if (employee.getUserID().equals(user.getUserID())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IUser getEmployeeByID(String id) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        List<IUser> users = new ArrayList<IUser>();
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
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        IUser user = getEmployeeByID(userID);
        try {
            return user.viewSalary(currentUser);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException("Invalid access. " + ie.getMessage());
        }
    }

    @Override
    public void changeSalary(String userID, int amount) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        IUser user = getEmployeeByID(userID);
        try {
            user.editSalary(currentUser, amount);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException("Invalid access. " + ie.getMessage());
        }
    }

    @Override
    public List<Integer> viewPastSalaries(String userID) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        IUser user = getEmployeeByID(userID);
        try {
            return user.viewPastSalaries(currentUser);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException("Invalid access. " + ie.getMessage());
        }
    }

    @Override
    public int viewVacationBalance(String userID) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        IUser user = getEmployeeByID(userID);
        try {
            return user.viewVacationBalance(currentUser);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException("Invalid access. " + ie.getMessage());
        }
    }

    @Override
    public void changeVacationBalance(String userID, int increment) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        IUser user = getEmployeeByID(userID);
        try {
            user.changeVacationBalance(currentUser, increment);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException("Invalid access. " + ie.getMessage());
        }
    }

    @Override
    public int viewAnnualBonus(String userID) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        IUser user = getEmployeeByID(userID);
        try {
            return user.viewAnnualBonus(currentUser);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException("Invalid access. " + ie.getMessage());
        }
    }

    @Override
    public void changeAnnualBonus(String userID, int increment) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        IUser user = getEmployeeByID(userID);
        try {
            user.changeAnnualBonus(currentUser, increment);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException("Invalid access. " + ie.getMessage());
        }
    }

    @Override
    public void changeManager(String employee, String manager) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        IUser employ = getEmployeeByID(employee);
        IUser oldManager = getEmployeeByID(employ.viewManager(currentUser));
        IUser mangr = getEmployeeByID(manager);
        if (mangr.isManager()) {
            try {
                employ.changeManager(currentUser, manager);
                mangr.addEmployee(currentUser, employ);
                oldManager.removeEmployee(currentUser, employ);
            } catch (IllegalArgumentException ie) {
                throw new IllegalArgumentException("Invalid access. " + ie.getMessage());
            }
        }
        else {
            throw new IllegalArgumentException("Has to be a Manager");
        }
    }

    @Override
    public List<String> viewEmployees(String userID) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        IUser user = getEmployeeByID(userID);
        try {
            return user.viewEmployees(currentUser);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException("Invalid access. " + ie.getMessage());
        }
    }

    @Override
    public void removeEmployee(String employee) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        if (currentUser.isAdmin()) {
            IUser employ = getEmployeeByID(employee);
            IUser oldManager = getEmployeeByID(employ.viewManager(currentUser));
            if (employ.isManager()) {
                List<String> ems = employ.viewEmployees(currentUser);
                String man = employ.viewManager(currentUser);
                IUser manager = getEmployeeByID(man);
                for (String s : ems) {
                    IUser user = getEmployeeByID(s);
                    user.changeManager(currentUser, man);
                    manager.addEmployee(currentUser, user);
                }
            }
            oldManager.removeEmployee(currentUser, employ);
            managers.remove(employ);
            return;
        }
        throw new IllegalArgumentException("The current user can't remove an employee");
    }

    @Override
    public String viewManager(String userID) {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
        IUser user = getEmployeeByID(userID);
        try {
            return user.viewManager(currentUser);
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException("Invalid access. " + ie.getMessage());
        }
    }

    public static void main(String[] args) {
        // Initialize the HRSystem with an admin user.
        IUser admin = new Manager("jan", "vp-northeast-sales",
                1000000, 55, 50000, "", true);
        IHRSystem hr = new HRSystem(admin);

        // Log in as the admin user and add various employees
        hr.login("jan", "vp-northeast-sales");
        hr.addEmployee(new Manager("michael", "michael-scarn",
                39000, 35, 10000, "jan", false));
        hr.addEmployee(new Employee("dwight", "assistant-to-the-rm",
                80000, 21, 8000, "michael", false));
        hr.addEmployee(new Employee("jim", "big-tuna",
                40000, 23, 7000, "michael", false));
        hr.addEmployee(new HREmployee("toby", "human-resources",
                55000, 40, 5432, "michael", false));
        hr.addEmployee(new HREmployee("holly", "human-resources",
                56000, 23, 4353, "michael", false));

        // Demonstrates the view and change functionalities of the admin user. Should print out:
        //  1000000
        //  []
        //  21
        //  5432
        //  michael
        //  --------------
        //  2000000
        //  [39000]
        //  20
        //  5832
        //  jan
        System.out.println(hr.viewSalary("jan"));
        System.out.println(hr.viewPastSalaries("michael"));
        System.out.println(hr.viewVacationBalance("dwight"));
        System.out.println(hr.viewAnnualBonus("toby"));
        System.out.println(hr.viewManager("toby"));
        hr.changeSalary("jan", 2000000);
        hr.changeSalary("michael", 40000);
        hr.changeVacationBalance("dwight", -1);
        hr.changeAnnualBonus("toby", 400);
        hr.changeManager("toby", "jan");
        System.out.println("--------------");
        System.out.println(hr.viewSalary("jan"));
        System.out.println(hr.viewPastSalaries("michael"));
        System.out.println(hr.viewVacationBalance("dwight"));
        System.out.println(hr.viewAnnualBonus("toby"));
        System.out.println(hr.viewManager("toby"));
        System.out.println("--------------");

        // Logout of the admin and log into as a regular employee.
        hr.logout();
        hr.login("dwight", "assistant-to-the-rm");
        System.out.println("--------------");

        // Demonstrate the view/change functionality of a regular employee. Should display one 8000, and then
        // multiple instances of error messages: "Invalid access. Can't access this user's information."
        System.out.println(hr.viewAnnualBonus("dwight"));
        try {
            System.out.println(hr.viewAnnualBonus("jim"));
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }
        try {
            System.out.println(hr.viewAnnualBonus("toby"));
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }
        try {
            System.out.println(hr.viewAnnualBonus("michael"));
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }
        try {
            hr.changeAnnualBonus("dwight", 200);
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }
        try {
            hr.changeAnnualBonus("michael", 200);
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }
        try {
            hr.changeAnnualBonus("toby", 200);
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }

        // Log out of a regular employee's account, and log into as an HR Employee's account.
        hr.logout();
        hr.login("toby", "human-resources");
        System.out.println("--------------");

        // Demonstrate the view/change functionality of an HR employee. Should display:
        // 55
        // 35
        // 23
        // 40
        // Followed by multiple instances of "Invalid access. Can't change this user's information." because
        // an HR employee can only view the information from employees that aren't HR employees.
        System.out.println(hr.viewVacationBalance("jan"));
        System.out.println(hr.viewVacationBalance("michael"));
        System.out.println(hr.viewVacationBalance("jim"));
        System.out.println(hr.viewVacationBalance("toby"));
        try {
            System.out.println(hr.viewVacationBalance("holly"));
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }
        try {
            hr.changeVacationBalance("dwight", 200);
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }
        try {
            hr.changeVacationBalance("michael", 200);
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }
        try {
            hr.changeVacationBalance("toby", 200);
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }
        try {
            hr.changeVacationBalance("holly", 200);
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }

        // Log out of an HR employee's account and log back in as an admin.
        hr.logout();
        hr.login("jan", "vp-northeast-sales");
        System.out.println("--------------");

        // Demonstrates the view/change employees functionality.
        // Should print out:
        //  [dwight, jim, holly]
        //  [michael, toby]
        //  --------------
        //  [dwight, jim]
        //  [michael, toby]
        //  --------------
        //  No such user
        //  [toby, dwight, jim]
        System.out.println(hr.viewEmployees("michael"));
        System.out.println(hr.viewEmployees("jan"));

        hr.removeEmployee("holly");
        System.out.println("--------------");
        System.out.println(hr.viewEmployees("michael"));
        System.out.println(hr.viewEmployees("jan"));

        hr.removeEmployee("michael");
        System.out.println("--------------");
        try {
            System.out.println(hr.viewEmployees("michael"));
        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
        }
        System.out.println(hr.viewEmployees("jan"));
    }
}
