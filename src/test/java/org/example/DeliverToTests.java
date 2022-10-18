package org.example;

import org.example.pageobject.pages.HomePage;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DeliverToTests extends BaseTest {
    @Test
    public void changeDeliverToLocationByZipCode() {
        var updatedLocation = new HomePage(webDriver).open()
                .clickDeliverToIcon()
                .enterZipCodeApplyAndContinue()
                .waitForPageLoad()
                .getDeliverToLocation();
        assertTrue(updatedLocation.contains("Los Angeles 90001"));
    }

    @Test
    public void isPolandAvailable() {
        var country = new HomePage(webDriver).open()
                .clickDeliverToIcon()
                .clickCountriesDropDown()
                .clickPoland()
                .clickDone()
                .waitForPageLoad()
                .getDeliverToLocation();
        assertEquals(country, "Poland");
    }

    @Test
    public void changeDeliverToCountry() {
        var shipToCountry = new HomePage(webDriver).open()
                .clickDeliverToIcon()
                .clickCountriesDropDown()
                .clickPoland()
                .clickDone()
                .waitForPageLoad()
                .clickRandomCategory()
                .clickRandomResult()
                .getShipToCountry();
        assertTrue(shipToCountry.contains("Poland"));
    }
}
