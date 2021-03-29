package final_project.travel_agency.repository;

import final_project.travel_agency.model.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Repository
public interface AuthorityRepository extends JpaRepository<Authority,String> {
    Optional<Authority> findByAuthority(String authority);
    @Query("select a.authority from Authority as a ")
    List<String> getAuthorityNames();

//    @Transactional
//    @Modifying
//    @Query(value = "ALTER DATABASE travel_agency_db\n" +
//            "CHARACTER SET utf8mb4\n" +
//            "COLLATE  utf8mb4_bin;", nativeQuery = true)
//   void changeDB();
}
