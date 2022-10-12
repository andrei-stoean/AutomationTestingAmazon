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

    @Test
    public void verifyAddFunctionality() {

        WebElement electronics = webDriver.findElement(By.xpath("//img[@alt='Electronics']"));
        electronics.click();

        WebElement computers = webDriver.findElement(By.xpath("//span[contains(text(),'Computers')]"));
        computers.click();


        WebElement openItem = webDriver.findElement(By.xpath("//*[contains(text(),'Seagate Portable')]"));
        openItem.click();


        WebElement addButton = webDriver.findElement(By.id("add-to-cart-button"));
        addButton.click();


        WebElement addedToCart = webDriver.findElement(By.xpath("//*[@id=\"NATC_SMART_WAGON_CONF_MSG_SUCCESS\"]/span"));
        Assert.assertEquals("Added to Cart", addedToCart.getText());

        WebElement cartNumber = webDriver.findElement(By.xpath("//*[@id=\"nav-cart-count\"]"));
        Assert.assertEquals("1", cartNumber.getText());


    }


    @Test
    public void verifyRemoveFunctionality() {

        WebElement electronics = webDriver.findElement(By.xpath("//img[@alt='Electronics']"));
        electronics.click();

        WebElement computers = webDriver.findElement(By.xpath("//span[contains(text(),'Computers')]"));
        computers.click();


        WebElement openItem = webDriver.findElement(By.xpath("//*[contains(text(),'Seagate Portable')]"));
        openItem.click();


        WebElement addButton = webDriver.findElement(By.id("add-to-cart-button"));
        addButton.click();


        WebElement addedToCart = webDriver.findElement(By.xpath("//*[@id=\"NATC_SMART_WAGON_CONF_MSG_SUCCESS\"]/span"));
        Assert.assertEquals("Added to Cart", addedToCart.getText());

        WebElement cartNumber = webDriver.findElement(By.xpath("//*[@id=\"nav-cart-count\"]"));
        Assert.assertEquals("1", cartNumber.getText());

        //-----

        WebElement clickOnCart = webDriver.findElement(By.id("nav-cart"));
        clickOnCart.click();


        WebElement deleteItem = webDriver.findElement(By.xpath("//input[@value='Delete']"));
        deleteItem.click();


        WebElement emptyCartText = new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Your Amazon Cart is empty.')]")));
        Assert.assertEquals(emptyCartText.getText(), "Your Amazon Cart is empty.");

        WebElement price = webDriver.findElement(By.xpath("//span[@id='sc-subtotal-amount-activecart']/span"));
        Assert.assertEquals(price.getText(), "$0.00");


    }

    @AfterMethod
    public void closeDriver() {
        webDriver.quit();
    }
}
