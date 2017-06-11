package promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import promotions.model.Promotii;

@Repository
public interface Promotions_Repository extends JpaRepository<Promotii, Integer> {
}
