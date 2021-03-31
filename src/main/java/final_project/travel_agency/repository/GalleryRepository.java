package final_project.travel_agency.repository;

import final_project.travel_agency.model.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery,String> {
}
