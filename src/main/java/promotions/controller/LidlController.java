package promotions.controller;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import promotions.dto.ShopDTO;
import promotions.exceptions.CurrentCatalogException;
import promotions.model.Catalog;
import promotions.model.Image;
import promotions.model.Shop;
import promotions.model.ShopDetails;
import promotions.pageArea.LidlArea;
import promotions.service.LidlService;
import promotions.tools.web.BrowserManager;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping(value = "/lidl")
public class LidlController{

    private static final Logger logger = Logger.getLogger(LidlController.class);

    @Autowired
    LidlService lidlService;

    @RequestMapping(value = "/promotions", method = RequestMethod.GET)
    public void obtainCurrentCatalogImages() throws Exception {
        lidlService.obtainCurrentCatalogImages();
        try {
            lidlService.getAllImagesForCurrentCatalog(currentUrl);
        }catch (CurrentCatalogException e){
            browserManager.closeBrowser();
            browserManager.close();
        }
        browserManager.closeBrowser();
        browserManager.close();
    }

    @RequestMapping(value = "/allCatalogs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Catalog> findAllCatalogs(){
       return lidlService.findAllCatalogsWithImages();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Catalog findCurrentCatalogForACity(@RequestParam String city){
        return lidlService.findCurrentCatalogForACity(city);
    }

    @RequestMapping(value = "/allShops", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllShopNames(){
        return lidlService.getAllShopNames();
    }

    @RequestMapping(value = "/allShops", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllShopsInACountry(@RequestParam String country){
        return lidlService.getAllShopsInACountry(country);
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopDetails> getAllShopDetailsForAShop(@RequestParam String shopName){
        return lidlService.getAllShopDetailsForAShop(shopName);
    }
}
