package org.example.pageobject.pages;

import org.checkerframework.checker.units.qual.C;
import org.example.pageobject.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage extends BasePage {
    @FindBy(xpath = "//input[@value='Delete']")
    WebElement delete;
    @FindBy(xpath = "//div[@class=\"a-row sc-cart-header\"]/div/h1")
    WebElement cartMessage;
    @FindBy(xpath = "//span[@id='sc-subtotal-amount-activecart']/span")
    WebElement price;
    public CartPage(WebDriver webDriver) {
        super(webDriver);
    }

    public CartPage deleteItem(){
        delete.click();
        return new CartPage(webDriver);
    }
    public String getCartMessage(){
        return cartMessage.getText();
    }

    public CartPage waitForCartStatusText() {
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.textToBe(By.xpath("//div[@class=\"a-row sc-cart-header\"]/div/h1"),"Your Amazon Cart is empty."));
        return this;
    }

    public String getPrice(){
        return price.getText();
    }
}
