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


public class SearchFieldTests {
    private WebDriver webDriver;

    @BeforeClass
    public void setDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
        webDriver = new ChromeDriver();
    }

    @Test
    public void searchFieldWritingTest() {
        openAmazonWebPage();
        String randomTextForSearch= "kgvgvkyuoum,8lhlmu,ybjhuvbhjvlb.hylubyhv;bhjlv,khn;biuh";
        WebElement searchBar = webDriver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys(randomTextForSearch);

        WebElement searchButton = webDriver.findElement(By.id("nav-search-submit-text"));
        searchButton.click();

        WebElement textErorr = webDriver.findElement(By.xpath("//div[@class='a-row']"));
        Assert.assertEquals(String.format("No results for %s.", randomTextForSearch), textErorr.getText());

    }


    @Test
    public void searchFieldInfo() {
        openAmazonWebPage();
        WebElement searchBar = webDriver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys("laptop");

        WebElement searchResultsMessage = webDriver.findElement(By.id("nav-search-submit-text"));
        searchResultsMessage.click();

        WebElement textEqualsWithKey = webDriver.findElement(By.xpath("//span[@class=\"a-color-state a-text-bold\"]"));
        Assert.assertEquals("\"laptop\"", textEqualsWithKey.getText());

    }

    @Test
    public void searchResultsForSpecificKey() {
        openAmazonWebPage();
        WebElement searchBar = webDriver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys("laptop");

        WebElement searchResultsMessage = webDriver.findElement(By.id("nav-search-submit-text"));
        searchResultsMessage.click();

        List<WebElement> searchlist = webDriver.findElements(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));
        Assert.assertTrue( SpecificWord(searchlist));
    }

    private boolean  SpecificWord (List<WebElement> results) {
        return results.stream().map(WebElement::getText).anyMatch(x -> x.toLowerCase().contains("laptop"));
    }

    @AfterClass
    public void closeDriver() {
        webDriver.quit();
    }

    public void openAmazonWebPage() {
        webDriver.get("https://www.amazon.com/");
        webDriver.manage().window().maximize();
    }

}