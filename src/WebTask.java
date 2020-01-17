import java.util.ArrayList;
import java.util.List;

public class WebTask implements Runnable {
    private List<Account> operatingAccounts = new ArrayList<>();
    private String task;

    WebTask(String task, Account account){
        this.task = task;
        operatingAccounts.add(account);
    }

    WebTask(String task, List<Account> accounts){
        this.task = task;
        operatingAccounts.addAll(accounts);
    }

    void discoverName(Account account){
        System.out.println("test");
    }

    @Override
    public void run() {
        if(task.equalsIgnoreCase("discoverName"))
            operatingAccounts.forEach(this::discoverName);
        else
            try {
                throw new NoSuchMethodException();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
    }
}
