package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;


public class SearchField {
    private WebDriver webDriver;

    @BeforeClass
    private void setDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
        webDriver = new ChromeDriver();

    }

    @Test
    private void searchFieldWritingTest() {
        openAmazonWebPage();
        String textTry= "kgvgvkyuoum,8lhlmu,ybjhuvbhjvlb.hylubyhv;bhjlv,khn;biuh";
        WebElement searchBar = webDriver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys(textTry);

        WebElement searchButton = webDriver.findElement(By.id("nav-search-submit-text"));
        searchButton.click();

        WebElement textErorr = webDriver.findElement(By.xpath("//div[@class='a-row']"));
        Assert.assertEquals(String.format("No results for %s.", textTry), textErorr.getText());

    }


    @Test
    private void searchFieldInfo() {
        openAmazonWebPage();
        WebElement searchBar = webDriver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys("laptop");

        WebElement searchButton = webDriver.findElement(By.id("nav-search-submit-text"));
        searchButton.click();

        WebElement textEqualsWithKey = webDriver.findElement(By.xpath("//span[@class=\"a-color-state a-text-bold\"]"));
        Assert.assertEquals("\"laptop\"", textEqualsWithKey.getText());

    }

    @Test
    private void searchResultsForSpecificKey() {
        openAmazonWebPage();
        WebElement searchBar = webDriver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys("laptop");

        WebElement searchButton = webDriver.findElement(By.id("nav-search-submit-text"));
        searchButton.click();

        List<WebElement> searchlist = webDriver.findElements(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));
        Assert.assertTrue(searchForKey(searchlist));
    }

    private boolean searchForKey (List<WebElement> results) {
        return results.stream().map(WebElement::getText).anyMatch(x -> x.toLowerCase().contains("laptop"));
    }

    @AfterClass
    private void closeDriver() {
        webDriver.quit();
    }

    private void openAmazonWebPage() {
        webDriver.get("https://www.amazon.com/");
        webDriver.manage().window().maximize();
    }

}