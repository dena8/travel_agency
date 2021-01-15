package final_project.travel_agency.repository;

import final_project.travel_agency.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {
   Optional<Category> findByName(String category);
}
