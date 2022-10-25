package org.example;

import org.example.pageobject.pages.HomePage;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DeliverToTests extends BaseTest {
    @Test
    public void changeDeliverToLocationByZipCode() {
        var losAngelesZipCode = "90001";
        var updatedLocation = new HomePage(webDriver)
                .open()
                .changeDeliveryLocationByProvidingZipCode(losAngelesZipCode)
                .waitForLocationUpdate()
                .findDeliverToLocation();
        assertTrue(updatedLocation.contains("Los Angeles 90001"));
    }

    @Test
    public void testIfShippingToPolandIsAvailable() {
        var deliverToCountries = new HomePage(webDriver)
                .open()
                .findCountriesAvailableForDelivery();
        assertTrue(deliverToCountries.contains("Poland"));
    }

    @Test
    public void changeDeliverToCountry() {
        var deliverToCountry = new HomePage(webDriver)
                .open()
                .changeDeliveryCountryToPoland()
                .waitForLocationUpdate()
                .selectARandomCategory()
                .selectARandomProduct()
                .findDeliverToCountry();
        assertTrue(deliverToCountry.contains("Poland"));
    }
}
