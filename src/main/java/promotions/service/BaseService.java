package promotions.service;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import promotions.exceptions.EntityNotFoundException;
import promotions.exceptions.ValidatorException;
import promotions.model.*;
import promotions.pageArea.KauflandArea;
import promotions.pageArea.LidlArea;
import promotions.repository.*;
import promotions.tools.web.BrowserManager;
import promotions.utils.conf.SiteConfigurations;
import promotions.utils.validation.InputValidator;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static promotions.utils.Constants.LIDL;
import static promotions.utils.Utils.getDateFromString;
import static promotions.utils.Utils.getStringFromDate;

@Component
@ImportResource(locations = {"classpath:beans.xml"})
public abstract class BaseService {

    @Autowired
    @Qualifier(value = "siteConfiguration")
    SiteConfigurations configurations;

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    ShopDetailsRepository shopDetailsRepository;

    @Autowired
    InputValidator validator;

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

    public void saveCatalog(String countryName, String shopName, String catalogName, List<String> availability, List<String> imageUrls) throws ParseException, EntityNotFoundException {
        Shop shop = shopRepository.findByShopnameAndCountry(shopName, countryName);
        if(shop == null){
            throw new EntityNotFoundException("This shop does not exist in Romania!");
        }
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

    public List<Shop> findShops(String country, String city, String searchKey, Sort.Direction direction, String orderBy, Integer page, Integer pageSize) throws Exception {
        validator.validate(country, city);
        if(!"".equals(country)){
            if(countryRepository.findByName(country).isEmpty()){
                throw new EntityNotFoundException("The coutry you entered does not exist in out database. Please enter something else");
            }
        }
        if(!"".equals(city)){
            if(shopDetailsRepository.findByCityName(city).isEmpty()){
                throw new EntityNotFoundException("The city you have entered does not exist in out database. Please enter something else");
            }
        }
        QShop qShop = QShop.shop;
        com.querydsl.core.types.Predicate predicate = qShop.countries.any().name.eq(country)
                                        .and(qShop.shopDetails.any().city.eq(city))
                                        .or(qShop.name.containsIgnoreCase(searchKey))
                                        .or(qShop.countries.any().name.containsIgnoreCase(searchKey))
                                        .or(qShop.shopDetails.any().city.containsIgnoreCase(searchKey))
                                        .or(qShop.countries.any().countryCode.containsIgnoreCase(searchKey))
                                        .or(qShop.shopDetails.any().street.containsIgnoreCase(searchKey));

        List<Shop> shops = shopRepository.findAll(predicate, new PageRequest(page, pageSize, new Sort(direction, orderBy))).getContent();
        return shops;
    }

    public List<Catalog> findCatalogs(String country, String city, String shop, Integer id, Date startDate, Date endDate, String searchKey, String orderBy, Sort.Direction direction, Integer page, Integer pageSize) throws ValidatorException, EntityNotFoundException {
        validator.validate(country, city, shop, searchKey);
        List<Predicate<Catalog>> predicateList = new ArrayList<>();
        List<Predicate<Catalog>> searchKeyPredicateList = new ArrayList<>();
        if(!"".equals(country)){
            if(countryRepository.findByName(country).isEmpty()){
                throw new EntityNotFoundException("This country does not exist in our database. Please enter something else");
            }
            predicateList.add(catalog -> catalog.getShop().getCountries().stream().anyMatch(c -> c.getName().equals(country)));
        }
        if(!"".equals(city)){
            if(shopDetailsRepository.findByCityName(city).isEmpty()){
                throw new EntityNotFoundException("This city does not exist in out database. Please enter something else");
            }
            predicateList.add(catalog -> catalog.getShop().getShopDetails().stream().anyMatch(sd -> sd.getCity().equals(city)));
        }
        if(!"".equals(shop)){
            if(shopRepository.findByName(shop) == null){
                throw new EntityNotFoundException("This shop does not exist in our database. Please try something else");
            }
            predicateList.add(catalog -> catalog.getShop().equals(shop));
        }
        if(id != null){
            if(catalogRepository.findOne(id) == null){
                throw new EntityNotFoundException("The catalog with the specified id does not exist in our database");
            }
            predicateList.add(catalog -> catalog.getId().equals(id));
        }
        if(startDate != null){
            predicateList.add(catalog -> catalog.getStart_date().after(startDate));
        }
        if(endDate != null){
            predicateList.add(catalog -> catalog.getEnd_date().before(endDate));
        }
        searchKeyPredicateList.add(catalog -> catalog.getName().contains(searchKey));
        searchKeyPredicateList.add(catalog -> getStringFromDate(catalog.getStart_date()).contains(searchKey));
        searchKeyPredicateList.add(catalog -> getStringFromDate(catalog.getEnd_date()).contains(searchKey));
        searchKeyPredicateList.add(catalog -> catalog.getShop().getName().contains(searchKey));

        List<Catalog> catalogs = catalogRepository.findAll(new PageRequest(page, pageSize, new Sort(direction, orderBy))).getContent();

        if(predicateList.isEmpty()){
            return catalogs.stream()
                    .filter(searchKeyPredicateList.stream().reduce(Predicate::or).orElse(null))
                    .collect(Collectors.toList());
        }else {
            return catalogs.stream()
                    .filter(predicateList.stream().reduce(Predicate::and).orElse(null))
                    .filter(searchKeyPredicateList.stream().reduce(Predicate::or).orElse(null))
                    .collect(Collectors.toList());
        }
    }

    public Catalog findCatalogById(Integer id){
        return catalogRepository.findOne(id);
    }

    public List<Catalog> findCurrentCatalogsForACity(String city) throws ValidatorException, EntityNotFoundException {
        validator.validate(city);
        if(!"".equals(city)){
            if(shopDetailsRepository.findByCityName(city) == null){
                throw new EntityNotFoundException("This city does not exist in our database. Please insert something else");
            }
            return catalogRepository.findCurrentCatalogsForACity(city);
        }else{
            return catalogRepository.findCurrentCatalogs();
        }
    }

    public List<Image> findImagesForACatalog(Integer id) throws EntityNotFoundException {
        Catalog catalog = catalogRepository.findOne(id);
        if(catalog == null){
            throw new EntityNotFoundException("This catalog does not exist");
        }
        return catalog.getImages();
    }

    public List<ShopDetails> getAllShopDetailsForAShop(String shopName) throws Exception {
        validator.validate(shopName);
        Shop shop = shopRepository.findByName(shopName);
        if(shop == null){
            logger.warn("The shop you were looking for does not exist");
            throw new EntityNotFoundException("This shop does not exist in our database. Please enter something else");
        }
        return shop.getShopDetails();
    }

    public List<ShopDetails> getShopDetailsForAShop(String shopName, String country, String city, Sort.Direction direction, String orderBy, Integer page, Integer pageSize) throws ValidatorException, EntityNotFoundException {
        validator.validate(shopName, country, city);
        Shop shop = shopRepository.findByName(shopName);
        if(shop == null){
            logger.warn("The shop you were looking for does not exist");
            throw new EntityNotFoundException("This shop does not exist in our database. Please enter something else");
        }
        List<Predicate<ShopDetails>> predicateList = new ArrayList<>();
        if(!"".equals(country)){
            if(countryRepository.findByName(country).isEmpty()){
                throw new EntityNotFoundException("The coutry you entered does not exist in out database. Please enter something else");
            }
            predicateList.add(sd -> sd.getShop().getCountries().stream().anyMatch(c -> c.getName().equals(country)));
        }
        if(!"".equals(city)){
            if(shopDetailsRepository.findByCityName(city).isEmpty()){
                throw new EntityNotFoundException("The city you have entered does not exist in out database. Please enter something else");
            }
            predicateList.add(sd -> sd.getCity().equals(city));
        }
        if(predicateList.isEmpty()){
            return shop.getShopDetails();
        }else {
            return shop.getShopDetails().stream()
                    .filter(predicateList.stream().reduce(Predicate::and).orElse(null))
                    .collect(Collectors.toList());
        }
    }

    public ShopDetails getShopDetailById(Integer id) throws EntityNotFoundException {
        ShopDetails sd = shopDetailsRepository.findOne(id);
        if(sd == null){
            throw new EntityNotFoundException("Details with this id do not exist. Please enter something else");
        }
        return sd;
    }
}
