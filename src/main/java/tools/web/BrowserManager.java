package tools.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BrowserManager {

    private WebDriver driver;
    private WebDriverWait wait;
    private DesiredCapabilities capabilities;

    public BrowserManager(){
        String path = "D:/Licenta/Promotii/src/main/resources/drivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);
        driver = new ChromeDriver();
        wait = new WebDriverWait(this.driver, 90);
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
    }

    public DesiredCapabilities getCapabilities() {
        return capabilities;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void openUrl(String urlToBeOpened) {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(urlToBeOpened);
    }

    //close all opened winows
    public void closeAllOpenedWindows(boolean keepFirst) {
        Set<String> openedWindows = driver.getWindowHandles();
        for (String w : openedWindows) {
            if (!keepFirst) {
                driver.switchTo().window(w).close();
            } else {
                keepFirst = false;
            }
        }
    }

    //close the browser
    public void closeBrowser() {
        if (driver != null) {
            driver.close();
        }
    }

    //close the server
    public void close() {
        driver.quit();
    }
}
