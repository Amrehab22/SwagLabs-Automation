package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DataDriven;

public class InventoryTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginToApplication() {

        DataDriven.TestData data = DataDriven.jsonReader();

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                data.validUser.username,
                data.validUser.password
        );

        inventoryPage = new InventoryPage(driver);
    }

    @Test
    public void verifyPageTitle() {

        Assert.assertEquals(
                inventoryPage.getPageTitle(),
                "Swag Labs",
                "Page title is incorrect"
        );
    }

    @Test
    public void verifyCartIconDisplayed() {

        Assert.assertTrue(
                inventoryPage.isCartDisplayed(),
                "Cart icon is not displayed"
        );
    }

    @Test
    public void verifySixProductsDisplayed() {

        Assert.assertEquals(
                inventoryPage.getProductsCount(),
                6,
                "Incorrect number of products displayed"
        );
    }
}