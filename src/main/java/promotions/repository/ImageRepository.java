package promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import promotions.model.Catalog;
import promotions.model.Image;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>{

    @Query("SELECT i FROM Image i JOIN i.catalog c JOIN c.shop s WHERE s.name = 'Lidl' AND c.end_date < CURRENT_DATE ")
    List<Image>findAllImagesForCurrentCatalog();

    @Query("SELECT c FROM Catalog JOIN c.images JOIN c.shop s WHERE s.name = 'Lidl' AND c.end_date < CURRENT_DATE ")
    List<Catalog> findAllCatalogsWithImages();

}
