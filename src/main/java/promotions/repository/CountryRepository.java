package promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import promotions.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    public Country findByName(@Param("country_name") String countryName);
}
