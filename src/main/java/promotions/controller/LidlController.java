package promotions.controller;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import promotions.exceptions.CurrentCatalogException;
import promotions.model.Image;
import promotions.pageArea.LidlArea;
import promotions.service.LidlService;
import promotions.tools.web.BrowserManager;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping(value = "/promotions")
public class LidlController{

    private static final Logger logger = Logger.getLogger(LidlController.class);

    BrowserManager browserManager;

    LidlArea lidlArea;

    @Autowired
    LidlService lidlService;

    @Autowired
    Environment env;

    public void initPages(WebDriver driver, WebDriverWait wait) throws MalformedURLException {
        lidlArea = new LidlArea(browserManager);

        PageFactory.initElements(driver, lidlArea);
        logger.info("Initializing lidlArea...");
    }

    @RequestMapping(value = "/lidl", method = RequestMethod.GET)
    public void getCurrentCatalogImages() throws Exception {
        browserManager = new BrowserManager(env);

        initPages(browserManager.getDriver(), browserManager.getWait());
        browserManager.openUrl(env.getProperty("catalog.url.lidl"));

        Thread.sleep(2000);
        lidlArea.waitForElementToBeClickable(lidlArea.getCatalogs().get(0));
        lidlArea.getCatalogs().get(0).click();
        String currentUrl = browserManager.getDriver().getCurrentUrl();
        try {
            lidlService.getAllImagesForCurrentCatalog(currentUrl);
        }catch (CurrentCatalogException e){
            browserManager.closeBrowser();
            browserManager.close();
        }
        browserManager.closeBrowser();
        browserManager.close();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Image> findAllImagesForCurrentCatalog(){
       return lidlService.findAllImagesForCurrentCatalog();
    }
}