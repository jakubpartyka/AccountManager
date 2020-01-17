import java.util.ArrayList;
import java.util.List;

public abstract class WebTask implements Runnable {
    private List<Account> operatingAccounts = new ArrayList<>();

    WebTask(Account account){
        operatingAccounts.add(account);
    }

    WebTask(List<Account> accounts){
        operatingAccounts.addAll(accounts);
    }
}
