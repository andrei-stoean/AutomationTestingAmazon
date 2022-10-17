package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {
    @FindBy(xpath = "//span[contains(text(), 'No Import')] | //div[@id='contextualIngressPtLabel_deliveryShortLine']/span[2]")
    WebElement country;
    public ProductPage(WebDriver webDriver) {
        super(webDriver);
    }

    public String getShipToCountry() {
        return country.getAttribute("textContent");
    }
}
