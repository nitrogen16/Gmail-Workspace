package pages.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogoutPage extends ParentPage{

    private final String GREETING_XPATH = "//h1[@id = 'headingText' and text() = 'Hi Test']";
    private final String LOGOUT_OPTIONS_XPATH = "//div[@aria-label = 'Account Information' and @aria-hidden = 'false']";

    @FindBy(xpath = "//span[@class = 'gb_db gbii']")
    private WebElement signOutOptions;
    
    @FindBy(xpath = "//*[@id='gb_71']")
    private WebElement logOutButton;
    
    @FindBy(xpath = "//h1[@id = 'headingText' and text() = 'Hi Test']")
    private WebElement signOutLabel;

    public LogoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLogout(){
        highlightElement(signOutOptions);
        signOutOptions.click();
        waitForElement(LOGOUT_OPTIONS_XPATH);
        highlightElement(logOutButton);
        logOutButton.click();
        waitForElement(GREETING_XPATH);
        highlightElement(signOutLabel);
        return signOutLabel.isDisplayed();
    }

}