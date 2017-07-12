package promotions.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import promotions.exceptions.EntityNotFoundException;
import promotions.exceptions.ValidatorException;
import promotions.model.Catalog;
import promotions.model.Image;
import promotions.model.Shop;
import promotions.model.ShopDetails;
import promotions.service.LidlService;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;



@CrossOrigin(origins = "*", maxAge = 36000, methods = {RequestMethod.GET, RequestMethod.OPTIONS}, exposedHeaders = {"Access-Control-Allow-Origin:*","Access-Control-Allow-Methods:GET, POST, PUT, DELETE, OPTIONS"})
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
}
