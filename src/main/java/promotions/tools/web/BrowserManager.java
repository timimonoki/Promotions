package promotions.tools.web;

import org.browsermob.proxy.ProxyServer;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import promotions.utils.conf.SiteConfigurations;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class BrowserManager {

    private static final Logger logger = Logger.getLogger(BrowserManager.class);

    private String browserType;

    private WebDriver driver;
    private WebDriverWait wait;
    private DesiredCapabilities capabilities;
    private BrowserMobProxy proxy;


    public BrowserManager(SiteConfigurations conf) throws Exception {
        this.browserType = conf.getBrowserType();
        switch (this.browserType){
            case "chrome" : {
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
            }
            case "firefox" : {
//                ProxyServer server = new ProxyServer(4446);
//                server.start();


                proxy = new BrowserMobProxyServer();
                proxy.start(8081);
                Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
                FirefoxProfile profile = new FirefoxProfile();
                profile.setAcceptUntrustedCertificates(true);
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
                capabilities.setCapability(FirefoxDriver.PROFILE, profile);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                String path = "C:/Users/MonokiT/Desktop/Betfair Internship/Promotii/Promotions/src/main/resources/drivers/geckodriver.exe";
                System.setProperty("webdriver.gecko.driver", path);
                driver = new FirefoxDriver(capabilities);
                logger.info("Initiating FirefoxManager instance...");
            }
        }
        wait = new WebDriverWait(this.driver, 90);

    }

    public BrowserMobProxy getProxy() {
        return proxy;
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
