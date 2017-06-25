package promotions.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import promotions.exceptions.EntityNotFoundException;
import promotions.exceptions.ValidatorException;
import promotions.model.Catalog;
import promotions.model.Image;
import promotions.model.Shop;
import promotions.model.ShopDetails;
import promotions.service.BaseService;
import java.util.Date;
import java.util.List;

import static promotions.utils.Constants.*;

public class BaseController {

    private static final Logger logger = Logger.getLogger(BaseController.class);

    @Autowired
    BaseService baseService;

    @RequestMapping(value = "/findCatalogs", method = RequestMethod.GET)
    public List<Catalog> findCatalogs(@RequestParam(required = false, defaultValue = "") String country,
                                      @RequestParam(required = false, defaultValue = "") String city,
                                      @RequestParam(required = false, defaultValue = "") String shop,
                                      @RequestParam(required = false, defaultValue = "") Integer id,
                                      @RequestParam(required = false, defaultValue = "") @DateTimeFormat(pattern="dd-MM-yyyy") Date startDate,
                                      @RequestParam(required = false, defaultValue = "") @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate) throws ValidatorException, EntityNotFoundException {
        return baseService.findCatalogs(country, city, shop, id, startDate, endDate);
    }

    @RequestMapping(value = "/currentCatalogs", method = RequestMethod.GET)
    public List<Catalog> findCurrentCatalogsForACity(@RequestParam(required = false, defaultValue = "") String city) throws ValidatorException, EntityNotFoundException {
        return baseService.findCurrentCatalogsForACity(city);
    }

    @RequestMapping(value = "/catalogImages", method = RequestMethod.GET)
    public List<Image> findImagesForACatalog(@RequestParam Integer id) throws EntityNotFoundException {
        return baseService.findImagesForACatalog(id);
    }

    @RequestMapping(value = "/findShops", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Shop> findShops(@RequestParam(required = false, defaultValue = "") String country,
                                @RequestParam(required = false, defaultValue = "") String city) throws Exception {

        return baseService.findShops(country, city);
    }

    @RequestMapping(value = "/allDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopDetails> getAllShopDetailsForAShop(@RequestParam String shop) throws Exception {
        return baseService.getAllShopDetailsForAShop(shop);
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopDetails> getShopDetailsForAShop(@RequestParam String shop,
                                                    @RequestParam(required = false, defaultValue = "") String country,
                                                    @RequestParam(required = false, defaultValue = "") String city) throws ValidatorException, EntityNotFoundException {
        return baseService.getShopDetailsForAShop(shop, country, city);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ShopDetails getShopDetailById(@RequestParam Integer id) throws EntityNotFoundException {
        return baseService.getShopDetailById(id);
    }
}
