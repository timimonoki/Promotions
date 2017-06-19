package promotions.model;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
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
    private Long idNumber;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "shops_countries",
            joinColumns = {@JoinColumn(name = "shop_id")},
            inverseJoinColumns = {@JoinColumn(name = "country_id")})
    private List<Country> countries;

    @JsonIgnore
    @OneToMany(mappedBy = "shop")
    private List<ShopDetails> shopDetails;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "shops_categories",
            joinColumns = {@JoinColumn(name = "shop_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categories;

    @JsonIgnore
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

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
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
