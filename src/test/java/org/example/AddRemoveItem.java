package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class AddRemoveItem {
    public static final String computersXpath = "//a[@aria-label=\"Computers & Accessories\"]";
    public static final String seeAllResultsXpath = "//span[@class=\"a-size-medium a-color-link a-text-bold\"]";
    public static final String openItemXpath = "//*[contains(text(),'Seagate Portable')]";
    public static final String addId = "add-to-cart-button";
    public static final String addedToCartXpath = "//*[@id=\"NATC_SMART_WAGON_CONF_MSG_SUCCESS\"]/span";
    public static final String cartNumberXpath = "//*[@id=\"nav-cart-count\"]";
    public int duration = 5;

    private WebDriver webDriver;

    @BeforeMethod
    public void setUpDriver() {
        System.setProperty(
                "webdriver.chrome.driver",
                "src/test/resources/webdriver/chromedriver.exe"
        );

        webDriver = new ChromeDriver();

        webDriver.get("https://www.amazon.com/");
        webDriver.manage().window().maximize();
    }
    @AfterMethod
    public void closeDriver() {
        webDriver.quit();
    }

    @Test
    public void verifyAddFunctionality() {
        WebElement computers = webDriver.findElement(By.xpath(computersXpath));
        computers.click();

        WebElement seeAllResults = webDriver.findElement(By.xpath(seeAllResultsXpath));
        seeAllResults.click();

        WebElement openItem = webDriver.findElement(By.xpath(openItemXpath));
        openItem.click();

        WebElement addButton = webDriver.findElement(By.id(addId));
        addButton.click();

        WebElement addedToCart = webDriver.findElement(By.xpath(addedToCartXpath));
        Assert.assertEquals("Added to Cart", addedToCart.getText());

        WebElement cartNumber = webDriver.findElement(By.xpath(cartNumberXpath));
        Assert.assertEquals("1", cartNumber.getText());
    }
    @Test
    public void verifyRemoveFunctionality() {
        WebElement computers = webDriver.findElement(By.xpath(computersXpath));
        computers.click();

        WebElement seeAllResults = webDriver.findElement(By.xpath(seeAllResultsXpath));
        seeAllResults.click();

        WebElement openItem = webDriver.findElement(By.xpath(openItemXpath));
        openItem.click();

        WebElement addButton = webDriver.findElement(By.id(addId));
        addButton.click();

        WebElement addedToCart = webDriver.findElement(By.xpath(addedToCartXpath));
        Assert.assertEquals("Added to Cart", addedToCart.getText());

        WebElement cartNumber = webDriver.findElement(By.xpath(cartNumberXpath));
        Assert.assertEquals("1", cartNumber.getText());

        WebElement clickOnCart = webDriver.findElement(By.id("nav-cart"));
        clickOnCart.click();

        WebElement deleteItem = webDriver.findElement(By.xpath("//input[@value='Delete']"));
        deleteItem.click();

        WebElement emptyCartText = new WebDriverWait(webDriver, Duration.ofSeconds(duration))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"a-row sc-cart-header\"]/div/h1")));
        Assert.assertEquals(emptyCartText.getText(), "Your Amazon Cart is empty.");

        WebElement price = webDriver.findElement(By.xpath("//span[@id='sc-subtotal-amount-activecart']/span"));
        Assert.assertEquals(price.getText(), "$0.00");
    }

}
