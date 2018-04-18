package pages.pageobject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Utils.GMailUtils;

public class LoginPage extends ParentPage {

    private static String URL = "http:\\gmail.com";

    @FindBy(xpath = "//input[@type='email']")
    private WebElement loginInput;
    @FindBy(xpath = "//span[text()='Next']")
    private WebElement nextButton;
    @FindBy(name = "password")
    private WebElement passwordInput;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public MainPage loginToGMailAccount() {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        driver.get(URL);
        waitForElement("//h1[text()='Sign in']");
        js.executeScript("document.getElementById('identifierId').value = 'cdpautomation9'");
        highlightElement(nextButton);
        nextButton.click();
        waitForElement("//h1[text()='Welcome']");
        highlightElement(passwordInput);
        js.executeScript("document.querySelector('#password > div.aCsJod.oJeWuf > div > div.Xb9hP > input').value = '" +
                new GMailUtils().getCredentials().get("cdpautomation9@gmail.com") + "'");
        highlightElement(nextButton);
        nextButton.click();
        return new MainPage(driver);
    }

}
