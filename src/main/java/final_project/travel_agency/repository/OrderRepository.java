package final_project.travel_agency.repository;

import final_project.travel_agency.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    Order[] findAllByOrderByBuyDateAsc();
}
