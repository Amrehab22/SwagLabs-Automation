package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DataDriven;

import java.util.Arrays;
import java.util.List;

public class CartTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private DataDriven.TestData data;

    @BeforeMethod
    public void login() {

        data = DataDriven.jsonReader();
        checkoutPage = new CheckoutPage(driver);
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);

        loginPage.login(
                data.validUser.username,
                data.validUser.password
        );
    }

    // Scenario 2
    @Test
    public void verifyCartIsEmpty() {

        inventoryPage.openCart();

        Assert.assertTrue(
                cartPage.isCartEmpty(),
                "Cart should be empty"
        );
    }

    // Scenario 3
    @Test
    public void verifyThreeProductsAddedToCart() {

        for (String product : data.cartProducts) {
            inventoryPage.addProductToCart(product);
        }

        inventoryPage.openCart();

        List<String> actualProducts =
                cartPage.getCartProductNames();

        List<String> expectedProducts =
                Arrays.asList(data.cartProducts);

        Assert.assertEquals(
                actualProducts,
                expectedProducts,
                "Products are not in the expected order"
        );
    }

    // Scenario 4
    @Test
    public void verifyRemoveProductFromCart() {

        for (String product : data.cartProducts) {
            inventoryPage.addProductToCart(product);
        }

        inventoryPage.openCart();

        cartPage.removeProduct(data.cartProducts[1]);

        driver.navigate().back();

        Assert.assertEquals(
                inventoryPage.getProductButtonText(data.cartProducts[1]),
                "Add to cart",
                "Bolt T-Shirt should show 'Add to cart'"
        );

        Assert.assertEquals(
                inventoryPage.getProductButtonText(data.cartProducts[0]),
                "Remove",
                "Backpack should still show 'Remove'"
        );

        Assert.assertEquals(
                inventoryPage.getProductButtonText(data.cartProducts[2]),
                "Remove",
                "Onesie should still show 'Remove'"
        );
    }

    // Scenario 1
    @Test
    public void verifySocialLinks() {

        String originalWindow = driver.getWindowHandle();

        // LinkedIn
        inventoryPage.clickSocialLink("LinkedIn");

        for (String window : driver.getWindowHandles()) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }

        Assert.assertTrue(
                driver.getCurrentUrl().contains("linkedin"),
                "LinkedIn URL is incorrect"
        );

        driver.close();
        driver.switchTo().window(originalWindow);
    }
    // Scenario 5
    @Test
    public void verifyCartTotalPrice() {

        // Calculate expected subtotal while we are still on Inventory Page
        double expectedSubtotal = 0;

        for (String product : data.cartProducts) {
            expectedSubtotal += inventoryPage.getProductPrice(product);
        }

        // Add products to cart
        for (String product : data.cartProducts) {
            inventoryPage.addProductToCart(product);
        }

        // Open Cart
        inventoryPage.openCart();

        // Go to Checkout
        cartPage.clickCheckout();

        // Checkout Step One
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        checkoutPage.enterCheckoutInformation(
                "Amr",
                "Ehab",
                "12345"
        );

        // Move to Checkout Step Two
        checkoutPage.clickContinue();

        // Get actual subtotal
        double actualSubtotal = checkoutPage.getSubtotal();

        // Verify
        Assert.assertEquals(
                actualSubtotal,
                expectedSubtotal,
                0.01,
                "Cart subtotal is incorrect"
        );
    }
    // Scenario 6
    @Test
    public void verifyCheckoutWithEmptyCart() {

        // Cart should be empty
        inventoryPage.openCart();

        Assert.assertTrue(
                cartPage.isCartEmpty(),
                "Cart should be empty"
        );

        // Try to checkout with empty cart
        cartPage.clickCheckout();

        // Verify actual behavior
        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-one.html"),
                "User should be allowed to proceed to checkout with an empty cart"
        );
    }


    // Scenario 7
    @Test
    public void verifyCartStateAfterLogoutLogin() {

        // Add 2 products
        inventoryPage.addProductToCart(data.cartProducts[0]);
        inventoryPage.addProductToCart(data.cartProducts[1]);

        // Logout
        inventoryPage.logout();

        // Login again
        loginPage.login(
                data.validUser.username,
                data.validUser.password
        );

        // Open cart
        inventoryPage.openCart();

        // Verify cart state
        List<String> actualProducts = cartPage.getCartProductNames();

        Assert.assertEquals(
                actualProducts.size(),
                2,
                "Cart should still contain 2 products after logout/login"
        );
    }
}