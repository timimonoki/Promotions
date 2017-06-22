package promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promotions.model.ShopDetails;

@Repository
public interface ShopDetailsRepository extends JpaRepository<ShopDetails, Integer> {

}
