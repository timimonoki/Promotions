package promotions.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import promotions.exceptions.EntityNotFoundException;
import promotions.exceptions.ValidatorException;
import promotions.model.*;
import promotions.service.BaseService;

import java.util.*;

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
                                      @RequestParam(required = false, defaultValue = "") @DateTimeFormat(pattern="dd-MM-yyyy") Date endDate,
                                      @RequestParam(required = false, defaultValue = "") String searchKey,
                                      @RequestParam(required = false, defaultValue = "id") String orderBy,
                                      @RequestParam(required = false, defaultValue = "ASC") Sort.Direction orderDirection,
                                      @RequestParam(required = false, defaultValue = "0") Integer page,
                                      @RequestParam(required = false, defaultValue = "30") Integer pageSize) throws ValidatorException, EntityNotFoundException {
        return baseService.findCatalogs(country, city, shop, id, startDate, endDate, searchKey, orderBy, orderDirection, page, pageSize);
    }

    @RequestMapping(value = "/findCatalog", method = RequestMethod.GET)
    public Catalog findCatalogById(@RequestParam(value = "id") Integer id){
        return baseService.findCatalogById(id);
    }

    @RequestMapping(value = "/cityCatalogs", method = RequestMethod.GET)
    public List<Catalog> findCurrentCatalogsForACity(@RequestParam(required = false, defaultValue = "") String city) throws ValidatorException, EntityNotFoundException {
        return baseService.findCurrentCatalogsForACity(city);
    }

    @RequestMapping(value = "/allCatalogs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Catalog> findAllCatalogsForAShop(@RequestParam String shop){
        return baseService.findAllCatalogsForAShop(shop);
    }

    @RequestMapping(value = "/currentCatalogs", method = RequestMethod.GET)
    public List<Catalog> findCurrentCatalogsForAShop(@RequestParam Integer id) throws ValidatorException, EntityNotFoundException {
        return baseService.findCurrentCatalogsForAShop(id);
    }

    @RequestMapping(value = "/catalogImages", method = RequestMethod.GET)
    public List<Image> findImagesForACatalog(@RequestParam Integer id) throws EntityNotFoundException {
        return baseService.findImagesForACatalog(id);
    }

    @RequestMapping(value = "/findShops", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Shop> findShops(@RequestParam(required = false, defaultValue = "") String country,
                                @RequestParam(required = false, defaultValue = "") String city,
                                @RequestParam(required = false, defaultValue = "") String searchKey,
                                @RequestParam(required = false, defaultValue = "id") String orderBy,
                                @RequestParam(required = false, defaultValue = "ASC") Sort.Direction orderDirection,
                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                @RequestParam(required = false, defaultValue = "30") Integer pageSize) throws Exception {
        return baseService.findShops(country, city, searchKey, orderDirection, orderBy, page, pageSize);
    }

    @RequestMapping(value = "/allDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopDetails> getAllShopDetailsForAShop(@RequestParam String shop) throws Exception {
        return baseService.getAllShopDetailsForAShop(shop);
    }

    @RequestMapping(value = "/allShopsDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopDetails> getAllShopsDetailsForAShop() throws Exception {
        return baseService.getAllShopsDetails();
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopDetails> getShopDetailsForAShop(@RequestParam String shop,
                                                    @RequestParam(required = false, defaultValue = "") String country,
                                                    @RequestParam(required = false, defaultValue = "") String city,
                                                    @RequestParam(required = false, defaultValue = "") String searchKey,
                                                    @RequestParam(required = false, defaultValue = "city") String orderBy,
                                                    @RequestParam(required = false, defaultValue = "ASC") Sort.Direction orderDirection,
                                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(required = false, defaultValue = "30") Integer pageSize) throws ValidatorException, EntityNotFoundException {
        return baseService.getShopDetailsForAShop(shop, country, city, orderDirection, orderBy, page, pageSize);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ShopDetails getShopDetailById(@RequestParam Integer id) throws EntityNotFoundException {
        return baseService.getShopDetailById(id);
    }

    @CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.OPTIONS})
    @RequestMapping(value = "/location", method = RequestMethod.GET)
    public Map<String, String> getLocation(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("latitude", "46.784010");
        map.put("longitude", "23.556620");
        return map;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public List<Category> findAllCategories(){
        return baseService.findAllCategories();
    }
}
