import java.lang.Exception;
import java.util.HashSet;
import java.util.Set;

public class Account {
    private final String ID;
    private int Money;
    private static final Set<String> usedIds = new HashSet<>();
    private final Object monitor = new Object();

    Account(String ID, int Money) throws Exception {
        if (usedIds.contains(ID)) {
            throw new Exception("ID field must be unique!");
        }
        usedIds.add(ID);
        this.ID = ID;
        this.Money = Money;
    }

    Account(){
        int k = 1;
        while(usedIds.contains(Integer.toString(k))){
            k++;
        }
        usedIds.add(Integer.toString(k));
        this.ID = Integer.toString(k);
        this.Money = 0;
    }
    public void withdrawMoney(int amount){
        synchronized (monitor){
            this.Money -= amount;
        }
    }

    public void addMoney(int amount){
        synchronized (monitor){
            this.Money += amount;
        }
    }

    public String getID() {

        return ID;
    }

    public int getMoney() {

        return Money;
    }

    public void setMoney(int money) {

        Money = money;
    }

    public static Set<String> getUsedIds()
    {
        return usedIds;
    }
}