package final_project.travel_agency.repository;

import final_project.travel_agency.model.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour,String> {
    Optional<Tour> findById(String id);

    @Transactional
    @Modifying
    @Query("update Tour set participants = participants-1 where id=id")
    void updateParticipants(@Param("id") String id);
}
