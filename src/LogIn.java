import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

//this method logs in into single account displaying it to the user
class LogIn extends WebTask {
    private Account currentAccount;

    LogIn (Account operatingAccount){
        super(operatingAccount);
        currentAccount = operatingAccount;
    }

    @Override
    public void run() {
        //setting up a web driver with popup blocking
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        ChromeDriver driver = new ChromeDriver(options);

        //navigating to profile page
        String profilePage = "https://www.facebook.com/" + currentAccount.getId();
        driver.get(profilePage);                                //navigating to target's profile page

        //locating input elements
        try {
            WebElement login = driver.findElementByXPath("//*[@id='email']");
            WebElement password = driver.findElementByXPath("//*[@id='pass']");
            WebElement submit = driver.findElementByXPath("//*[@type='submit']");
            Thread.sleep(300);                                                               //cool down
            login.sendKeys(currentAccount.getLogin());
            password.sendKeys(currentAccount.getPassword());
            Thread.sleep(100);
            submit.click();
            try {
                //trying to locate login field again
                password = driver.findElementByXPath("//*[@type='password']");
                submit = driver.findElementByXPath("//*[@type='submit']");
                //this code runs if first login failed (probably due to unaccepted cookies policy)
                Thread.sleep(2000);
                password.sendKeys(currentAccount.getPassword());
                submit.click();
            }
            catch (NoSuchElementException ignored){}
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "failed to log in", "failed to log in",JOptionPane.WARNING_MESSAGE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
