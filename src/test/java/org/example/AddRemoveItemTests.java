package org.example;

import org.example.pageobject.pages.HomePage;
import org.example.pageobject.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddRemoveItemTests extends BaseTest {
    @Test
    public void verifyAddFunctionality() {
        var productPage = new HomePage(webDriver).open()
                .clickComputersCategory()
                .clickSeeAllResults()
                .clickOnItem()
                .addToCart();

        var addedToCart = productPage.getAddToCartMessage();
        var cartNumber = productPage.getCartNumber();

        Assert.assertEquals("Added to Cart", addedToCart, "Item not added to cart");
        Assert.assertEquals("1", cartNumber);
    }

    @Test
    public void verifyRemoveFunctionality() {
        var cartMessage = new HomePage(webDriver).open()
                .clickComputersCategory()
                .clickSeeAllResults()
                .clickOnItem()
                .addToCart()
                .clickOnCart()
                .deleteItem()
                .waitForCartStatusText();

        var cartMessageString = cartMessage.getCartMessage();
        var priceString = cartMessage.getPrice();

        Assert.assertEquals("Your Amazon Cart is empty.", cartMessageString);
        Assert.assertEquals("$0.00", priceString);
    }
}
