package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Clothes {
    private final WebDriver driver;

    @FindBy(css = "[data-id-product='22']")
    WebElement sweaterLink;

    public Clothes(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goToSweaterSite() {
        sweaterLink.click();
    }
}
