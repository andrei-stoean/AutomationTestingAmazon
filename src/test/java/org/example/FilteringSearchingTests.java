package org.example;

import org.example.pageobject.pages.ResultsPage;
import org.example.pageobject.pages.HomePage;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.testng.Assert.*;

public class FilteringSearchingTests extends BaseTest{
    @Test
    public void searchByCategoryBrand() {
        long numberOfResultsContainingBrandName = new HomePage(webDriver).open()
                .clickRandomCategory()
                .clickRandomBrand()
                .getNumberOfProductsContainingBrandName();
        assertTrue(numberOfResultsContainingBrandName > 0);
    }

    @Test
    public void filterByPriceRange() {
        int minValue = new Random().nextInt(10);
        int maxValue = new Random().nextInt(minValue, 200);

        List<Double> priceResults = new HomePage(webDriver).open()
                .clickRandomCategory()
                .clickRandomBrand()
                .filterByPrice(minValue, maxValue)
                .getPriceResults();
        assertFalse(priceResults.isEmpty(), "No results within price range");

        long numberOfPricesWithinRange = priceResults.stream()
                .filter(price -> price >= minValue && price <= maxValue)
                .count();
        assertTrue(numberOfPricesWithinRange >= priceResults.size() / 2);
    }

    @Test
    public void sortByPrice() {
        ResultsPage resultsPage = new HomePage(webDriver).open()
                .clickRandomCategory()
                .clickRandomBrand()
                .clickSortByDropDown()
                .clickLowToHigh();

        List<Double> priceResults = resultsPage.getPriceResults();
        assertFalse(priceResults.isEmpty(), "No results matching");
        assertEquals(priceResults.stream().sorted().toList(), priceResults);

        priceResults = resultsPage
                .clickSortByDropDown()
                .clickHighToLow()
                .getPriceResults();
        assertEquals(priceResults.stream().sorted(Comparator.reverseOrder()).toList(), priceResults);
    }
}
