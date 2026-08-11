package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.DataDriven;

public class LoginTest extends BaseTest {

    @Test
    public void verifySuccessfulLogin() {

        DataDriven.TestData data = DataDriven.jsonReader();

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                data.validUser.username,
                data.validUser.password
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/inventory.html"),
                "User was not redirected to Inventory page"
        );
    }

    @Test
    public void verifyInvalidLogin() {

        DataDriven.TestData data = DataDriven.jsonReader();

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                data.invalidUser.username,
                data.invalidUser.password
        );

        String actualError = loginPage.getErrorMessage();

        Assert.assertTrue(
                actualError.contains("Username and password do not match"),
                "Invalid login error message is incorrect"
        );
    }

    @Test
    public void verifyLoginWithoutPassword() {

        DataDriven.TestData data = DataDriven.jsonReader();

        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(data.validUser.username);

        loginPage.clickLogin();

        String actualError = loginPage.getErrorMessage();

        Assert.assertTrue(
                actualError.contains("Password is required"),
                "Password required error message is incorrect"
        );
    }
}