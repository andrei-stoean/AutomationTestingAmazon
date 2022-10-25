package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ResultsPage extends BasePage {

    @FindBy(xpath = "//span[@class='a-size-base-plus a-color-base a-text-normal' or @class='a-size-medium a-color-base a-text-normal']")
    List<WebElement> results;
    @FindBy(xpath = "//span[text()='Featured Brands' or text()='Brands']/../following-sibling::ul//a")
    List<WebElement> brands;
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

    @FindBy(xpath = "//div[@class='a-row']")
    WebElement textErorr;

    @FindBy(xpath = "//span[@class=\"a-color-state a-text-bold\"]")
    WebElement textEqualsWithKey;

    public ResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public ProductPage selectARandomProduct() {
        results.get(new Random().nextInt(results.size())).click();
        return new ProductPage(webDriver);
    }

    public ResultsPage selectARandomBrand() {
        WebElement brand = brands.stream()
                .filter(WebElement::isDisplayed)
                .findAny().orElseThrow();
        brand.click();
        return this;
    }

    public long findNumberOfProductsContainingBrandName() {
        var brandName = selectedBrand.getText();
        return results.stream()
                .map(WebElement::getText)
                .filter(productName -> productName.contains(brandName))
                .count();
    }

    public ResultsPage filterResultsByPrice(int minValue, int maxValue) {
        minPriceField.sendKeys(String.valueOf(minValue));
        maxPriceField.sendKeys(String.valueOf(maxValue));
        goButton.click();
        return this;
    }

    public List<Double> findPriceResults() {
        return priceResults.stream()
                .map(elem -> elem.getAttribute("textContent"))
                .map(price -> Double.valueOf(price.substring(1)))
                .collect(Collectors.toList());
    }

    public ResultsPage sortResultsByPriceIncreasing() {
        sortByDropDown.click();
        lowToHigh.click();
        return this;
    }

    public List<String> resultsList() {
        return results.stream()
                .map(WebElement::getText).collect(Collectors.toList());
    }

    public ResultsPage sortResultsByPriceDecreasing() {
        sortByDropDown.click();
        highToLow.click();
        return this;
    }

    public ResultsPage clickSeeAllResults() {
        seeAllResults.click();
        return new ResultsPage(webDriver);
    }

    public String searchSpecificWordResults() {
        return textEqualsWithKey.getText();
    }

    public ProductPage clickOnItem() {
        openItem.click();
        return new ProductPage(webDriver);
    }

    public String getIncorectMessage() {
        return textErorr.getText();
    }
}
