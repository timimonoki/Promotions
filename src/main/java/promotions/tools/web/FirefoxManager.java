package promotions.tools.web;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class FirefoxManager {

    private WebDriver driver;
    private WebDriverWait wait;
    private DesiredCapabilities capabilities;
    private BrowserMobProxy proxy;

    public FirefoxManager(){
        proxy = new BrowserMobProxyServer();
        proxy.start(15);

        // get the Selenium proxy object
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        // configure it as a desired capability
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

        // start the browser up
        //System.setProperty("webdriver.gecko.driver", "D:/Licenta/Promotii/src/main/resources/drivers/geckodriver.exe");
        driver = new FirefoxDriver(capabilities);
    }

    public BrowserMobProxy getProxy() {
        return proxy;
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

    public DesiredCapabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(DesiredCapabilities capabilities) {
        this.capabilities = capabilities;
    }


    public void openUrl(String urlToBeOpened) {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(urlToBeOpened);
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
