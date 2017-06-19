package promotions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import promotions.model.Image;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>{

    @Query("SELECT i FROM Image i WHERE i.catalog.end_date <= Date()")
    List<Image>findAllImagesForCurrentCatalog();
}
