package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccount {
    private final WebDriver driver;

    @FindBy(xpath = "/html/body//a[@id='address-link']")
    WebElement addressBtn;

    public MyAccount(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickAddressBtn() {
        addressBtn.click();
    }
}
