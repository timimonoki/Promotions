package promotions.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.lightbody.bmp.core.har.Har;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import promotions.pageArea.KauflandArea;
import promotions.pageArea.LidlArea;
import promotions.tools.web.BrowserManager;

import java.net.MalformedURLException;

@RestController
@RequestMapping(value = "/kaufland")
public class KauflandController {

    private static final Logger logger = Logger.getLogger(KauflandController.class);

    private BrowserManager browserManager;

    KauflandArea kauflandArea;

    @Autowired
    Environment env;

    public void initPages(WebDriver driver, WebDriverWait wait) throws MalformedURLException {
        kauflandArea = new KauflandArea(browserManager);

        PageFactory.initElements(driver, kauflandArea);
        logger.info("Initializing kauflandArea...");
    }

    @RequestMapping(value = "/promotions", method = RequestMethod.GET)
    public void getCurrentCatalogImages() throws MalformedURLException, JsonProcessingException {
        browserManager = new BrowserManager(env);
        initPages(browserManager.getDriver(), browserManager.getWait());

        browserManager.getProxy().newHar("kaufland.ro");
        browserManager.openUrl("https://www.kaufland.ro/oferte/cataloage-cu-reduceri.html");
        Har har = browserManager.getProxy().getHar();
        ObjectMapper mapper = new ObjectMapper();
        String harString = mapper.writeValueAsString(har);
        System.out.println("Har file content is: \n" +harString);
    }
}
