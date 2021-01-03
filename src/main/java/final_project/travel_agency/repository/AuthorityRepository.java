package final_project.travel_agency.repository;

import final_project.travel_agency.model.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,String> {
    Optional<Authority> findByAuthority(String authority);
}
