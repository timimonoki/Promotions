package promotions.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.lightbody.bmp.core.har.Har;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import promotions.pageArea.KauflandArea;
import promotions.pageArea.LidlArea;
import promotions.service.KauflandService;
import promotions.tools.web.BrowserManager;
import promotions.utils.conf.SiteConfigurations;

import java.net.MalformedURLException;

@RestController
@RequestMapping(value = "/kaufland")
@ImportResource(locations = {"classpath:beans.xml"})
public class KauflandController extends BaseController{

    private static final Logger logger = Logger.getLogger(KauflandController.class);

    private BrowserManager browserManager;

    KauflandArea kauflandArea;

    @Autowired
    SiteConfigurations configurations;

    @Autowired
    KauflandService kauflandService;

    public void initPages(WebDriver driver, WebDriverWait wait) throws MalformedURLException {
        kauflandArea = new KauflandArea(browserManager);

        PageFactory.initElements(driver, kauflandArea);
        logger.info("Initializing kauflandArea...");
    }

    @RequestMapping(value = "/promotions", method = RequestMethod.GET)
    public void getCurrentCatalogImages() throws Exception {
        kauflandService.obtainCurrentCatalogImages();
    }
}
