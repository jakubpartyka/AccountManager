import java.util.ArrayList;
import java.util.List;

abstract class WebTask implements Runnable {
    List<Account> operatingAccounts = new ArrayList<>();
    List<Account> successfull = new ArrayList<>();
    List<Account> unsuccessful = new ArrayList<>();

    WebTask(Account account){
        operatingAccounts.add(account);
    }

    WebTask(List<Account> accounts){
        operatingAccounts.addAll(accounts);
    }
}
