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
    public void isShippingToPolandAvailable() {
        var shippingCountries = new HomePage(webDriver).open()
                .clickDeliverToIcon()
                .clickCountriesDropDown()
                .getShipToCountries();
        assertTrue(shippingCountries.contains("Poland"));
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
