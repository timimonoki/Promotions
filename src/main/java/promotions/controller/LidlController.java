package promotions.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import promotions.model.Catalog;
import promotions.model.Shop;
import promotions.model.ShopDetails;
import promotions.service.LidlService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/lidl")
public class LidlController{

    private static final Logger logger = Logger.getLogger(LidlController.class);

    @Autowired
    LidlService lidlService;

    @RequestMapping(value = "/promotions", method = RequestMethod.GET)
    public void obtainCurrentCatalogImages() throws Exception {
        lidlService.obtainCurrentCatalogImages();
    }

    @RequestMapping(value = "/allCatalogs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Catalog> findAllCatalogs(){
       return lidlService.findAllCatalogsWithImages();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Catalog findCurrentCatalogsForACity(@RequestParam String city){
        return lidlService.findCurrentCatalogsForACity(city);
    }

    @RequestMapping(value = "/allShops", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Shop> getAllShops(){
        return lidlService.getAllShops();
    }

    @RequestMapping(value = "/allShopsIn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Shop> getAllShopsInACountry(@RequestParam String country) throws Exception {
        return lidlService.getAllShopsInACountry(country);
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopDetails> getAllShopDetailsForAShop(@RequestParam String shopName) throws Exception {
        return lidlService.getAllShopDetailsForAShop(shopName);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Shop> findShops(@RequestParam(required = false, defaultValue = "") String countryName,
                                @RequestParam(required = false, defaultValue = "") String cityName) throws Exception {

        return lidlService.findShops(countryName, cityName);
    }
}
