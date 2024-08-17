package ru.nikita.tyufyakov.pumpingunits.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikita.tyufyakov.pumpingunits.model.Users;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String username);
    Optional<Users> existsUserByUsername(String username);

}
