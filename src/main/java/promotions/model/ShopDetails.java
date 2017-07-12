package promotions.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "shop_details")
public class ShopDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "street_number", nullable = false)
    private String streetNumber;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "zipcode", nullable = false)
    private Long zipcode;

    @Column(name = "opening_hour")
    private Time openingHour;

    @Column(name = "closing_hour")
    private Time closingHour;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JsonBackReference
    private Shop shop;


    public ShopDetails(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getZipcode() {
        return zipcode;
    }

    public void setZipcode(Long zipcode) {
        this.zipcode = zipcode;
    }

    public Time getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(Time openingHour) {
        this.openingHour = openingHour;
    }

    public Time getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(Time closingHour) {
        this.closingHour = closingHour;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
