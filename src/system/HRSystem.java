package system;

import users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        IUser admin = new Admin("admin", "admin", 20000, 35, 1000);
        IHRSystem hr = new HRSystem(admin);
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to Dunder Mifflin's HR System! Please login");
        System.out.println("User ID:");
        String username = scan.next();
        System.out.println("Password:");
        String password = scan.next();
        hr.login(username, password);
        System.out.println("Welcome admin! enter your command here:");
        System.out.println("Possible commands:");
        System.out.println("- viewSalary [user id]");
        System.out.println("- viewPastSalaries [user id]");
        System.out.println("- viewVacationBalance [user id]");
        System.out.println("- viewAnnualBonus [user id]");
        System.out.println("- addEmployee [type] [user id] [temp-password] [salary] [vacation balance] [annual bonus] [manager id]");
        System.out.println("- exit");
        while (scan.hasNext()) {
            String command = scan.next();
            if (command.equals("viewSalary")) {
                String next = scan.next();
                try {
                    System.out.println(hr.viewSalary(next));
                } catch (IllegalArgumentException ie) {
                    System.out.println("Invalid command. " + ie.getMessage());
                }
            }
            else if (command.equals("viewPastSalaries")) {
                String next = scan.next();
                try {
                    System.out.println(hr.viewPastSalaries(next));
                } catch (IllegalArgumentException ie) {
                    System.out.println("Invalid command. " + ie.getMessage());
                }
            }
            else if (command.equals("viewVacationBalance")) {
                String next = scan.next();
                try {
                    System.out.println(hr.viewVacationBalance(next));
                } catch (IllegalArgumentException ie) {
                    System.out.println("Invalid command. " + ie.getMessage());
                }
            }
            else if (command.equals("viewAnnualBalance")) {
                String next = scan.next();
                try {
                    System.out.println(hr.viewVacationBalance(next));
                } catch (IllegalArgumentException ie) {
                    System.out.println("Invalid command. " + ie.getMessage());
                }
            }
            else if (command.equals("addEmployee")) {
                String type = scan.next();
                String id = scan.next();
                String pw = scan.next();
                int salary = Integer.parseInt(scan.next());
                int vb = Integer.parseInt(scan.next());
                int ab = Integer.parseInt(scan.next());
                String manager = scan.next();
                try {
                    IUser user;
                    if (type.equals("manager")) {
                        if (manager.equals("none")) {
                            user = new Manager(id, pw, salary, vb, ab, "");
                        }
                        else {
                            user = new Manager(id, pw, salary, vb, ab, manager);
                        }
                    }
                    else if (type.equals("employee")) {
                        user = new Employee(id, pw, salary, vb, ab, manager);
                    }
                    else if (type.equals("hr-employee")) {
                        user = new HREmployee(id, pw, salary, vb, ab, manager);
                    }
                    else {
                        user = new Admin(id, pw, salary, vb, ab);
                    }
                    hr.addEmployee(user);
                    System.out.println("Employee added!");
                } catch (IllegalArgumentException ie) {
                    System.out.println("Invalid command. " + ie.getMessage());
                }
            }
            else if (command.equals("exit")) {
                break;
            }

            System.out.println("Possible commands:");
            System.out.println("- viewSalary [user id]");
            System.out.println("- viewPastSalaries [user id]");
            System.out.println("- viewVacationBalance [user id]");
            System.out.println("- viewAnnualBonus [user id]");
            System.out.println("- addEmployee [type] [user id] [temp-password] [salary] [vacation balance] [annual bonus] [manager id]");
            System.out.println("- exit");
        }
    }
}
