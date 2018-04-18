package pages.pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import org.apache.commons.io.FileUtils;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public abstract class ParentPage {

    protected WebDriver driver;
    private WebDriverWait waiter;
    private Wait wait;

    public ParentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public void highlightElement(WebElement element) {
        String bg = element.getCssValue("backgroundColor");
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].style.backgroundColor = '" + "yellow" + "'", element);
        makeScreenshot();
        js.executeScript("arguments[0].style.backgroundColor = '" + bg + "'", element);
    }

    private void makeScreenshot() {
        try{
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFileToDirectory(screenshot, new File("src/screenshots"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public WebDriverWait waitForElement(String xpath){
        if(waiter == null){
            waiter = new WebDriverWait(driver, 5);
            waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        } else{
            waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        }
        return waiter;
    }

    public Wait waitForElementFluent(WebElement element, WebDriver driver, String xpath){
        if(wait == null){
            wait = new FluentWait(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
            element = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
                @Override
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.xpath(xpath));
                }
            });
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        }
        return wait;
    }

    public void checkUrl(String expectedValue) {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String url = js.executeScript("return document.URL;").toString();
        for(int i = 0; i < 20; i++){
            if(!url.contains(expectedValue)){
                try {
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                url = js.executeScript("return document.URL;").toString();
            }
        }
    }

}
