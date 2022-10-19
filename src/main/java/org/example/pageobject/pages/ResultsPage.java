package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.transform.Result;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ResultsPage extends BasePage {

    @FindBy(xpath = "//span[@class='a-size-base-plus a-color-base a-text-normal' or @class='a-size-medium a-color-base a-text-normal']")
    List<WebElement> results;
    @FindBy(xpath = "//span[text()='Featured Brands' or text()='Brands']/../..//span[@class='a-size-base a-color-base' and not(ancestor::div[@class='a-row a-expander-container a-expander-extend-container'])]")
    List<WebElement> featuredBrands;
    @FindBy(xpath = "//input[@checked]/../../following-sibling::span")
    WebElement selectedBrand;
    @FindBy(xpath = "//div[@data-component-type='s-search-result']//span[@data-a-color='base']/span[@class='a-offscreen']")
    List<WebElement> priceResults;
    @FindBy(id = "low-price")
    WebElement minPriceField;
    @FindBy(id = "high-price")
    WebElement maxPriceField;
    @FindBy(xpath = "//input[@class='a-button-input']")
    WebElement goButton;
    @FindBy(xpath = "//span[@data-csa-c-func-deps='aui-da-a-dropdown-button']")
    WebElement sortByDropDown;
    @FindBy(id = "s-result-sort-select_1")
    WebElement lowToHigh;
    @FindBy(id = "s-result-sort-select_2")
    WebElement highToLow;
    @FindBy(xpath = "//span[@class=\"a-size-medium a-color-link a-text-bold\"]")
    WebElement seeAllResults;
    @FindBy(xpath = "//*[contains(text(),'Seagate Portable')]")
    WebElement openItem;

    public ResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public ProductPage clickRandomResult() {
        results.get(new Random().nextInt(results.size())).click();
        return new ProductPage(webDriver);
    }

    public ResultsPage clickRandomBrand() {
        featuredBrands.get(new Random().nextInt(featuredBrands.size())).click();
        return this;
    }

    public long getNumberOfProductsContainingBrandName() {
        var brandName = selectedBrand.getText();
        return results.stream()
                .map(WebElement::getText)
                .filter(productName -> productName.contains(brandName))
                .count();
    }

    public ResultsPage filterByPrice(int minValue, int maxValue) {
        minPriceField.sendKeys(String.valueOf(minValue));
        maxPriceField.sendKeys(String.valueOf(maxValue));
        goButton.click();
        return this;
    }

    public List<Double> getPriceResults() {
        return priceResults.stream()
                .map(elem -> elem.getAttribute("textContent"))
                .map(price -> Double.valueOf(price.substring(1)))
                .collect(Collectors.toList());
    }

    public ResultsPage clickSortByDropDown() {
        sortByDropDown.click();
        return this;
    }

    public ResultsPage clickLowToHigh() {
        lowToHigh.click();
        return this;
    }

    public ResultsPage clickHighToLow() {
        highToLow.click();
        return this;
    }
    public ResultsPage clickSeeAllResults(){
        seeAllResults.click();
        return new ResultsPage(webDriver);
    }
    public ProductPage clickOnItem(){
        openItem.click();
        return new ProductPage(webDriver);
    }
    public ResultsPage waitItemToBeClickable(){
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.xpath("//*[contains(text(),'Seagate Portable')]"))));
        return this;
    }
}
