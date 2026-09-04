package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.userSubclasses.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Optional<Admin> findByEmail(String email);

    List<Admin> findByActive(boolean active);

    List<Admin> findByDataAssunzioneBefore(LocalDate date);

    List<Admin> findByDataAssunzioneBetween(LocalDate from, LocalDate to);

    List<Admin> findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(String name, String surname);

    boolean existsByEmail(String email);
}
