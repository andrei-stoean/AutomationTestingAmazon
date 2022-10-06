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

    @BeforeClass
    private void setDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterClass
    private void closeDriver() {
        driver.quit();
    }

    @Test
    private void changeDeliverLocation() throws InterruptedException {
        openAmazonWebPage();
        driver.manage().window().maximize();
        clickDeliverToIcon();
        enterZipCode();
        clickApplyBtn();
        clickContinueBtn();
        Assert.assertEquals(getUpdatedLocation().getText(), "Los Angeles 90001\u200C", "Wrong Location");
    }

    @Test
    private void checkIfPolandIsPresent() {
        openAmazonWebPage();
        driver.manage().window().maximize();
        clickDeliverToIcon();
        clickCountryDropDown();
        findPoland();
    }

    private void findPoland() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Poland']")));
    }

    private void clickCountryDropDown() {
        WebElement countryDropDown = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-action='a-dropdown-button']")));
        countryDropDown.click();
    }

    private WebElement getUpdatedLocation() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Los Angeles 90001')]")));
    }

    private void openAmazonWebPage() {
        driver.get("https://www.amazon.com/");
    }

    private void clickContinueBtn() {
        WebElement continueBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='a-popover-footer']//input[@id='GLUXConfirmClose']")));
        continueBtn.click();
    }

    private void clickApplyBtn() {
        WebElement zipCodeApplyBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-labelledby='GLUXZipUpdate-announce']")));
        zipCodeApplyBtn.click();
    }

    private void enterZipCode() {
        WebElement zipCodeInputField = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("GLUXZipUpdateInput")));
        zipCodeInputField.sendKeys("90001");
    }

    private void clickDeliverToIcon() {
        WebElement deliverToIcon = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-global-location-popover-link")));
        deliverToIcon.click();
    }
}
