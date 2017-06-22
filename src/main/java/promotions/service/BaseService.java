package promotions.service;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import promotions.model.Catalog;
import promotions.model.Image;
import promotions.model.Shop;
import promotions.pageArea.KauflandArea;
import promotions.pageArea.LidlArea;
import promotions.repository.CatalogRepository;
import promotions.repository.ImageRepository;
import promotions.tools.web.BrowserManager;
import promotions.utils.conf.SiteConfigurations;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import static promotions.utils.Utils.getDateFromString;

@ImportResource(locations = {"classpath:beans.xml"})
public abstract class BaseService {

    @Autowired
    @Qualifier(value = "siteConfiguration")
    SiteConfigurations configurations;

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    ImageRepository imageRepository;

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

    public void saveCatalog(Shop shop, String catalogName, List<String> availability, List<String> imageUrls) throws ParseException {
        Catalog catalog = new Catalog();
        catalog.setName(catalogName);
        catalog.setStart_date(getDateFromString(availability.get(0)));
        catalog.setEnd_date(getDateFromString(availability.get(1)));
        catalog.setShop(shop);
        catalogRepository.save(catalog);
        imageUrls.forEach(imageUrl ->{
            Image image = new Image(imageUrl);
            image.setCatalog(catalog);
            imageRepository.save(image);
        });
    }

}
