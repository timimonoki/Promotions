package promotions.tools.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class BrowserManager {

    private static Logger logger = LogManager.getLogger();

    private WebDriver driver;
    private WebDriverWait wait;
    private DesiredCapabilities capabilities;

    public BrowserManager() throws MalformedURLException {
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.CLIENT, Level.WARNING);
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        Map<String, Object> perfLogPrefs = new HashMap<String, Object>();
        perfLogPrefs.put("traceCategories", "browser,devtools.timeline,devtools");
        perfLogPrefs.put("enableNetwork", true);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("perfLoggingPrefs", perfLogPrefs);
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        String path = "D:/Licenta/Promotii/src/main/resources/drivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);
        driver = new ChromeDriver(cap);
        logger.info("Initiating ChromeDriver instance...");

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
