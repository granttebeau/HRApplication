package users;

/**
 * Represents a regular employee.
 */
public class Employee extends AbstractUser {

    public Employee(String id, String password, int salary, int vacationBalance, int annualBonus,
                    String manager, boolean isAdmin) {
        super(id, password, salary, vacationBalance, annualBonus, isAdmin);
        this.manager = manager;
    }
}
