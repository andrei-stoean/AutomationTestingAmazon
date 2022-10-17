package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class CategoryResultsPage extends BasePage {
    @FindBy(xpath = "//span[@class='a-size-base-plus a-color-base a-text-normal' or @class='a-size-medium a-color-base a-text-normal']")
    List<WebElement> results;

    public CategoryResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public ProductPage clickRandomResult() {
        results.get(new Random().nextInt(results.size())).click();
        return new ProductPage(webDriver);
    }
}
