package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {
    protected final WebDriver webDriver;

    public BaseTest() {
        WebDriverManager.chromiumdriver().setup();
        webDriver = new ChromeDriver();
    }

    @BeforeClass
    public void setUpDriver() {
        webDriver.manage().window().maximize();
    }



    @AfterClass
    public void closeDriver() {
        webDriver.quit();
    }
}