package promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import promotions.model.Catalog;
import promotions.model.Image;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>{

    @Query("SELECT i FROM Image i JOIN i.catalog.shop WHERE i.catalog.shop.name = 'Lidl' AND i.catalog.end_date < CURRENT_DATE ")
    List<Image>findAllImagesForCurrentCatalog();
}
