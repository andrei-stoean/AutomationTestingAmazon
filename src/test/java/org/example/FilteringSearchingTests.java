package org.example;

import org.example.pageobject.pages.HomePage;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.Random;

import static org.testng.Assert.*;

public class FilteringSearchingTests extends BaseTest{
    @Test
    public void searchByCategoryBrand() {
        var numberOfResultsContainingBrandName = new HomePage(webDriver)
                .open()
                .selectARandomCategory()
                .selectARandomBrand()
                .findNumberOfProductsContainingBrandName();
        assertTrue(numberOfResultsContainingBrandName > 0);
    }

    @Test
    public void filterByPriceRange() {
        var minPrice = new Random().nextInt(10);
        var maxPrice = new Random().nextInt(minPrice, 200);

        var priceResults = new HomePage(webDriver).open()
                .selectARandomCategory()
                .selectARandomBrand()
                .filterResultsByPrice(minPrice, maxPrice)
                .findPriceResults();
        assertFalse(priceResults.isEmpty(), "No results within price range");

        var numberOfPricesWithinRange = priceResults.stream()
                .filter(price -> price >= minPrice && price <= maxPrice)
                .count();
        assertTrue(numberOfPricesWithinRange >= priceResults.size() / 2);
    }

    @Test
    public void sortByPrice() {
        var resultsPage = new HomePage(webDriver)
                .open()
                .selectARandomCategory()
                .selectARandomBrand();

        var priceResults = resultsPage
                .sortResultsByPriceIncreasing()
                .findPriceResults();
        assertFalse(priceResults.isEmpty(), "No results matching");
        assertEquals(priceResults, priceResults.stream().sorted().toList());

        priceResults = resultsPage
                .sortResultsByPriceDecreasing()
                .findPriceResults();
        assertEquals(priceResults, priceResults.stream().sorted(Comparator.reverseOrder()).toList());
    }
}
