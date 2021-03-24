package final_project.travel_agency.repository;

import final_project.travel_agency.model.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,String> {
    Optional<Authority> findByAuthority(String authority);
    @Query("select a.authority from Authority as a ")
    List<String> getAuthorityNames();
}
