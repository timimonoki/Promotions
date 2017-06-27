package promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import promotions.model.Country;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>, QueryDslPredicateExecutor<Country> {

    public List<Country> findByName(@Param("country_name") String countryName);
}
