package pages.pageobject;

import enums.FoldersEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InboxPage extends ParentPage{

    @FindBy(linkText = "Inbox (1)")
    private WebElement inboxLinkUnvisited;

    @FindBy(linkText = "Inbox")
    private WebElement inboxLinkVisited;

    @FindBy(xpath = "//span[@class = 'bog' and text() = 'Test Email']")
    private WebElement emailLabel;
    
    public InboxPage(WebDriver driver) {
        super(driver);
    }

    public boolean isEmailPresent() {
        highlightElement(inboxLinkUnvisited);
        inboxLinkUnvisited.click();
        checkUrl(FoldersEnum.INBOX.url());
        waitForElementFluent(emailLabel, driver, "//span[@class = 'bog' and text() = 'Test Email']");
        driver.findElement(By.xpath("//span[@class = 'bog']/b[text() = 'Test Email']")).click();
        driver.getPageSource().contains("Hello, world!");
        driver.findElement(By.xpath("//div[@aria-label = 'More']")).click();
        waitForElement("//div[text() = 'Delete this message']");
        driver.findElement(By.xpath("//div[text() = 'Delete this message']")).click();
        
        return driver.findElement(By.xpath("//span[contains(text(), 'The conversation has been moved to the Bin.')]")).isDisplayed();
    }
    
}
