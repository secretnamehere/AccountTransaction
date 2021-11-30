import java.util.Random;
import org.apache.log4j.Logger;
public class Transactor {
    final static Logger logger = Logger.getLogger(Transactor.class);

    public static boolean transaction(Account from, Account to, int amount){
        Random randomTime = new Random();
        int millis = randomTime.nextInt(1000) + 1000;

        if(from == to) {
            logger.warn("Self-transaction append From:" + from.getID() + " To:" + to.getID() + " Amount: " + amount);
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }

        if ((from.getMoney() - amount) > 0) {
            from.withdrawMoney(amount);
            to.addMoney(amount);
            logger.info("Transaction is done! From:" + from.getID() + " To:" + to.getID() + " Amount: " + amount);
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            logger.warn("Not enough money on " + from.getID() + " From:" + from.getID() + " To:" + to.getID() + " Amount: " + amount);
            try {
             Thread.sleep(millis);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            return false;
        }
    }
}
