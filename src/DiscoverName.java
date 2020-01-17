//purpose of this task is to check registered names of the given accounts
//this task does not require logging in to the operating account

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.util.List;

@SuppressWarnings("unused")
public class DiscoverName extends WebTask {
    private boolean useIncognitoMode = true;
    private boolean useHeadlessMode = true;

    DiscoverName(Account account) {
        super(account);
    }

    DiscoverName(List<Account> accounts){
        super(accounts);
    }

    @Override
    public void run() {
        //cleaning result lists (in case of re-running this runnable)
        successful.clear();
        unsuccessful.clear();

        //setting up a web driver with popup blocking
        ChromeOptions options  = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        if(useIncognitoMode)
            options.addArguments("incognito");
        if(useHeadlessMode)
            options.addArguments("headless");

        ChromeDriver driver = new ChromeDriver(options);

        for (Account currentAccount : operatingAccounts) {
            String profilePage = "https://www.facebook.com/" + currentAccount.getId();
            driver.get(profilePage);                                //navigating to target's profile page
            try{
                WebElement nameTag = driver.findElementByXPath("//*[@class='_2nlw _2nlv']");
                currentAccount.setName(nameTag.getText());      //setting name to discovered value
                currentAccount.setStatus(Account.ONLINE);       //setting account status
                successful.add(currentAccount);                //adding to successful list for further access
            }
            catch (NoSuchElementException e){
                unsuccessful.add(currentAccount);               //add to unsuccessful if name could not be resolved (or found)
            }
        }
        if(unsuccessful.size() > 0){
            JOptionPane.showMessageDialog(null, "Name discovery operation was unsuccessful for following accounts: " + unsuccessful.toString());
            unsuccessful.forEach(e -> e.setStatus(Account.STATUS_ACTION_NEEDED));
        }
    }

    public void setUseIncognitoMode(boolean useIncognitoMode) {
        this.useIncognitoMode = useIncognitoMode;
    }

    public void setUseHeadlessMode(boolean useHeadlessMode) {
        this.useHeadlessMode = useHeadlessMode;
    }
}
