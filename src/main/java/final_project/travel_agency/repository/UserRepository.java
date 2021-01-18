package final_project.travel_agency.repository;

import final_project.travel_agency.model.entity.Tour;
import final_project.travel_agency.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);

  //  @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query(value = "UPDATE User u set u.cart= :cart where u.id= :id")
//    Integer updateUserCart(@Param("id") String id, @Param("cart")List<Tour> cart);

    @Modifying(clearAutomatically = true)
   @Transactional
    @Query(value = "INSERT INTO users_cart(user_id,cart_id)\n" +
            " VALUES(?1,?2)", nativeQuery = true)
    Integer updateUserCart( String userId, String tourId);
//  @Transactional
//  @Modifying
//    @Query(value = "UPDATE User u set u.email=:email where u.id=:id")
//    Integer updateU( @Param("id") String id, @Param("email") String email);
}
