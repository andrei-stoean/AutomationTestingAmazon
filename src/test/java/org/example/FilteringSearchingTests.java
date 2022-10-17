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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class FilteringSearchingTests {
    private WebDriver driver;
    private static final String AMAZON_URL = "https://www.amazon.com/";
    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private final By categories = By.xpath("//h2[text()='Shop by Category' or text()='Gaming accessories']/../following-sibling::div//div[contains(@class, 'quadrant-container')] | //img[@alt='Electronics']");
    private final By featuredBrands = By.xpath("//span[text()='Featured Brands' or text()='Brands']/../..//span[@class='a-size-base a-color-base' and not(ancestor::div[@class='a-row a-expander-container a-expander-extend-container'])]");
    private final By searchResults = By.xpath("//span[@data-component-type='s-search-results' and not(contains(@class, 'AdHolder'))]//span[@class='a-size-base-plus a-color-base a-text-normal' or @class='a-size-medium a-color-base a-text-normal']");
    private final By minField = By.id("low-price");
    private final By maxField = By.id("high-price");
    private final By goBtn = By.xpath("//input[@class='a-button-input']");
    private final By priceResults = By.xpath("//div[@data-component-type='s-search-result']//span[@data-a-color='base']/span[@class='a-offscreen']");
    private final By checkedBrand = By.xpath("//input[@checked]/../../following-sibling::span");
    private final By sortByDropDown = By.xpath("//span[@data-csa-c-func-deps='aui-da-a-dropdown-button']");
    private final By lowToHigh = By.id("s-result-sort-select_1");
    private final By highToLow = By.id("s-result-sort-select_2");


    @BeforeClass
    private void setDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    private void closeDriver() {
        driver.quit();
    }

    @BeforeMethod
    public void clickACategoryAndChooseABrand() {
        openPage(AMAZON_URL);
        clickRandomElement(categories);
        clickRandomElement(featuredBrands);
    }

    @Test
    public void testSearchingBrandedItems() {
        Assert.assertTrue(doMostResultsContainBrandName());
    }

    @Test
    private void testSearchingWithinPriceRange() {
        int minValue = new Random().nextInt(10);
        int maxValue = new Random().nextInt(minValue, 200);
        filterResultsByPriceRange(minValue, maxValue);
        Assert.assertTrue(areMostPricesWithinRange(minValue, maxValue));
    }

    @Test
    private void testSortingResultsByPrice() {
        click(sortByDropDown);
        click(lowToHigh);
        Assert.assertTrue(areResultsCorrectlySorted(Comparator.naturalOrder()));
        click(sortByDropDown);
        click(highToLow);
        Assert.assertTrue(areResultsCorrectlySorted(Comparator.reverseOrder()));
    }

    private boolean doMostResultsContainBrandName() {
        String brandName = getElement(checkedBrand).getText().toLowerCase();
        List<WebElement> results = getElements(searchResults);
        if (results.size() == 0) throw new AssertionError("Result list is empty");
        long numberOfResultsThatContainBrandName = results.stream()
                .map(WebElement::getText)
                .map(String::toLowerCase)
                .filter(elem -> elem.contains(brandName))
                .count();
        return numberOfResultsThatContainBrandName >= results.size() / 2;
    }

    private boolean areMostPricesWithinRange(int minValue, int maxValue) {
        List<WebElement> results = getElements(priceResults);
        if (results.size() == 0) throw new AssertionError("Result list is empty");
        long numberOfPricesWithinRange = results.stream()
                .map(elem -> elem.getAttribute("textContent"))
                .map(price -> Double.valueOf(price.substring(1)))
                .filter(price -> price >= minValue && price <= maxValue)
                .count();
        return numberOfPricesWithinRange >= results.size() / 2;
    }

    private boolean areResultsCorrectlySorted(Comparator<Double> comparator) {
        List<Double> results = getElements(priceResults).stream()
                .map(elem -> elem.getAttribute("textContent"))
                .map(price -> Double.valueOf(price.substring(1)))
                .toList();
        return results.equals(results.stream().sorted(comparator).toList());
    }

    private void filterResultsByPriceRange(int minValue, int maxValue) {
        sendKeys(minField, String.valueOf(minValue));
        sendKeys(maxField, String.valueOf(maxValue));
        click(goBtn);
    }

    private WebElement getElement(By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private List<WebElement> getElements(By locator) {
        return new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    private WebElement getRandomElement(By locator) {
        List<WebElement> categories = getElements(locator);
        return getElements(locator).get(new Random().nextInt(categories.size()));
    }

    private void openPage(String url) {
        driver.get(url);
    }

    private void sendKeys(By locator, String keys) {
        getElement(locator).sendKeys(keys);
    }

    private void click(By locator) {
        getElement(locator).click();
    }

    private void clickRandomElement(By locator) {
        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(getRandomElement(locator)))
                .click();
    }
}
