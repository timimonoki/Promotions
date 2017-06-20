package promotions.dto;

import promotions.model.Catalog;
import promotions.model.Category;
import promotions.model.ShopDetails;

import java.util.List;

public class ShopDTO {

    private Integer id;
    private String name;
    private Long identificationNumber;
    private ShopDetails details;
    private List<Catalog> catalogs;
    private List<Category> categories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(Long identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public ShopDetails getDetails() {
        return details;
    }

    public void setDetails(ShopDetails details) {
        this.details = details;
    }

    public List<Catalog> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
