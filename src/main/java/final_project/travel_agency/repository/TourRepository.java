package final_project.travel_agency.repository;

import final_project.travel_agency.model.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour,String> {
    Optional<Tour> findById(String id);
}
