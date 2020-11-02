package users;

import java.util.List;

public abstract class AbstractUser implements IUser{
    protected final String userID;
    protected String password;
    protected int salary;
    protected List<Integer> pastSalaries;
    protected int vacationBalance;
    protected int annualBonus;

    public AbstractUser(String id, String password, int salary, int vacationBalance, int annualBonus) {
        this.userID = id;
        this.password = password;
        this.salary = salary;
        this.vacationBalance = vacationBalance;
        this.annualBonus = annualBonus;
    }

    @Override
    public boolean login(String id, String password) {
        return this.userID.equals(id) && this.password.equals(password);
    }

    @Override
    public String getUserID() {
        return userID;
    }
}
