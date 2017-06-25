package promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import promotions.model.ShopDetails;

import java.util.List;

@Repository
public interface ShopDetailsRepository extends JpaRepository<ShopDetails, Integer> {

    @Query("SELECT sd FROM ShopDetails sd WHERE sd.city = :city_name")
    public List<ShopDetails> findByCityName(@Param("city_name") String cityName);
}
