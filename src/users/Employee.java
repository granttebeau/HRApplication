package users;

public class Employee extends AbstractUser {

    public Employee(String id, String password, int salary, int vacationBalance, int annualBonus, String manager) {
        super(id, password, salary, vacationBalance, annualBonus);
        this.manager = manager;
    }
}
