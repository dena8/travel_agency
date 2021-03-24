package final_project.travel_agency.repository;

import final_project.travel_agency.model.entity.Tour;
import final_project.travel_agency.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO users_cart(user_id,cart_id)\n" +
            " VALUES(?1,?2)", nativeQuery = true)
    Integer updateUserCart(String userId, String tourId);

    Optional<User> findById(String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE  FROM users_authorities WHERE user_id = (?1)",nativeQuery = true)
    Integer deleteAuthority(@Param("userId") String userId);

    @Transactional
    @Modifying
    @Query(value = "insert into users_authorities (user_id,authorities_id)\n" +
            "VALUES((?1),(?2))",nativeQuery = true)
    Integer insertUserAuthority(@Param("userId") String userId, @Param("authorityId") String authorityId);

}
