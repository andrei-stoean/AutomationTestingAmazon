package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class DeliverToTests {
    private WebDriver driver;
    private static final String AMAZON_URL = "https://www.amazon.com/";
    private static final String LOS_ANGELES_ZIP_CODE = "90001";
    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private final By deliverToIcon = By.id("nav-global-location-popover-link");
    private final By zipCodeInputField = By.id("GLUXZipUpdateInput");
    private final By zipCodeApplyButton = By.xpath("//input[@aria-labelledby='GLUXZipUpdate-announce']");
    private final By continueButton = By.xpath("//div[@class='a-popover-footer']//input[@id='GLUXConfirmClose']");
    private final By countriesDropDown = By.xpath("//span[@data-action='a-dropdown-button']");
    private final By location = By.xpath("//span[contains(text(), 'Los Angeles 90001')]");
    private final By poland = By.xpath("//a[@data-value='{\"stringVal\":\"PL\"}']");
    private final By doneBtn = By.xpath("//button[@name='glowDoneButton']");
    private final By categories = By.xpath("//h2[text()='Shop by Category' or text()='Gaming accessories']/../following-sibling::div//div[contains(@class, 'quadrant-container')] | //img[@alt='Electronics']");
    private final By searchResults = By.xpath("//span[@class='a-size-base-plus a-color-base a-text-normal' or @class='a-size-medium a-color-base a-text-normal']");
    private final By shipToCountry = By.xpath("//span[contains(text(), 'No Import')] | //div[@id='contextualIngressPtLabel_deliveryShortLine']/span[2]");

    @BeforeClass
    public void setDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @AfterClass
    public void closeDriver() {
        driver.quit();
    }

    @BeforeMethod
    public void clickDeliverToIcon() {
        openPage(AMAZON_URL);
        click(deliverToIcon);
    }

    @Test
    private void testChangingDeliverLocation() {
        sendKeys(zipCodeInputField, LOS_ANGELES_ZIP_CODE);
        click(zipCodeApplyButton);
        click(continueButton);
        Assert.assertEquals(getElement(location).getText(), "Los Angeles 90001\u200C");
    }

    @Test
    private void testIfDeliverToPolandIsAvailable() {
        click(countriesDropDown);
        Assert.assertEquals(getElement(poland).getText(), "Poland");
    }

    @Test
    private void testChangingDeliverToCountry() {
        click(countriesDropDown);
        click(poland);
        click(doneBtn);
        waitForPageRefresh();
        clickRandomElement(categories);
        clickRandomElement(searchResults);
        Assert.assertTrue(getElement(shipToCountry).getAttribute("textContent").contains("Poland"));
    }

    private WebElement getElement(By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    private List<WebElement> getElements(By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    private WebElement getRandomElement(By locator) {
        List<WebElement> categories = getElements(locator);
        return getElements(locator).get(new Random().nextInt(categories.size()));
    }

    private void openPage(String url) {
        driver.get(url);
    }

    private void sendKeys(By locator, String keys) {
        getElement(locator).sendKeys(keys);
    }

    private void click(By locator) {
        getElement(locator).click();
    }

    private void clickRandomElement(By locator) {
        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(getRandomElement(locator)))
                .click();
    }

    private void waitForPageRefresh() {
        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.stalenessOf(getElement(deliverToIcon)));
    }
}
