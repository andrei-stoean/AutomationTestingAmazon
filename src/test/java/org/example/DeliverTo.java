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
import org.testng.annotations.Test;

import java.time.Duration;

public class DeliverTo {
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

    @BeforeClass
    private void setDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    private void closeDriver() {
        driver.quit();
    }

    @Test
    private void testChangingDeliverLocation() {
        openPage(AMAZON_URL);
        click(deliverToIcon);
        sendKeys(zipCodeInputField, LOS_ANGELES_ZIP_CODE);
        click(zipCodeApplyButton);
        click(continueButton);
        Assert.assertEquals(getElement(location).getText(), "Los Angeles 90001\u200C");
    }

    @Test
    private void testIfDeliverToPolandIsAvailable() {
        openPage(AMAZON_URL);
        click(deliverToIcon);
        click(countriesDropDown);
        Assert.assertEquals(getElement(poland).getText(), "Poland");
    }

    private void openPage(String url) {
        driver.get(url);
    }

    private void click(By locator) {
        getElement(locator).click();
    }

    private void sendKeys(By locator, String keys) {
        getElement(locator).sendKeys(keys);
    }

    private WebElement getElement(By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
