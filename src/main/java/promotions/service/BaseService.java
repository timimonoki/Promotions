package promotions.service;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import promotions.pageArea.KauflandArea;
import promotions.pageArea.LidlArea;
import promotions.tools.web.BrowserManager;
import promotions.utils.conf.SiteConfigurations;

import java.net.MalformedURLException;

public abstract class BaseService {

    private static final Logger logger = Logger.getLogger(BaseService.class);

    protected BrowserManager browserManager;
    protected LidlArea lidlArea;
    protected KauflandArea kauflandArea;

    public void setUp(SiteConfigurations configurations) throws MalformedURLException {
        browserManager = new BrowserManager(configurations);
        lidlArea = new LidlArea(browserManager);
        kauflandArea = new KauflandArea(browserManager);
        initPages(browserManager.getDriver(), browserManager.getWait());
    }

    public void initPages(WebDriver driver, WebDriverWait wait) throws MalformedURLException {
        PageFactory.initElements(driver, lidlArea);
        PageFactory.initElements(driver, kauflandArea);
        logger.info("Initializing pages...");
    }

}
