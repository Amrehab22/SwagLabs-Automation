package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartPage {

    private WebDriver driver;

    private By cartItems = By.className("cart_item");
    private By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getCartItemsCount() {
        return driver.findElements(cartItems).size();
    }

    public boolean isCartEmpty() {
        return getCartItemsCount() == 0;
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }

    public List<String> getCartProductNames() {

        List<String> productNames = new ArrayList<>();

        List<WebElement> items = driver.findElements(cartItems);

        for (WebElement item : items) {

            String productName = item
                    .findElement(By.className("inventory_item_name"))
                    .getText();

            productNames.add(productName);
        }

        return productNames;
    }

    public void removeProduct(String productName) {

        By removeButton = By.xpath(
                "//div[@class='cart_item' and " +
                        ".//div[@class='inventory_item_name' and text()='" +
                        productName +
                        "']]//button"
        );

        driver.findElement(removeButton).click();
    }

    public double getProductPrice(String productName) {

        By priceLocator = By.xpath(
                "//div[@class='cart_item' and " +
                        ".//div[@class='inventory_item_name' and text()='" +
                        productName +
                        "']]//div[@class='inventory_item_price']"
        );

        String priceText = driver.findElement(priceLocator).getText();

        return Double.parseDouble(
                priceText.replace("$", "")
        );
    }

    public double getItemTotal() {

        By itemTotalLocator = By.xpath(
                "//*[contains(text(),'Item total:')]"
        );

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        String totalText = wait
                .until(ExpectedConditions.visibilityOfElementLocated(itemTotalLocator))
                .getText();

        return Double.parseDouble(
                totalText.replace("Item total: $", "").trim()
        );
    }
}