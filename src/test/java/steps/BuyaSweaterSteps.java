package steps;

import drivers.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.*;
import pages.components.AddToCartPopupComponent;
import pages.components.HeaderComponent;

public class BuyaSweaterSteps {
    private final WebDriver driver = DriverManager.getDriver();
    HeaderComponent headerComponent;

    @Given("I am signed in")
    public void signingIn() {
        driver.get("https://mystore-testlab.coderslab.pl/index.php");
        headerComponent = new HeaderComponent(driver);
        headerComponent.signInClick();
        Login loginPage = new Login(driver);
        loginPage.loginAs("wuoednlgyguzqovhdt@nbmbb.com", "edyta1");
        Assert.assertEquals("Edyta Gajewska", headerComponent.getLoggedUserName());
    }

    @When("I add {string} of {string} selected sweater to the cart")
    public void selectAndAddToCart(String quantity, String size) {
        headerComponent.clickClothesLink();
        Clothes clothes = new Clothes(driver);
        clothes.goToSweaterSite();
        Sweaters sweaterPage = new Sweaters(driver);
        sweaterPage.selectSize(size);
        sweaterPage.selectQuantityUsingButtons(quantity);
        Assert.assertEquals(size, sweaterPage.getDisplayedSize());
//        Assert.assertEquals(quantity, sweaterPage.getDisplayedQuantity()); To  sobie darujemy bo Presta miesza Asertujemy koszyk
        sweaterPage.addToCart();
        AddToCartPopupComponent addToCartPopupComponent = new AddToCartPopupComponent(driver);
        Assert.assertTrue("Adding to cart invalid message", addToCartPopupComponent.getAddToCartMessage().contains("Product successfully added to your shopping cart"));
        Assert.assertTrue("Size in cart is incorrect.", addToCartPopupComponent.getSizeMessage().contains(size));
        System.out.printf("Oczekiwana ilość: '%s', ilość z popupu: '%s'%n", quantity, addToCartPopupComponent.getQuantityMessage());
        Assert.assertTrue("Quantity in cart is incorrect", addToCartPopupComponent.getQuantityMessage().contains(quantity));
        Assert.assertEquals(addToCartPopupComponent.getSingleCartPrice(), (sweaterPage.singleRegularPrice() * 0.8), 0.01);
        addToCartPopupComponent.clickProceedToCheckoutBtn();
    }

    @And("I confirm an address, select pickup and payment method")
    public void confirmingPurchaseDetails() {
        Cart cartPage = new Cart(driver);
        cartPage.clickProceedToCheckoutBtn();
        PersonalInfo personalInformationPage = new PersonalInfo(driver);
        personalInformationPage.clickAddressConfirmBtn();
//        System.out.println("Before checkbox");
//        personalInformationPage.clickCheckbox(); Ten checkbox jest wejściowo klknięty nie będę walczyć z Prestą
//        System.out.println("Checkbox clicked");
        personalInformationPage.clickConfirmDeliveryBtn();
        personalInformationPage.clickPayCheckCheckbox();
        personalInformationPage.clickThermsAgreeCheckbox();
    }

    @Then("I confirm the order and make a screenshot of confirmation")
    public void orderConfirmation() {
        PersonalInfo personalInformationPage = new PersonalInfo(driver);
        personalInformationPage.clickPlaceOrderBtn();
        OrderConfirmation orderConfirmationPage = new OrderConfirmation(driver);
        System.out.println(orderConfirmationPage.getOrderReference());
        orderConfirmationPage.takeScreenshotOfOrderConfirmation();
    }
}
