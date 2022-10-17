package org.example;

import org.example.pageobject.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeliverToTests extends BaseTest {
    @Test
    private void changeDeliverToLocationByZipCode() {
        String updatedLocation = new HomePage(webDriver).open()
                .clickDeliverToIcon()
                .enterZipCodeApplyAndContinue()
                .getDeliverToLocation();
        Assert.assertEquals(updatedLocation, "Los Angeles 90001\u200C");
    }

    @Test
    private void isPolandAvailable() {
        String country = new HomePage(webDriver).open()
                .clickDeliverToIcon()
                .clickCountriesDropDown()
                .clickPoland()
                .clickDone()
                .getDeliverToLocation();
        Assert.assertEquals(country, "Poland");
    }

    @Test
    private void changeDeliverToCountry() {
        String shipToCountry = new HomePage(webDriver).open()
                .clickDeliverToIcon()
                .clickCountriesDropDown()
                .clickPoland()
                .clickDone()
                .waitForPageLoad()
                .clickRandomCategory()
                .clickRandomResult()
                .getShipToCountry();
        Assert.assertTrue(shipToCountry.contains("Poland"));
    }
}
