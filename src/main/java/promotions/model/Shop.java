package promotions.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "identification_nb", nullable = false)
    private String idNumber;

    @ManyToMany
    @JoinTable(name = "shops_countries",
            joinColumns = {@JoinColumn(name = "shop_id")},
            inverseJoinColumns = {@JoinColumn(name = "country_id")})
    @JsonBackReference
    private List<Country> countries;

    @JsonIgnore
    @OneToMany(mappedBy = "shop")
    @JsonManagedReference
    private List<ShopDetails> shopDetails;

    @ManyToMany
    @JoinTable(name = "shops_categories",
            joinColumns = {@JoinColumn(name = "shop_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    @JsonBackReference
    private List<Category> categories;

    @OneToMany(mappedBy = "shop")
    private List<Catalog> catalogs;


    public Shop(){}


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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<ShopDetails> getShopDetails() {
        return shopDetails;
    }

    public void setShopDetails(List<ShopDetails> shopDetails) {
        this.shopDetails = shopDetails;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
