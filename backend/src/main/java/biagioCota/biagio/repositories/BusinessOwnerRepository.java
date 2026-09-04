package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessOwnerRepository extends JpaRepository<BusinessOwner, UUID> {

    Optional<BusinessOwner> findByEmail(String email);

    List<BusinessOwner> findByActive(boolean active);

    List<BusinessOwner> findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(String name, String surname);

    boolean existsByEmail(String email);
}
