package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

    @FindBy(xpath = "//span[contains(text(), 'No Import')] | //div[@id='contextualIngressPtLabel_deliveryShortLine']/span[2]")
    WebElement country;
    @FindBy(id = "add-to-cart-button")
    WebElement addButton;
    @FindBy(xpath = "//*[@id=\"NATC_SMART_WAGON_CONF_MSG_SUCCESS\"]/span")
    WebElement addedToCartLabel;
    @FindBy(xpath = "//*[@id=\"nav-cart-count\"]")
    WebElement cartNumber;
    @FindBy(id="nav-cart")
    WebElement cart;
    public ProductPage(WebDriver webDriver) {
        super(webDriver);
    }

    public String getShipToCountry() {
        return country.getAttribute("textContent");
    }

    public ProductPage addToCart(){
        addButton.click();
        return this;
    }
    public String getAddToCartMessage(){
        return addedToCartLabel.getText();
    }
    public String getCartNumber(){
        return cartNumber.getText();
    }

    public CartPage clickOnCart(){
        cart.click();
        return new CartPage(webDriver);
    }

}
