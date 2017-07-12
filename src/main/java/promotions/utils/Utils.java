package promotions.utils;

import org.springframework.beans.factory.annotation.Autowired;
import promotions.data.ShopsWithLocations;
import promotions.model.Country;
import promotions.model.Shop;
import promotions.repository.CountryRepository;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    public static Date getDateFromString(String dateInString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatter.parse(dateInString);
        return date;
    }

    public static String getStringFromDate(Date date){
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public static ShopsWithLocations convertToShopWithLocations(Shop shop){
        ShopsWithLocations shopWithLocations = new ShopsWithLocations();
        shopWithLocations.setId(shop.getId());
        shopWithLocations.setShopName(shop.getName());

        return null;
    }
}
