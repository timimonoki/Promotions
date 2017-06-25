package promotions.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import promotions.exceptions.EntityNotFoundException;
import promotions.exceptions.ValidatorException;
import promotions.model.Catalog;
import promotions.model.Image;
import promotions.model.Shop;
import promotions.model.ShopDetails;
import promotions.service.LidlService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/lidl")
public class LidlController extends BaseController{

    private static final Logger logger = Logger.getLogger(LidlController.class);

    @Autowired
    LidlService lidlService;

    @RequestMapping(value = "/promotions", method = RequestMethod.GET)
    public void obtainCurrentCatalogImages() throws Exception {
        lidlService.obtainCurrentCatalogImages();
    }

    @RequestMapping(value = "/allCatalogs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Catalog> findAllCatalogsForLidl(){
       return lidlService.findAllCatalogsForLidl();
    }

    @RequestMapping(value = "/currentCatalog", method = RequestMethod.GET)
    public List<Catalog> findCurrentCatalogsForLidl(@RequestParam(required = false, defaultValue = "") String city) throws ValidatorException, EntityNotFoundException {
        return lidlService.findCurrentCatalogsForLidl(city);
    }
}
