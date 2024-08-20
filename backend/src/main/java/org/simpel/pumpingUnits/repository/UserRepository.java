package org.simpel.pumpingUnits.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.simpel.pumpingUnits.model.Users;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String username);
    boolean existsUserByEmail(String username);

}
