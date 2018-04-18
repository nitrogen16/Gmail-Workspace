package pages.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import enums.FoldersEnum;
import java.util.List;

public class SentMailPage extends ParentPage{

    @FindBy(linkText = "Sent Mail")
    private WebElement sentEmailsLink;
    @FindBy(xpath = "//span[@class = 'bog' and text() = 'Test Email']")
    private WebElement emailLabel;
    @FindBy(xpath = "//div[contains(text(), 'Delete')]")
    private WebElement deleteContextMenuButton;
    @FindBy(xpath = "//div[@role='checkbox']//div[1]")
    private WebElement checkbox;

    public SentMailPage(WebDriver driver) {
        super(driver);
    }

    public boolean isEmailDeletedFromSentMailFolder() {
        highlightElement(sentEmailsLink);
        sentEmailsLink.click();
        checkUrl(FoldersEnum.SENT_MAIL.url());
        waitForElementFluent(emailLabel, driver, "//span[@class = 'bog' and text() = 'Test Email']");
        clickCheckbox("//div[@role='checkbox']//div[1]");
        WebElement draggable = driver.findElement(By.xpath("//span[@email ='cdpautomation9@gmail.com' and @name = 'me']"));
        WebElement droppable = driver.findElement(By.linkText("Inbox (1)"));
        new Actions(driver).dragAndDrop(draggable, droppable).build().perform();
        waitForElement("//span[contains(text(), 'The conversation has been moved to the Inbox.')]");
        rightClickOnElement("//span[@email ='cdpautomation9@gmail.com' and @name = 'me']");
        clickDelete();
        waitForElement("//button[@name = 'ok']");
        driver.findElement(By.xpath("//button[@name = 'ok']")).click();
        waitForElement("//span[contains(text(), 'The conversation has been moved to the Bin.')]");
        return driver.findElement(By.xpath("//span[contains(text(), 'The conversation has been moved to the Bin.')]")).isDisplayed();
    }

    private void rightClickOnElement(String xpath){
        List<WebElement> list = driver.findElements(By.xpath(xpath));
        if(list.size() == 1){
            highlightElement(list.get(0));
            new Actions(driver).contextClick(list.get(0)).build().perform();
        }else{
            highlightElement(list.get(1));
            new Actions(driver).contextClick(list.get(1)).build().perform();
        }
    }

    private void clickDelete(){
        List<WebElement> list = driver.findElements(By.xpath("//div[contains(text(), 'Delete')]"));
        for(WebElement e : list){
            if(e.isDisplayed()){
                highlightElement(e);
                e.click();
                break;
            }
        }
    }

    private void clickCheckbox(String xpath){
        List<WebElement> list = driver.findElements(By.xpath(xpath));
        for(WebElement e : list){
            if(e.isDisplayed()){
                highlightElement(e);
                e.click();
                break;
            }
        }
    }

}