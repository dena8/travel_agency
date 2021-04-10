package final_project.travel_agency.repository;

import final_project.travel_agency.model.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log,String> {
    List<Log> findAllByOrderByDateAsc();

    @Transactional
    @Modifying
    @Query("DELETE FROM Log")
    int delLog();
}
