package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InventoryPage {

    private WebDriver driver;

    private By cartIcon = By.className("shopping_cart_link");
    private By products = By.className("inventory_item");

    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutButton = By.id("logout_sidebar_link");
    private By linkedinIcon = By.linkText("LinkedIn");
    private By facebookIcon = By.linkText("Facebook");
    private By twitterIcon = By.linkText("Twitter");
    
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isCartDisplayed() {
        return driver.findElement(cartIcon).isDisplayed();
    }

    public int getProductsCount() {
        return driver.findElements(products).size();
    }

    public void openCart() {
        driver.findElement(cartIcon).click();
    }
    public void addProductToCart(String productName) {

        By product = By.xpath(
                "//div[contains(@class,'inventory_item')]" +
                        "[.//div[contains(@class,'inventory_item_name') and normalize-space()='" + productName + "']]"
        );

        driver.findElement(product)
                .findElement(By.tagName("button"))
                .click();
    }

    public double getProductPrice(String productName) {

        By product = By.xpath(
                "//div[contains(@class,'inventory_item')]" +
                        "[.//div[contains(@class,'inventory_item_name') and normalize-space()='" + productName + "']]"
        );

        String priceText = driver.findElement(product)
                .findElement(By.className("inventory_item_price"))
                .getText();

        return Double.parseDouble(priceText.replace("$", ""));
    }

    public String getProductButtonText(String productName) {

        By product = By.xpath(
                "//div[contains(@class,'inventory_item')]" +
                        "[.//div[contains(@class,'inventory_item_name') and normalize-space()='" + productName + "']]"
        );

        return driver.findElement(product)
                .findElement(By.tagName("button"))
                .getText();
    }
    public void logout() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open burger menu
        wait.until(
                ExpectedConditions.elementToBeClickable(menuButton)
        ).click();

        // Click Logout
        wait.until(
                ExpectedConditions.elementToBeClickable(logoutButton)
        ).click();

        // Make sure login page is loaded
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("user-name")
                )
        );
    }
    public void clickSocialLink(String socialNetwork) {
        switch (socialNetwork.toLowerCase()) {
            case "linkedin":
                clickLinkedIn();
                break;
            case "facebook":
                clickFacebook();
                break;
            case "twitter":
                clickTwitter();
                break;
        }
    }

    private void clickLinkedIn() {
        driver.findElement(linkedinIcon).click();
    }

    private void clickFacebook() {
        driver.findElement(facebookIcon).click();
    }

    private void clickTwitter() {
        driver.findElement(twitterIcon).click();
    }
}