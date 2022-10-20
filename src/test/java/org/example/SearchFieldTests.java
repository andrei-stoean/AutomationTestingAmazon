package org.example;

import org.example.pageobject.pages.HomePage;
import org.example.pageobject.pages.ResultsPage;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class SearchFieldTests extends BaseTest {

    @Test
    public void searchFieldWritingTest() {
        String textToSearch = "kgvgvkyuoum,8lhlmu,ybjhuvbhjvlb.hylubyhv;bhjlv,khn;biuh";
        new HomePage(webDriver).open()
                .SearchGivenText(textToSearch);
        var incorectMessage = new ResultsPage(webDriver)
                .getIncorectMessage();
        assertEquals(incorectMessage, String.format("No results for %s.", textToSearch));
    }

    @Test
    public void searchFieldInfo() {
        String textToSearch = "laptop";
        new HomePage(webDriver).open()
                .SearchGivenText(textToSearch);
        var resultsMessage = new ResultsPage(webDriver)
                .searchSpecificWordResults();
        assertTrue(resultsMessage.contains(textToSearch));

    }

    @Test
    public void searchResultsForSpecificKey() {
        String textToSearch = "laptop";
        new HomePage(webDriver).open()
                .SearchGivenText(textToSearch);
        var specificResults = new ResultsPage(webDriver)
                .resultsList();
        assertTrue(specificResults.stream().anyMatch(x -> x.toLowerCase().contains(textToSearch)));
    }
}