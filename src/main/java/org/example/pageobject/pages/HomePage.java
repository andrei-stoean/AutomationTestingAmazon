package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class HomePage extends BasePage {

    public static final String AMAZON_URL = "https://www.amazon.com/";
    @FindBy(id = "nav-global-location-popover-link")
    WebElement deliverToIcon;
    @FindBy(xpath = "//input[@data-action='GLUXPostalInputAction']")
    WebElement zipCodeInputField;
    @FindBy(xpath = "//input[@aria-labelledby='GLUXZipUpdate-announce']")
    WebElement zipCodeApplyButton;
    @FindBy(xpath = "//div[@class='a-popover-footer']//input[@id='GLUXConfirmClose']")
    WebElement continueButton;
    @FindBy(id = "glow-ingress-line2")
    WebElement locationLabel;
    @FindBy(xpath = "//a[@data-value='{\"stringVal\":\"PL\"}']")
    WebElement poland;
    @FindBy(xpath = "//a[contains(@id,\"GLUXCountryList\")]")
    List<WebElement> shipToCountries;
    @FindBy(xpath = "//button[@name='glowDoneButton']")
    WebElement doneButton;
    @FindBy(xpath = "//h2[text()='Shop by Category' or text()='Gaming accessories']/../following-sibling::div//div[contains(@class, 'quadrant-container')] | //img[@alt='Electronics']")
    List<WebElement> categories;
    By countriesDropDown = By.xpath("//span[@data-action='a-dropdown-button']");
    @FindBy(xpath = "//a[@aria-label=\"Computers & Accessories\"]")
    WebElement computersCategory;

    @FindBy(id = "twotabsearchtextbox")
    WebElement searchBar;

    @FindBy(id ="nav-search-submit-text")
    WebElement searchButton;


    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public HomePage open() {
        webDriver.get(AMAZON_URL);
        webDriver.manage().deleteAllCookies();
        return this;
    }

    public String findDeliverToLocation() {
        return locationLabel.getText();
    }

    public void clickCountriesDropDown() {
        var dropDown = new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(countriesDropDown));
        dropDown.click();
    }

    public HomePage waitForPageLoad() {
        new WebDriverWait(webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.stalenessOf(webDriver.findElement(By.id("nav-global-location-popover-link"))));
        return this;
    }

    public ResultsPage selectAProductCategory() {
        categories.get(new Random().nextInt(categories.size())).click();
        return new ResultsPage(webDriver);
    }

    public List<String> findCountriesAvailableForDelivery() {
        deliverToIcon.click();
        clickCountriesDropDown();
        return shipToCountries.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public HomePage SearchGivenText(String toSearch) {
        searchBar.sendKeys(toSearch);
        searchButton.click();
        return this;
    }

    public HomePage changeDeliveryLocationByProvidingZipCode(String zipCode) {
        deliverToIcon.click();
        zipCodeInputField.sendKeys(zipCode);
        zipCodeApplyButton.click();
        continueButton.click();
        return this;
    }

    public HomePage changeDeliveryCountryToPoland() {
        deliverToIcon.click();
        clickCountriesDropDown();
        poland.click();
        doneButton.click();
        return this;
    }

    public ResultsPage clickComputersCategory(){
        computersCategory.click();
        return new ResultsPage(webDriver);
    }
}
