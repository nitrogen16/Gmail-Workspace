package pages.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends ParentPage {

    private static String COMPOSE_BUTTON_XPATH = "//div[text() = 'COMPOSE']";
    private static String SEND_TO_XPATH = "//textarea[@aria-label = 'To']";
    private static String SUBJECT_INPUT_XPATH = "//input[@name='subjectbox']";
    private static String MESSAGE_BODY_INPUT_XPATH = "//div[@aria-label='Message Body']";
    private static String SAVE_AS_DRAFT_BUTTON_XPATH = "//img[@alt = 'Close' and @aria-label = 'Save & Close']";
    private static String DRAFT_EMAIL_LINK_XPATH = "//table/tbody/tr[@class = 'zA yO']//descendant-or-self::span[text() = 'Test Email']";
    private static String SEND_BUTTON_XPATH = "//div[@role = 'button' and @aria-label='Send \u202A(Ctrl-Enter)\u202C']";

    private WebElement sendToInput;
    private WebElement subjectInput;
    private WebElement messageInput;
    
    private WebElement draftEmail;
    private WebElement sendButton;

    @FindBy(xpath = "//div[text() = 'COMPOSE']")
    private WebElement composeButton;

    @FindBy(xpath = "//a[contains(text(), 'Drafts')]")
    private WebElement draftsLink;

    @FindBy(xpath = "//img[@alt = 'Close' and @aria-label = 'Save & Close']")
    private WebElement saveAsDraftButton;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public boolean isComposeButtonDisplayed(){
        waitForElement(COMPOSE_BUTTON_XPATH);
        return driver.findElement(By.xpath(COMPOSE_BUTTON_XPATH)).isDisplayed();
    }

    public void createEmail() {
        highlightElement(composeButton);
        composeButton.click();
        waitForElement("//div[text()='New Message']");

        sendToInput = driver.findElement(By.xpath(SEND_TO_XPATH));
        highlightElement(sendToInput);
        sendToInput.sendKeys("cdpautomation9@gmail.com");

        subjectInput = driver.findElement(By.xpath(SUBJECT_INPUT_XPATH));
        highlightElement(subjectInput);
        subjectInput.sendKeys("Test Email");

        messageInput = driver.findElement(By.xpath(MESSAGE_BODY_INPUT_XPATH));
        highlightElement(messageInput);
        messageInput.sendKeys("Hello, world!");
    }

    public void saveAsDraft() {
//        save email as a draft
        highlightElement(saveAsDraftButton);
        saveAsDraftButton.click();
    }
    public boolean isDraftCreated() {
        draftsLink.click();
        draftEmail = driver.findElement(By.xpath(DRAFT_EMAIL_LINK_XPATH));
        draftEmail.click();

        return driver.findElement(By.xpath("//span[@class='vN bfK a3q']")).getAttribute("email").equals("cdpautomation9@gmail.com") &&
                driver.findElement(By.xpath("//input[@name = 'subject' and @value='Test Email']")).getAttribute("value").equals("Test Email");
    }

    public void sendEmail() {
        sendButton = driver.findElement(By.xpath(SEND_BUTTON_XPATH));
        highlightElement(sendButton);
        sendButton.click();
        waitForElement("//div[contains(text(), 'has been sent')]");
    }


    public boolean isEmailSent(){
        return driver.findElement(By.xpath("//div[contains(text(), 'has been sent')]")).isDisplayed();
    }

}
