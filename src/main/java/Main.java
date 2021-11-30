import org.apache.log4j.Logger;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    final static Logger logger = Logger.getLogger(Main.class);
    private static final int NUM_OF_THREADS = 4;
    private static final int NUM_OF_ACCOUNTS = 10;
    private static final int NUM_OF_TRANSACTIONS = 30;
    private static final int DEFAULT_AMOUNT_OF_MONEY = 10_000;

    public static void main(String[] args) throws Exception {
        logger.info("New part of transactions");
        final Random randomizer = new Random();
        final List<Account> accounts = new ArrayList<>();
        for(int i=0; i < NUM_OF_ACCOUNTS; i++){
            int randomID;
            do {
                randomID = randomizer.nextInt(1_000_000);
            } while (Account.getUsedIds().contains(Integer.toString(randomID)));
            Account currentAccount = new Account(Integer.toString(randomID), DEFAULT_AMOUNT_OF_MONEY);
            accounts.add(currentAccount);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREADS);
        final CountDownLatch countDownLatch = new CountDownLatch(NUM_OF_TRANSACTIONS);
        for(int i = 0; i < NUM_OF_TRANSACTIONS; i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    int from = randomizer.nextInt(NUM_OF_ACCOUNTS);
                    int to = randomizer.nextInt(NUM_OF_ACCOUNTS);
                    int amount =  randomizer.nextInt(DEFAULT_AMOUNT_OF_MONEY);
                    Transactor.transaction(accounts.get(from), accounts.get(to), amount);
                    countDownLatch.countDown();
                }
            });
        }
        executorService.shutdown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info(NUM_OF_TRANSACTIONS + " transactions have been done!");
    }
}
