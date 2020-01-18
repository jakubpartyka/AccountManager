//this method opens new browser window and shows the selected profile
//for single account only

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ShowProfile extends WebTask {

    ShowProfile(Account operatingAccount){
        super(operatingAccount);
    }

    @Override
    public void run() {
        //setting up a web driver with popup blocking
        ChromeOptions options  = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");

        WebDriver driver = new ChromeDriver(options);

        String profilePage = "https://www.facebook.com/" + operatingAccounts.get(0).getId();
        driver.get(profilePage);
    }
}
