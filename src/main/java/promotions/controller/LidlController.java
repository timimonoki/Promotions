package promotions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.lightbody.bmp.core.har.Har;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promotions.pageArea.LidlArea;
import promotions.service.LidlService;
import promotions.tools.web.FirefoxManager;

import java.io.IOException;

@RestController
@RequestMapping(value = "/promotions")
public class LidlController{

    private static Logger logger = LogManager.getLogger();

    FirefoxManager firefoxManager;

    LidlArea lidlArea;

    @Autowired
    LidlService lidlService;

    public void initPages(WebDriver driver, WebDriverWait wait){
        lidlArea = new LidlArea();

        PageFactory.initElements(driver, lidlArea);
        logger.info("Initializing lidlArea...");
    }

    @RequestMapping(value = "/lidl")
    public void getHarFileForLIdl() throws InterruptedException, IOException {
        firefoxManager = new FirefoxManager();

        initPages(firefoxManager.getDriver(), firefoxManager.getWait());

        firefoxManager.getProxy().newHar("lidl.ro");
        firefoxManager.openUrl("http://www.lidl.ro/ro/2829.htm");
        Har har = firefoxManager.getProxy().getHar();
        ObjectMapper mapper = new ObjectMapper();
        String harString = mapper.writeValueAsString(har);

        System.out.println(harString);

        //I should need a wait for element present here, not sure yet how to extend BasePageObject
        lidlArea.getCatalogues().get(0).click();

        //waitForPageLoad --here
        // ---then do
        String currentUrl = firefoxManager.getDriver().getCurrentUrl();
        lidlService.getCatalogueImagesForCurrentPromotion(currentUrl);

        firefoxManager.closeBrowser();
        firefoxManager.close();
    }

    @RequestMapping(value = "/try")
    public void incearca() throws IOException {
        lidlService.getCatalogueImagesForCurrentPromotion("http://catalog.lidl.ro/acd07657-a63e-4c86-ae3c-fcf1bfb19620/html5.html#/1");
    }
}
