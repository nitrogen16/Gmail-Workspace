package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.pageobject.*;
import java.util.concurrent.TimeUnit;

public class GMailTest {

    private WebDriver driver;

    @BeforeClass(description = "Starting browser")
    public void startBrowser() {
        System.out.println("Opening browser...");
        System.setProperty("webdriver.chrome.driver", getClass().getClassLoader().getResource("chromedriver.exe").getPath());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test(description = "Login to GMail account")
    public void loginToGMailAccount() {
        Assert.assertTrue(new LoginPage(driver).loginToGMailAccount().isComposeButtonDisplayed(), "You have not login to your GMail account.");
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String title = js.executeScript("return document.title;").toString();
        Assert.assertTrue(title.contains("cdpautomation9@gmail.com"), "Expected: [cdpautomation9@gmail.com]. Actual: ".concat(title));
    }

    @Test(description = "Check Bin folder is empty", dependsOnMethods = "loginToGMailAccount")
    public void cleanBinFolder() {
        Assert.assertTrue(new BinMailPage(driver).isBinEmpty());
    }

    @Test(description = "Create an email",  dependsOnMethods = "cleanBinFolder")
    public void createDraftEmail() {
        new MainPage(driver).createEmail();
        new MainPage(driver).saveAsDraft();
        Assert.assertTrue(new MainPage(driver).isDraftCreated(), "Draft email has not been created.");
    }

    @Test(description = "Save email as draft",  dependsOnMethods = "createDraftEmail")
    public void sendEmail() {
        new MainPage(driver).sendEmail();
        Assert.assertTrue(new MainPage(driver).isEmailSent());
    }

    @Test(description = "Delete emails from Sent folder",  dependsOnMethods = "sendEmail")
    public void workWithInboxEmail() {
        Assert.assertTrue(new InboxPage(driver).isEmailPresent());
    }
    
    @Test(description = "Save email as draft",  dependsOnMethods = "workWithInboxEmail", alwaysRun = true)
    public void logout() {
        Assert.assertTrue(new LogoutPage(driver).isLogout());
    }

    @AfterClass(description = "Stop browser")
    public void closeBrowser() {
        driver.quit();
        System.out.println("Browser was successfully closed.");
    }

}