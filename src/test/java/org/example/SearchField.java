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
import org.testng.annotations.Test;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SearchField {
    private WebDriver webDriver;

    @BeforeClass
    private void setDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
        webDriver = new ChromeDriver();
    }

    @Test
    private void searchFieldWrite() {
        openAmazonWebPage();
        webDriver.manage().window().maximize();
        String textTry= randomText();
        WebElement searchBar = webDriver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys(textTry);

        WebElement searchButton = webDriver.findElement(By.id("nav-search-submit-text"));
        searchButton.click();

        WebElement textErorr = webDriver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[3]/div[2]/div[1]/div/div/div/div[1]"));
        Assert.assertEquals(String.format("No results for %s.", textTry), textErorr.getText());

    }

    private String randomText() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 50;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        return generatedString;
    }

    @Test
    private void searchFieldInfo() {
        openAmazonWebPage();
        webDriver.manage().window().maximize();

        WebElement searchBar = webDriver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys("laptop");

        WebElement searchButton = webDriver.findElement(By.id("nav-search-submit-text"));
        searchButton.click();

        WebElement textSame = webDriver.findElement(By.xpath("//*[@id=\"search\"]/span/div/h1/div/div[1]/div/div/span[3]"));
        Assert.assertEquals("\"laptop\"", textSame.getText());

    }

    @Test
    private void searchResults() {
        openAmazonWebPage();
        webDriver.manage().window().maximize();

        WebElement searchBar = webDriver.findElement(By.id("twotabsearchtextbox"));
        searchBar.sendKeys("laptop");

        WebElement searchButton = webDriver.findElement(By.id("nav-search-submit-text"));
        searchButton.click();

        List<WebElement> searchlist = webDriver.findElements(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));
        Assert.assertTrue(search(searchlist));
    }

    private boolean search (List<WebElement> results) {
        return results.stream().map(WebElement::getText).anyMatch(x -> x.toLowerCase().contains("laptop"));
    }

    @AfterClass
    private void closeDriver() {
        webDriver.quit();
    }

    private void openAmazonWebPage() {
        webDriver.get("https://www.amazon.com/");
    }


}