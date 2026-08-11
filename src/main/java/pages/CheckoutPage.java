package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

    private WebDriver driver;

    // Checkout Step One
    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By continueButton = By.id("continue");

    // Checkout Step Two
    private By subtotal = By.className("summary_subtotal_label");
    private By tax = By.className("summary_tax_label");
    private By total = By.className("summary_total_label");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterCheckoutInformation(
            String firstName,
            String lastName,
            String postalCode
    ) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    public double getSubtotal() {

        String text = driver.findElement(subtotal).getText();

        return Double.parseDouble(
                text.replace("Item total: $", "").trim()
        );
    }

    public double getTax() {

        String text = driver.findElement(tax).getText();

        return Double.parseDouble(
                text.replace("Tax: $", "").trim()
        );
    }

    public double getTotal() {

        String text = driver.findElement(total).getText();

        return Double.parseDouble(
                text.replace("Total: $", "").trim()
        );
    }
}