package promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import promotions.model.Catalog;

import java.util.Date;
import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer>, QueryDslPredicateExecutor<Catalog> {

    @Query("SELECT c FROM Catalog c WHERE c.end_date > :current_date AND c.shop.name = :shop_name")
    List<Catalog> findByAvailability(@Param("current_date") Date currentDate, @Param("shop_name") String shopName);

    @Query("SELECT c FROM Catalog c JOIN c.shop s JOIN s.shopDetails d WHERE d.city = :city AND c.end_date > CURRENT_DATE")
    List<Catalog> findCurrentCatalogsForACity(@Param("city") String city);

    @Query("SELECT c FROM Catalog c JOIN c.shop s WHERE s.name = :shop AND c.end_date > CURRENT_DATE")
    List<Catalog> findCurrentCatalogsForAShop(@Param("shop") String shop);

    @Query("SELECT c FROM Catalog c WHERE c.end_date > CURRENT_DATE")
    List<Catalog> findCurrentCatalogs();

    @Query("SELECT c FROM Catalog c JOIN c.shop s WHERE s.name = :shop")
    List<Catalog> findAllCatalogsForAShop(@Param("shop") String shop);
}
