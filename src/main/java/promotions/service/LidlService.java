package promotions.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import promotions.exceptions.CurrentCatalogException;
import promotions.exceptions.ExceptionCode;
import promotions.exceptions.ExceptionResponse;
import promotions.model.Catalog;
import promotions.model.Image;
import promotions.model.Shop;
import promotions.model.ShopDetails;
import promotions.repository.CatalogRepository;
import promotions.repository.CountryRepository;
import promotions.repository.ImageRepository;

import javax.swing.plaf.SliderUI;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.Date;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import promotions.repository.ShopRepository;
import static promotions.utils.Utils.getDateFromString;

@Service
public class LidlService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    CountryRepository countryRepository;

    private static final Logger logger = Logger.getLogger(LidlService.class);

    public void getAllImagesForCurrentCatalog(String catalogUrl) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, ParseException {
        String[] formatURL = catalogUrl.split("/html5");
        String catalogImagesUrl = formatURL[0].concat("/mobile.xml");

        List<String> availability = getAvailability(catalogImagesUrl);
        List<String> imageUrls = getImageUrls(catalogImagesUrl);
        String catalogName = "Oferta saptamanii";

        List<Catalog> currentCatalog = catalogRepository.findByAvailability(new Date(), "Lidl");
        if(currentCatalog.isEmpty()){
            Shop shop = shopRepository.findByShopnameAndCountry("Lidl", "Romania");
            if(shop == null){
                throw new CurrentCatalogException(new ExceptionResponse(ExceptionCode.UNEXISTING_SHOP, "This shop does not exist in Romania!"));
            }
            Catalog catalog = new Catalog();
            catalog.setName(catalogName);
            catalog.setStart_date(getDateFromString(availability.get(0)));
            catalog.setEnd_date(getDateFromString(availability.get(1)));
            catalog.setShop(shop);
            catalogRepository.save(catalog);
            for(String imageUrl : imageUrls){
                Image image = new Image(imageUrl);
                image.setCatalog(catalog);
                imageRepository.save(image);
            }
        }else {
            logger.warn("This catalog already exists. You were trying to save the current catalogue twice. Nothing will be done");
        }
    }

    public List<Catalog> findAllCatalogsWithImages(){
        return imageRepository.findAllCatalogsWithImages();
    }

    public Catalog findCurrentCatalogForACity(String city){
        Catalog catalog = catalogRepository.findCurrentCatalogForACity(city);
        return catalog;
    }

    public List<String> getAvailability(String catalogImagesUrl) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder buider = factory.newDocumentBuilder();
        Document doc = buider.parse(catalogImagesUrl);
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        XPathExpression exp = xPath.compile("//pdf");
        String pdfTitle = exp.evaluate(doc);

        Pattern pattern = Pattern.compile("(([0-9]+)(\\.))");
        Matcher matcher = pattern.matcher(pdfTitle);
        String[] year;
        if(matcher.find()){
            year = matcher.group().split("\\.");
        }else{
            year = new String[]{""};
        }

        List<String> availability = new LinkedList<>();
        pattern = Pattern.compile("(([0-9]+)(_)([0-9])+)");
        matcher = pattern.matcher(pdfTitle);
        while (matcher.find()) {
            availability.add(matcher.group().replace("_", "-") + "-" + year[0]);
        }
        return availability;
    }

    public List<String> getImageUrls(String catalogImagesUrl) throws ParserConfigurationException, XPathExpressionException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder buider = factory.newDocumentBuilder();
        Document doc = buider.parse(catalogImagesUrl);
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        XPathExpression exp = xPath.compile("data/seite/quality");

        NodeList nodes = (NodeList) exp.evaluate(doc, XPathConstants.NODESET);

        String[] catalogUrl = catalogImagesUrl.split("mobile.xml");
        List<String> imageUrls = new ArrayList<>();
        int len = (nodes !=null) ? nodes.getLength() : 0;
        for(int i = 0; i < len; i++){
            Node child = nodes.item(i);
            if(child.getNodeType() == Node.ELEMENT_NODE){
                imageUrls.add(catalogUrl[0].concat(child.getTextContent()));
            }
        }
        return imageUrls;
    }

    public List<String> getAllShopNames(){
        List<Shop> shops = shopRepository.findAll();
        List<String> shopNames = new ArrayList<>();
        shops.forEach(shop->shopNames.add(shop.getName()));
        return shopNames;
    }

    public List<String> getAllShopsInACountry(String country){
        List<Shop> shops = shopRepository.findAll();
        List<String> shopCountries = new ArrayList<>();

        shops.forEach(shop -> shop.getCountries().forEach(shopCountry -> shopCountries.add(shopCountry.getName())));
        return null;
    }

    public List<ShopDetails> getAllShopDetailsForAShop(String shopName){
        Shop shop = shopRepository.findByName(shopName);
        return null;
    }

    public Integer updateShopLocation(){
        return null;
    }
}
