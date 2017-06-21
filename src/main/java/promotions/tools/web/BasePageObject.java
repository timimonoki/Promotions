package promotions.tools.web;

import com.google.common.base.Function;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import promotions.pageArea.LidlArea;

import javax.annotation.Nullable;
import javax.xml.ws.WebFault;
import java.util.concurrent.TimeUnit;

public abstract class BasePageObject {

    WebDriver driver;
    WebDriverWait wait;

    LidlArea lidlArea;

    public BasePageObject(BrowserManager browserManager){
        this.driver = browserManager.getDriver();
        this.wait = browserManager.getWait();
    }

    public BasePageObject(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
    }


    public void waitForElementPresent(WebDriver driver, final WebElement elementToWait) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(50, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return isElementDisplayed(elementToWait);
            }
        });
    }

    public void waitForElementToBeDisplayed(WebElement element) {
        waitForElementPresent(this.driver, element);
    }

    public void waitForElementToBeClickable(WebElement webElement) {
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

     //checks whether the window with the specified name is opened
    public boolean windowExists(String windowName) {
        try {
            driver.switchTo().window(windowName);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

     //checks whether the web element exists
    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public ExpectedCondition<WebElement> visibilityOfElementLocated(
            final WebElement locator) {
        return new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                if (locator.isDisplayed()) {
                    return locator;
                }
                return null;
            }
        };
    }
}
