package promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import promotions.model.Shop;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    @Query("SELECT s FROM Shop s JOIN s.countries c WHERE s.name = :shop_name AND c.name = :country")
    Shop findByShopnameAndCountry(@Param("shop_name") String shopName, @Param("country") String country);
}
