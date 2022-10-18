package org.example;

import org.example.pageobject.pages.HomePage;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.Random;

import static org.testng.Assert.*;

public class FilteringSearchingTests extends BaseTest{
    @Test
    public void searchByCategoryBrand() {
        var numberOfResultsContainingBrandName = new HomePage(webDriver).open()
                .clickRandomCategory()
                .clickRandomBrand()
                .getNumberOfProductsContainingBrandName();
        assertTrue(numberOfResultsContainingBrandName > 0);
    }

    @Test
    public void filterByPriceRange() {
        int minPrice = new Random().nextInt(10);
        int maxPrice = new Random().nextInt(minPrice, 200);

        var priceResults = new HomePage(webDriver).open()
                .clickRandomCategory()
                .clickRandomBrand()
                .filterByPrice(minPrice, maxPrice)
                .getPriceResults();
        assertFalse(priceResults.isEmpty(), "No results within price range");

        var numberOfPricesWithinRange = priceResults.stream()
                .filter(price -> price >= minPrice && price <= maxPrice)
                .count();
        assertTrue(numberOfPricesWithinRange >= priceResults.size() / 2);
    }

    @Test
    public void sortByPrice() {
        var resultsPage = new HomePage(webDriver).open()
                .clickRandomCategory()
                .clickRandomBrand();

        var priceResults = resultsPage.clickSortByDropDown()
                .clickLowToHigh()
                .getPriceResults();
        assertFalse(priceResults.isEmpty(), "No results matching");
        assertEquals(priceResults, priceResults.stream().sorted().toList());

        priceResults = resultsPage.clickSortByDropDown()
                .clickHighToLow()
                .getPriceResults();
        assertEquals(priceResults, priceResults.stream().sorted(Comparator.reverseOrder()).toList());
    }
}
