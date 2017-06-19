package promotions.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import promotions.model.Catalog;

import java.util.Date;
import java.util.List;

@Repository
public interface CatalogRepository extends CrudRepository<Catalog, Integer> {

    @Query("SELECT c FROM Catalog c WHERE c.end_date > Date(:current_date) AND c.shop.name = :shop_name")
    List<Catalog> findByAvailability(@Param("current_date") Date currentDate, @Param("shop_name") String shopName);
}
