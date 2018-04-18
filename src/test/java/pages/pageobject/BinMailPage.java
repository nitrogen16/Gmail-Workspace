package pages.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import enums.FoldersEnum;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class BinMailPage extends ParentPage {

    @FindBy(xpath = "//span[@role = 'button']//span[contains(text(), 'More')]")
    private WebElement more;

    @FindBy(xpath = "//span[contains(text(), 'More labels')]")
    private WebElement moreLabels;

    public BinMailPage(WebDriver driver) {
        super(driver);
    }

    public boolean isBinEmpty() {
        driver.findElement(By.linkText("Inbox")).click();
        if(driver.toString().contains("MicrosoftEdge")){
            moreLabels.click();
        } else{
            more.click();
        }
        driver.findElement(By.linkText("Bin")).click();
        checkUrl(FoldersEnum.BIN.url());
        waitForElement("//div[contains(text(), 'messages that have been in Bin for more than 30 days will be automatically deleted')]");
        List<WebElement> deleteLinks = driver.findElements(By.xpath("//img[@src = 'images/cleardot.gif' and @alt = 'Bin']"));
        if(!deleteLinks.isEmpty()){
            WebElement contextMenu = driver.findElement(By.xpath("//span[@class = 'bog']"));
            highlightElement(contextMenu);
            new Actions(driver).contextClick(contextMenu).build().perform();
            driver.findElement(By.xpath("//div[contains(text(), 'Delete forever')]")).click();
            waitForElement("//span[contains(text(), 'The conversation has been deleted.')]");
        }
        deleteLinks = driver.findElements(By.xpath("//img[@src = 'images/cleardot.gif' and @alt = 'Bin']"));
        return deleteLinks.isEmpty() ? true : false;
    }

}
