package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndActive(String email, boolean active);

    List<User> findByActive(boolean active);

    List<User> findByName(String name);

    List<User> findBySurname(String surname);

    List<User> findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(String name, String surname);

    List<User> findByEntryDateBefore(LocalDateTime date);

    List<User> findByEntryDateAfter(LocalDateTime date);

    List<User> findByEntryDateBetween(LocalDateTime from, LocalDateTime to);

    boolean existsByEmail(String email);
}
